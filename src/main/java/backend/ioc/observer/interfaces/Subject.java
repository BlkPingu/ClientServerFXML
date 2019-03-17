package backend.ioc.observer.interfaces;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void nofityObserver();
}
