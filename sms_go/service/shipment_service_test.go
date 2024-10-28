package service

import (
	"context"
	"reflect"
	"sms_go/model"
	"sms_go/repository"
	"testing"
)

func TestShipmentService_CreateShipment(t *testing.T) {
    
	type fields struct {
		shipmentRepository repository.ShipmentRepository
		stateRepository    repository.StateRepository
		eventRepository    repository.EventRepository
		stateManager       *StateManager
	}
	type args struct {
		ctx        context.Context
		shipmentId string
		stateId    string
	}
	tests := []struct {
		name    string
		fields  fields
		args    args
		want    *model.Shipment
		wantErr bool
	}{
		{
            name: "Shipment created",
        },
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
            ctx := context.TODO()
			ss := NewShipmentService(
                ctx,
                repository.NewInMemoryShipmentRepository(ctx),
                repository.NewInMemoryStateRepository(ctx),
                repository.NewInMemoryEventRepository(ctx),
                repository.NewInMemoryStateTransitionRepository(ctx),
            )
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
