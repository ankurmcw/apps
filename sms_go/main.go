package main

import (
	"context"
	"fmt"
	"log"
	"sms_go/model"
	"sms_go/repository"
	"sms_go/service"
	"sync"
)

var states = []string{
	"ORDER_PLACED",
	"ORDER_PACKED",
	"ORDER_SHIPPED",
	"ORDER_IN_TRANSIT",
	"ORDER_DELIVERED",
}

var events = []string{
	"PLACED",
	"PACKED",
	"SHIPPED",
	"IN_TRANSIT",
	"DELIVERED",
}

func main() {
	defer func() {
		if r := recover(); r != nil {
			log.Println("Recovered", r)
		}
	}()

	ctx := context.TODO()
	shipmentRepo := repository.NewInMemoryShipmentRepository()
	stateRepo := repository.NewInMemoryStateRepository()
	eventRepo := repository.NewInMemoryEventRepository()
	stateTransitionRepo := repository.NewInMemoryStateTransitionRepository()

	for _, stateId := range states {
		state := model.NewState(stateId)
		stateRepo.Save(state)
	}

	for _, eventId := range events {
		event := model.NewEvent(eventId)
		eventRepo.Save(event)
	}

	placedToPacked := model.NewStateTransition(
		eventRepo.Find("PACKED"), stateRepo.Find("ORDER_PLACED"), stateRepo.Find("ORDER_PACKED"))
	stateTransitionRepo.Save(placedToPacked)

	shipmenService := service.NewShipmentService(ctx, shipmentRepo, stateRepo, eventRepo, stateTransitionRepo)
	shipment1, err := shipmenService.CreateShipment(ctx, "shipment1", "ORDER_PLACED")
	if err != nil {
		log.Panicf(err.Error())
	}

	if shipment1 == shipmentRepo.Find("shipment1") {
		log.Println("shipment found")
	}

	shipment, err := shipmenService.UpdateState(ctx, "shipment1", "PACKED")
	if err != nil {
		log.Panicf(err.Error())
	}

	log.Printf("current state of shipment '%s' is %s and previous states are %v",
		shipment.GetShipmentId(), shipment.GetCurrentState(), shipment.GetPreviousStates()[0])

	log.Println(shipmentRepo.Find("shipment2"))

	var wg sync.WaitGroup
	for i := range 10 {
		wg.Add(1)
		go func() {
			defer wg.Done()
			shipmenService.CreateShipment(ctx, fmt.Sprintf("shipment%d", (i+2)), "ORDER_PLACED")
		}()
	}
	wg.Wait()
	log.Println(shipmentRepo.FindAll())
	// panic("I panicked")
}
