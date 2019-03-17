package backend.ioc.events.listeners.interfaces;

public interface Subject {
    void attachObserver(Observer observer);
    void detachObserver(Observer observer);
    void notifyObservers();
}
