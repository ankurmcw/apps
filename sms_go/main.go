package main

import (
	"context"
	"log"
	"sms_go/model"
	"sms_go/repository"
	"sms_go/service"
)

func main() {
    ctx := context.TODO()

    shipmentRepo := repository.NewInMemoryShipmentRepository(ctx)
    stateRepo := repository.NewInMemoryStateRepository(ctx)
    eventRepo := repository.NewInMemoryEventRepository(ctx)
    stateTransitionRepo := repository.NewInMemoryStateTransitionRepository(ctx)

    orderPlaced := model.NewState(ctx, "ORDER_PLACED")
    stateRepo.Save(ctx, orderPlaced)

    orderPacked := model.NewState(ctx, "ORDER_PACKED")
    stateRepo.Save(ctx, orderPacked)

    eventPacked := model.NewEvent(ctx, "PACKED")
    eventRepo.Save(ctx, eventPacked)

    placedToPacked := model.NewStateTransition(ctx, eventPacked, orderPlaced, orderPacked)
    stateTransitionRepo.Save(ctx, placedToPacked)

    shipmenService := service.NewShipmentService(ctx, shipmentRepo, stateRepo, eventRepo, stateTransitionRepo)
    shipment1, err := shipmenService.CreateShipment(ctx, "shipment1", "ORDER_PICKED")
    if err != nil {
        log.Panicf(err.Error())
    }

    if shipment1 == shipmentRepo.Find(ctx, "shipment1") {
        log.Println("shipment found")
    }

    shipment, err := shipmenService.UpdateState(ctx, "shipment1", "PACKED")
    if err != nil {
        log.Panicf(err.Error())
    }

    log.Printf("current state of shipment '%s' is %s and previous states are %v", 
        shipment.GetShipmentId(ctx), shipment.GetCurrentState(ctx), shipment.GetPreviousStates(ctx)[0])

    log.Println(shipmentRepo.Find(ctx, "shipment2"))

}

