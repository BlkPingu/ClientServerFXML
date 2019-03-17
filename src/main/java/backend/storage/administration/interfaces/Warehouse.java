package backend.storage.administration.interfaces;

public interface Warehouse {
    int getSize();
    void cargoByType();
    void getStorageDate();
    void deleteCargo();
    void newCargo();
    void getCustomersCargoAmount();
    void getHazardsInStorage();
    void getHazardsNotInStorage();
    void getStoragePosition();
}
