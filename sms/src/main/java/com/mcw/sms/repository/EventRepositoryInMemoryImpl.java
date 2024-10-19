package com.mcw.sms.repository;

import com.mcw.sms.model.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EventRepositoryInMemoryImpl implements Repository<Event> {
    private static final Map<String, Event> eventMap = new ConcurrentHashMap<>();
    private static final EventRepositoryInMemoryImpl instance = new EventRepositoryInMemoryImpl();

    private EventRepositoryInMemoryImpl() {}

    public static EventRepositoryInMemoryImpl getInstance() {
        return instance;
    }

    @Override
    public void save(Event event) {
        eventMap.put(event.eventId(), event);
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(eventMap.values());
    }

    @Override
    public Optional<Event> findOne(String eventId) {
        return Optional.ofNullable(eventMap.get(eventId));
    }
}
