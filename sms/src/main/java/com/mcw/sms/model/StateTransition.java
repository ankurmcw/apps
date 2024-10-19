package com.mcw.sms.model;

public record StateTransition(Event event, State from, State to) {
}
