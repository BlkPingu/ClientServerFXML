package backend.storage.cargo;


import backend.enums.Hazard;
import backend.storage.administration.Customer;

import java.util.Collection;

public class Cargo implements backend.storage.cargo.interfaces.Cargo {
    int size;
    backend.storage.administration.Customer owner;
    Collection<Hazard> hazards;

    public Cargo(int size, Customer owner, Collection<Hazard> hazards) {
        this.size = size;
        this.owner = owner;
        this.hazards = hazards;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public backend.storage.administration.Customer getOwner() {
        return this.owner;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return this.hazards;
    }
}
