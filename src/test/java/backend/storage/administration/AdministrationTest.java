package backend.storage.administration;

import backend.storage.administration.Administration;
import backend.storage.administration.Customer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AdministrationTest {



    private Administration administration = new Administration();

    private Customer customer1 = new Customer("foo");
    private Customer customer2 = new Customer("bar");


    @BeforeEach
    void setUp() {
        administration.getCustomerAdministration().clear();
    }

    /*
    @Test
    void addCustomer1() {
        administration.addCustomer(customer1);
        assertTrue(administration.getCustomerAdministration().containsKey(0));
        assertTrue(administration.getCustomerAdministration().containsValue(customer1));


    }

    @Test
    void addCustomer2() {
        administration.addCustomer(customer1);
        administration.addCustomer(customer2);

        assertTrue(administration.getCustomerAdministration().containsKey(0));
        assertTrue(administration.getCustomerAdministration().containsKey(1));
        assertTrue(administration.getCustomerAdministration().containsValue(customer1));
        assertTrue(administration.getCustomerAdministration().containsValue(customer2));
    }

    @Test
    void addCustomer3() {
        administration.addCustomer(null);
        assertTrue(administration.getCustomerAdministration().isEmpty());
    }

    @Test
    void deleteCustomer1() {
        administration.addCustomer(customer1);

        assertTrue(administration.getCustomerAdministration().containsKey(0));

        administration.deleteCustomer(0);

        assertFalse(administration.getCustomerAdministration().containsValue(customer1));
        assertTrue(administration.getCustomerAdministration().isEmpty());
    }

    @Test
    void deleteCustomer2() {

        administration.addCustomer(customer1);
        administration.addCustomer(customer2);

        assertTrue(administration.getCustomerAdministration().containsKey(0));
        assertTrue(administration.getCustomerAdministration().containsKey(1));

        administration.deleteCustomer(0);

        assertTrue(administration.getCustomerAdministration().containsValue(customer2));
        assertFalse(administration.getCustomerAdministration().containsValue(customer1));
    }

    @Test
    void deleteCustomer3() {

        assertTrue(administration.getCustomerAdministration().isEmpty());

        administration.deleteCustomer(0);

        assertTrue(administration.getCustomerAdministration().isEmpty());
    }
    */

    @Test
    void getInstance() {
    }

    @Test
    void getCustomerAdministration() {
    }

    @Test
    void store() {
    }

    @Test
    void move() {
    }

    @Test
    void getCustomerAdministration1() {
    }
}