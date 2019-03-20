package administration;


import backend.storage.administration.Customer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {


    backend.storage.administration.Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("TheName");
        customer = new Customer(null);

    }

    @Test
    void getName() {
        assertSame(customer.getName(), "TheName");
    }
}