package backend.storage.administration;

public class Customer implements backend.storage.administration.interfaces.Customer {

    String name;

    public Customer( String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
