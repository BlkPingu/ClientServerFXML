package backend.storage.administration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CargoLogisticsTest {


    CargoLogistics cargoLogistics;


    @BeforeEach
    void setUp() {
        cargoLogistics = new CargoLogistics(LocalDateTime.now(), 123);
    }

    @Test
    void getDate() {
        assertEquals(LocalDateTime.now(), cargoLogistics.getDate());
    }

    @Test
    void getPosition() {
        assertEquals(123, cargoLogistics.getPosition());
    }
}