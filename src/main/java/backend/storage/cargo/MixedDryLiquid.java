package backend.storage.cargo;


import backend.enums.Hazard;

import java.util.Collection;

public class MixedDryLiquid extends Cargo implements backend.storage.cargo.interfaces.MixedDryLiquid {

    private boolean solid;
    private boolean pressurized;

    public MixedDryLiquid(int size, backend.storage.administration.Customer owner, Collection<Hazard> hazards, boolean solid, boolean pressurized) {
        super(size, owner, hazards);
        this.solid = solid;
        this.pressurized = pressurized;
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

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public void setPressurized(boolean pressurized) {
        this.pressurized = pressurized;
    }
}
