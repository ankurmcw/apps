package repository

import (
	"sms_go/model"
)

type InMemoryShipmentRepository struct {
    store map[string]*model.Shipment
}

func NewInMemoryShipmentRepository() *InMemoryShipmentRepository {
    return &InMemoryShipmentRepository{
        store: make(map[string]*model.Shipment),
    }
}

func (i *InMemoryShipmentRepository) Save(shipment *model.Shipment) error {
    i.store[shipment.GetShipmentId()] = shipment
    return nil
}

func (i *InMemoryShipmentRepository) Find(id string) *model.Shipment {
    return i.store[id]
}

func (i *InMemoryShipmentRepository) FindAll() []string {
    var shipmentIds [] string
    for key := range i.store {
        shipmentIds = append(shipmentIds, key)
    }
    return shipmentIds
}
