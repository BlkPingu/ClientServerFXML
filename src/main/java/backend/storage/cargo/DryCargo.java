package backend.storage.cargo;


import backend.enums.Hazard;

import java.util.Collection;

public class DryCargo extends Cargo implements backend.storage.cargo.interfaces.DryCargo {

    private boolean solid;

    public DryCargo(int size, backend.storage.administration.Customer owner, Collection<Hazard> hazards, boolean solid) {
        super(size, owner, hazards);
        this.solid = solid;

    }

    @Override
    public boolean isSolid() {
        return this.solid;
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

    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}
