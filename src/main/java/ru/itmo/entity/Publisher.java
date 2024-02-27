package ru.itmo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Observer class stores subscribers and has subscription, unsubscribe, and notification methods
 * Implements the interface {@link IPublisher}
 *
 * @author valer
 * @version 1.0
 * @since 2024-02-27
 */
public class Publisher implements IPublisher {
    private List<ISubscriber> subscribers = new ArrayList<>();

    public void addObserver(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeObserver(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * @param message    the message that will be sent to subscribers
     * @param targetType the type of class that needs to be notified
     */
    public void notifyObservers(String message, Class<?> targetType) {
        for (ISubscriber subscriber : subscribers) {
            if (targetType.isInstance(subscriber))
                subscriber.update(message);
        }
    }
}