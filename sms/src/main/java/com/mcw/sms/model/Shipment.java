package com.mcw.sms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Shipment {

    private final String shipmentId;
    private State currentState;
    private final List<State> previousStates;

    public Shipment(String shipmentId, State currentState) {
        this.shipmentId = shipmentId;
        this.currentState = currentState;
        this.previousStates = new ArrayList<>();
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<State> getPreviousStates() {
        return previousStates;
    }

    public void addNextState(State nextState) {
        previousStates.add(currentState);
        this.currentState = nextState;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Shipment shipment)) return false;
        return Objects.equals(getShipmentId(), shipment.getShipmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getShipmentId());
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId='" + shipmentId + '\'' +
                ", currentState=" + currentState +
                ", previousStates=" + previousStates +
                '}';
    }
}
