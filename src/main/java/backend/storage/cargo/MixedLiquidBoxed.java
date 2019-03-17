package backend.storage.cargo;


import backend.enums.Hazard;

import java.util.Collection;

public class MixedLiquidBoxed extends Cargo implements backend.storage.cargo.interfaces.MixedLiquidBoxed {

    private boolean fragile;
    private boolean pressurized;

    public MixedLiquidBoxed(int size, backend.storage.administration.Customer owner, Collection<Hazard> hazards, boolean fragile, boolean pressurized) {
        super(size, owner, hazards);
        this.fragile = fragile;
        this.pressurized = fragile;
    }

    @Override
    public boolean isFragile() {
        return this.fragile;
    }

    @Override
    public boolean isPressurized() {
        return this.pressurized;
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
