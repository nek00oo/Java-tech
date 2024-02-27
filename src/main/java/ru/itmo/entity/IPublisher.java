package ru.itmo.entity;

public interface IPublisher {
    void addObserver(ISubscriber subscriber);
    void removeObserver(ISubscriber subscriber);
    void notifyObservers(String message, Class<?> targetType);
}
