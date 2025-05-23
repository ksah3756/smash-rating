package com.smashrating.user.infrastructure;

import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {
    private final List<Object> publishedEvents = new ArrayList<>();
    @Override
    public void publishEvent(Object event) {
        publishedEvents.add(event);
    }

    public List<Object> getPublishedEvents() {
        return publishedEvents;
    }

    public void clearPublishedEvents() {
        publishedEvents.clear();
    }

}
