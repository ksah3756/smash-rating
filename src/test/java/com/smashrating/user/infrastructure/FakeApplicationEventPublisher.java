package com.smashrating.user.infrastructure;

import org.springframework.context.ApplicationEventPublisher;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {
    @Override
    public void publishEvent(Object event) {
        // No-op implementation for testing purposes
        System.out.println("Event published: " + event.toString());
    }
}
