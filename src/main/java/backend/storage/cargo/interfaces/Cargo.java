package backend.storage.cargo.interfaces;

import backend.enums.Hazard;
import backend.storage.administration.interfaces.Customer;

import java.util.Collection;

public interface Cargo {
    int minSize=1;
    int maxSize=3;
    int getSize();
    Customer getOwner();
    Collection<Hazard> getHazards();
}
