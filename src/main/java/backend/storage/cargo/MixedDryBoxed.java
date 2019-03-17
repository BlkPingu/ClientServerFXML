package backend.storage.cargo;


import backend.enums.Hazard;

import java.util.Collection;

public class MixedDryBoxed extends Cargo implements backend.storage.cargo.interfaces.MixedDryBoxed {

    private boolean fragile;
    private boolean solid;

    public MixedDryBoxed(int size, backend.storage.administration.Customer owner, Collection<Hazard> hazards, boolean fragile, boolean solid) {
        super(size, owner, hazards);
        this.fragile = fragile;
        this.solid = solid;
    }

    @Override
    public boolean isFragile() {
        return this.fragile;
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
}
