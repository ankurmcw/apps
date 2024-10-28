package service

import (
	"context"
	"fmt"
	"sms_go/model"
	"sms_go/repository"
)

type ShipmentService struct {
    shipmentRepository repository.ShipmentRepository
    stateRepository    repository.StateRepository
    eventRepository    repository.EventRepository
    stateManager       *StateManager
}

func NewShipmentService(
        ctx context.Context,
        shipmentRepo repository.ShipmentRepository,
        stateRepository repository.StateRepository,
        eventRepoisotry repository.EventRepository,
        stateTransitionRepository repository.StateTransitionRepository) *ShipmentService {

            stateManager := NewStateManager(ctx, stateTransitionRepository)
            return &ShipmentService{
                shipmentRepository: shipmentRepo,
                stateRepository: stateRepository,
                eventRepository: eventRepoisotry,
                stateManager: stateManager,
            }
}

func (ss *ShipmentService) CreateShipment(ctx context.Context, shipmentId, stateId string) (*model.Shipment, error) {
    currentState := ss.stateRepository.Find(ctx, stateId)
    if currentState == nil {
        return nil, fmt.Errorf("no state found with id '%s", stateId)
    }
    shipment := model.NewShipment(ctx, shipmentId, currentState)
    ss.shipmentRepository.Save(ctx, shipment)
    return shipment, nil
}

func (ss *ShipmentService) UpdateState(ctx context.Context, shipmentId, eventId string) (*model.Shipment, error) {
    shipment := ss.shipmentRepository.Find(ctx, shipmentId)
    if shipment == nil {
        return nil, fmt.Errorf("no shipment found with id '%s'", shipmentId)
    }
    event := ss.eventRepository.Find(ctx, eventId)
    if event == nil {
        return nil, fmt.Errorf("no event found with id '%s'", eventId)
    }
    nextState, err := ss.stateManager.GetNextState(ctx, shipment.GetCurrentState(ctx), event)
    if err != nil {
        return nil, err
    }
    shipment.AddNextState(ctx, nextState)
    return shipment, nil
}
