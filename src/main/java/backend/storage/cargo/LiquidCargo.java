package backend.storage.cargo;


import backend.enums.Hazard;

import java.util.Collection;

public class LiquidCargo extends Cargo implements backend.storage.cargo.interfaces.LiquidCargo {

    private boolean pressurized;

    public LiquidCargo(int size, backend.storage.administration.Customer owner, Collection<Hazard> hazards, boolean pressurized) {
        super(size, owner, hazards);
        this.pressurized = pressurized;
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

    public void setPressurized(boolean pressurized) {
        this.pressurized = pressurized;
    }
}
