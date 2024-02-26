package ru.itmo.entity;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<ISubscriber> subscribers = new ArrayList<>();

    public void addObserver(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeObserver(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    protected void notifyObservers(String message, Class<?> targetType) {
        for (ISubscriber subscriber : subscribers) {
            if (targetType.isInstance(subscriber))
                subscriber.update(message);
        }
    }
}