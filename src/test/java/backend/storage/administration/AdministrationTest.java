package backend.storage.administration;

import backend.storage.administration.Administration;
import backend.storage.administration.Customer;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AdministrationTest {



    private Administration administration;

    @BeforeEach
    void setUp() {
        administration = new Administration();
    }

    @Test
    void getCustomerAdministration() {
        assertNotNull(administration.getCustomerAdministration());
    }
}