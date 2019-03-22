package backend.storage.administration;

import backend.enums.Hazard;
import backend.exceptions.NoWarehouseException;
import backend.exceptions.NotInCollectionException;
import backend.exceptions.NullValueException;
import backend.storage.cargo.Cargo;
import userinterface.dialogs.Dialogs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Administration{

    private HashMap<Integer, Customer> customerAdministration;
    public ArrayList<Warehouse> warehouses;
    public CopyOnWriteArrayList<Cargo> cargoList;

    private static Administration INSTANCE;

    public Administration() {
        customerAdministration = new HashMap<>();
        warehouses = new ArrayList<>();
        cargoList = new CopyOnWriteArrayList<>();
    }

    public static Administration getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Administration();
        }

        return INSTANCE;
    }



    private synchronized boolean validateMinOneWarehouse() throws NoWarehouseException {
        if(warehouses.isEmpty()){
            throw new NoWarehouseException("Warehouse List Empty");
        }
        else return true;
    }



    private synchronized Cargo randomCargogenerator(){
        return new Cargo(generate(5,15), new Customer("Name"), Arrays.asList(Hazard.explosive, Hazard.flammable));
    }


    private static int generate(int min,int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    private Warehouse findWarehouseWithSpace(Cargo cargo){
        for (Warehouse warehouse: warehouses) {
            if (!warehouse.hasNoSpace(cargo))
                return warehouse;
        }
        return null;
    }

    HashMap<Integer, Customer> getCustomerAdministration() {
        return customerAdministration;
    }


    private void addCustomer(Customer customer) {

        if(customer != null){
            int key = 0;
            boolean done = false;
            while(!done){
                if (customerAdministration.containsKey(key)) {
                    key++;
                } else {
                    customerAdministration.put(key, customer);
                    done = true;
                }
            }
        }else{
            try {
                throw new NullValueException("Customer is null:" + customer);
            } catch (NullValueException e) {
                e.printStackTrace();
            }
        }

    }

    private void deleteCustomer(int id){
        if(customerAdministration.keySet().contains(id)){
            customerAdministration.remove(id);
        }else{
            try {
                throw new NotInCollectionException("Not a key:" + id);
            } catch (NotInCollectionException e) {
                e.printStackTrace();
            }
        }
    }

    private void printAllWarehouseVolumeStored(){
        for (Warehouse warehouse: warehouses) {
            Dialogs.warehouseCapacityLevel(warehouse.volumeStored, warehouse.capacity);
        }
    }
}

