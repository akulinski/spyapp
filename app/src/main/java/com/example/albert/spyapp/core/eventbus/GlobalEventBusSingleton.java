package com.example.albert.spyapp.core.eventbus;

import com.google.common.eventbus.EventBus;

public class GlobalEventBusSingleton {

    private EventBus eventBus = null;

    private static GlobalEventBusSingleton busSingleton = null;

    private GlobalEventBusSingleton() {
        eventBus = new EventBus("Global");
    }

    public static GlobalEventBusSingleton getInstance() {

        if (busSingleton == null) {

            synchronized (GlobalEventBusSingleton.class) {
                busSingleton = new GlobalEventBusSingleton();
            }
        }

        return busSingleton;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
