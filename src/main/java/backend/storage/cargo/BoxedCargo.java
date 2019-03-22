package backend.storage.cargo;


import backend.enums.Hazard;
import backend.storage.administration.Customer;

import java.util.Collection;

public class BoxedCargo extends Cargo implements backend.storage.cargo.interfaces.BoxedCargo {


    private boolean fragile;

    public BoxedCargo(int size, Customer owner, Collection<Hazard> hazards, boolean fragile) {
        super(size, owner, hazards);
        this.fragile = fragile;
    }

    @Override
    public boolean isFragile() {
        return this.fragile;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Customer getOwner() {
        return this.owner;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return this.hazards;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }
}
