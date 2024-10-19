package com.mcw.sms;

import com.mcw.sms.model.Event;
import com.mcw.sms.model.Shipment;
import com.mcw.sms.model.State;
import com.mcw.sms.model.StateTransition;
import com.mcw.sms.repository.EventRepositoryInMemoryImpl;
import com.mcw.sms.repository.Repository;
import com.mcw.sms.repository.ShipmentRepositoryInMemoryImpl;
import com.mcw.sms.repository.StateRepositoryInMemoryImpl;
import com.mcw.sms.repository.StateTransitionInMemoryRepositoryImpl;
import com.mcw.sms.service.ShipmentService;
import com.mcw.sms.service.StateManager;
import com.mcw.sms.service.StateManagerImpl;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {

    private static final Logger logger = Logger.getLogger(Driver.class.getName());
    private static final Repository<State> stateRepository;
    private static final Repository<Event> eventRepository;
    private static final Repository<StateTransition> stateTransitionRepository;
    private static final Repository<Shipment> shipmentRepository;

    static {
        stateRepository = StateRepositoryInMemoryImpl.getInstance();
        eventRepository = EventRepositoryInMemoryImpl.getInstance();
        shipmentRepository = ShipmentRepositoryInMemoryImpl.getInstance();
        stateTransitionRepository = StateTransitionInMemoryRepositoryImpl.getInstance();

        State orderPlaced = new State("ORDER_PLACED");
        stateRepository.save(orderPlaced);

        State orderPacked = new State("ORDER_PACKED");
        stateRepository.save(orderPacked);

        State orderPickedUp = new State("ORDER_PICKED_UP");
        stateRepository.save(orderPickedUp);

        State orderShipped = new State("ORDER_SHIPPED");
        stateRepository.save(orderShipped);

        State orderInTransit = new State("ORDER_IN_TRANSIT");
        stateRepository.save(orderInTransit);

        State orderDelivered = new State("ORDER_DELIVERED");
        stateRepository.save(orderDelivered);

        Event packed = new Event("PACKED");
        eventRepository.save(packed);

        Event pickedUp = new Event("PICKED_UP");
        eventRepository.save(pickedUp);

        Event shipped = new Event("SHIPPED");
        eventRepository.save(shipped);

        Event inTransit = new Event("IN_TRANSIT");
        eventRepository.save(inTransit);

        Event delivered = new Event("DELIVERED");
        eventRepository.save(delivered);

        stateTransitionRepository.save(new StateTransition(packed, orderPlaced, orderPacked));

    }

    public static void main(String[] args) {
        logger.log(Level.INFO, () -> stateRepository.findAll().toString());
        logger.log(Level.INFO, () -> eventRepository.findAll().toString());
        logger.log(Level.INFO, () -> stateTransitionRepository.findAll().toString());

        StateManager stateManager = StateManagerImpl.getInstance(stateTransitionRepository);
        ShipmentService shipmentService = ShipmentService.getInstance(shipmentRepository, stateRepository, eventRepository);

        shipmentService.createShipment("SHP1", "ORDER_PLACED");
        logger.log(Level.INFO, () -> shipmentRepository.findOne("SHP1").toString());

        shipmentService.updateState(stateManager, "SHP1", "PACKED");
        logger.log(Level.INFO, () -> shipmentRepository.findOne("SHP1").toString());

        shipmentService.updateState(stateManager, "SHP1", "PICKED_UP");
    }
}
