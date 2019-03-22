package backend.storage.cargo;


import backend.enums.Hazard;

import java.util.Collection;

public class MixedDryLiquidBoxed extends Cargo implements backend.storage.cargo.interfaces.MixedDryLiquidBoxed {

    private boolean fragile;
    private boolean solid;
    private boolean pressurized;

    public MixedDryLiquidBoxed(int size, backend.storage.administration.Customer owner, Collection<Hazard> hazards, boolean fragile, boolean solid, boolean pressurized) {
        super(size, owner, hazards);
        this.fragile = fragile;
        this.solid = solid;
        this.pressurized = pressurized;

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

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public void setPressurized(boolean pressurized) {
        this.pressurized = pressurized;
    }
}
