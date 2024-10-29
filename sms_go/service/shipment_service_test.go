package service

import (
	"context"
	"reflect"
	"sms_go/model"
	"sms_go/repository"
	"testing"
)

var (
    shipmentRepo repository.ShipmentRepository
    stateRepo repository.StateRepository
    eventRepo repository.EventRepository
    stateTransitionRepo repository.StateTransitionRepository
)


func TestMain(m *testing.M) {
	shipmentRepo = repository.NewInMemoryShipmentRepository()
	stateRepo = repository.NewInMemoryStateRepository()
	eventRepo = repository.NewInMemoryEventRepository()
	stateTransitionRepo = repository.NewInMemoryStateTransitionRepository()

    orderPlaced := model.NewState("ORDER_PLACED")
    stateRepo.Save(orderPlaced)

    orderPacked := model.NewState("ORDER_PACKED")
    stateRepo.Save(orderPacked)

    eventPacked := model.NewEvent("PACKED")
    eventRepo.Save(eventPacked)

    placedToPacked := model.NewStateTransition(eventPacked, orderPlaced, orderPacked)
    stateTransitionRepo.Save(placedToPacked)
    m.Run()
}

func TestShipmentService_CreateShipment(t *testing.T) {
    ctx := context.TODO()

	type args struct {
		ctx        context.Context
		shipmentId string
		stateId    string
	}
	tests := []struct {
		name    string
		args    args
		want    *model.Shipment
		wantErr bool
	}{
		{
			name: "Shipment created",
			args: args{
				ctx:        context.TODO(),
				shipmentId: "shipment1",
				stateId:    "ORDER_PLACED",
			},
			want:    model.NewShipment("shipment1", model.NewState("ORDER_PLACED")),
			wantErr: false,
		},
		{
			name: "Invalid state",
			args: args{
				ctx:        context.TODO(),
				shipmentId: "shipment2",
				stateId:    "ORDER_PICKED_UP",
			},
			wantErr: true,
		},
	}
	ss := NewShipmentService(
		ctx, shipmentRepo, stateRepo, eventRepo, stateTransitionRepo)

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {

			got, err := ss.CreateShipment(tt.args.ctx, tt.args.shipmentId, tt.args.stateId)
			if (err != nil) != tt.wantErr {
				t.Errorf("ShipmentService.CreateShipment() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(got, tt.want) {
				t.Errorf("ShipmentService.CreateShipment() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestShipmentService_UpdateState(t *testing.T) {
	type args struct {
		ctx        context.Context
		shipmentId string
		eventId    string
	}
    type expected struct {
        shipmentId    string
        currentState  *model.State
        previousStates []*model.State
    }
	tests := []struct {
		name    string
		args    args
		want    expected
		wantErr bool
	}{
		{
            name: "State updated",
            args: args{
                ctx: context.TODO(),
                shipmentId: "shipment1",
                eventId: "PACKED",
            },
            want: expected{
                shipmentId: "shipment1",
                currentState: model.NewState("ORDER_PACKED"),
                previousStates: []*model.State{model.NewState("ORDER_PLACED")},
            },
            wantErr: false,
        },
	}

    ss := NewShipmentService(context.TODO(), shipmentRepo, stateRepo, eventRepo, stateTransitionRepo)
    _, err := ss.CreateShipment(context.TODO(), "shipment1", "ORDER_PLACED")
    if err != nil {
        t.Errorf("Set up failed, error: %s", err.Error())
    }

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			got, err := ss.UpdateState(tt.args.ctx, tt.args.shipmentId, tt.args.eventId)
			if (err != nil) != tt.wantErr {
				t.Errorf("ShipmentService.UpdateState() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if tt.wantErr == false {
				if got.GetShipmentId() != tt.want.shipmentId {
                    t.Errorf("shipment Id did not match")
                }
                if !reflect.DeepEqual(got.GetCurrentState(), tt.want.currentState) {
                    t.Errorf("current state did not match")
                }
                if len(got.GetPreviousStates()) != len(tt.want.previousStates) {
                    t.Errorf("number of previous states did not match")
                }
                for i, val := range got.GetPreviousStates() {
                    if !reflect.DeepEqual(val, tt.want.previousStates[i]) {
                        t.Errorf("previous states did not match")
                    }
                }
			}
		})
	}
}
