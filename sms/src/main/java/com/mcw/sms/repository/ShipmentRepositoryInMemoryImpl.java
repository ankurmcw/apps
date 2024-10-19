package com.mcw.sms.repository;

import com.mcw.sms.model.Shipment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ShipmentRepositoryInMemoryImpl implements Repository<Shipment> {
    private static final Map<String, Shipment> shipments = new ConcurrentHashMap<>();
    private static final ShipmentRepositoryInMemoryImpl INSTANCE = new ShipmentRepositoryInMemoryImpl();

    private ShipmentRepositoryInMemoryImpl() {}

    public static ShipmentRepositoryInMemoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Shipment shipment) {
        shipments.put(shipment.getShipmentId(), shipment);
    }

    @Override
    public List<Shipment> findAll() {
        return new ArrayList<>(shipments.values());
    }

    @Override
    public Optional<Shipment> findOne(String shipmentId) {
        return Optional.ofNullable(shipments.get(shipmentId));
    }
}
