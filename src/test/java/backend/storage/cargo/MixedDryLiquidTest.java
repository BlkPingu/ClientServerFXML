package cargo;

import backend.enums.Hazard;
import backend.storage.cargo.MixedDryLiquid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import backend.storage.administration.Customer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MixedDryLiquidTest {

    private MixedDryLiquid cargo;
    private Customer customer;
    private List<Hazard> hazards;

    @BeforeEach
    void setUp() {
        hazards = new ArrayList<>();
        hazards.add(Hazard.flammable);
        hazards.add(Hazard.radioactive);
        customer = new Customer("Name");
        cargo = new MixedDryLiquid(1, customer, hazards, true, true);
    }

    @Test
    void getSize() {
        assertEquals(1, cargo.getSize());
    }

    @Test
    void getOwner() {
        assertEquals(customer, cargo.getOwner());
    }

    @Test
    void getHazards() {
        assertEquals(hazards, cargo.getHazards());
    }

    @Test
    void isSolid() {
        assertTrue(cargo.isSolid());
    }

    @Test
    void isPressurized() {
        assertTrue(cargo.isPressurized());
    }
}