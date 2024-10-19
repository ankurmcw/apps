package com.mcw.sms.service;

import com.mcw.sms.model.Event;
import com.mcw.sms.model.Shipment;
import com.mcw.sms.model.State;
import com.mcw.sms.repository.Repository;
import java.util.Optional;

public class ShipmentService {

    private Repository<Shipment> shipmentRepository;
    private Repository<State> stateRepository;
    private Repository<Event> eventRepository;
    private static ShipmentService instance;

    private ShipmentService() {}

    public static synchronized ShipmentService getInstance(Repository<Shipment> shipmentRepository,
                                              Repository<State> stateRepository,
                                              Repository<Event> eventRepository) {
        if (instance == null) {
            instance = new ShipmentService();
            instance.shipmentRepository = shipmentRepository;
            instance.stateRepository = stateRepository;
            instance.eventRepository = eventRepository;
        }
        return instance;
    }

    public void createShipment(String shipmentId, String stateCode) {
        Optional<State> currentState = stateRepository.findOne(stateCode);
        if (currentState.isPresent()) {
            shipmentRepository.save(new Shipment(shipmentId, currentState.get()));
            return;
        }
        throw new IllegalArgumentException("No such state: " + stateCode);
    }

    public Shipment getShipment(String shipmentId) {
        Optional<Shipment> shipment = shipmentRepository.findOne(shipmentId);
        if (shipment.isPresent()) {
            return shipment.get();
        }
        throw new IllegalArgumentException("No such shipment: " + shipmentId);
    }

    public void updateState(StateManager stateManager, String shipmentId, String eventId) {
        Shipment shipment = getShipment(shipmentId);
        Optional<Event> event = eventRepository.findOne(eventId);
        if (event.isPresent()) {
            State nextState = stateManager.getNextState(event.get(), shipment.getCurrentState());
            shipment.addNextState(nextState);
            return;
        }
        throw new IllegalArgumentException("No such event: " + eventId);
    }

}
