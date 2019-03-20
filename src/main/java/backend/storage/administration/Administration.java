package backend.storage.administration;

import backend.enums.Hazard;
import backend.exceptions.NoWarehouseException;
import backend.exceptions.NotInCollectionException;
import backend.exceptions.NullValueException;
import backend.storage.cargo.Cargo;
import userinterface.dialogs.Dialogs;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
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
            throw new NoWarehouseException("FXMLWarehouse List Empty");
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

    public HashMap<Integer, Customer> getCustomerAdministration() {
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



    public void store() throws InterruptedException {
        Warehouse warehouse = warehouses.get(0);
        String name = "Storage Thread";


        synchronized (this) {
            while (true) {
                Cargo newCargo = randomCargogenerator();
                Dialogs.tryingToStoreCargoInWarehouse("Storage Thread", newCargo, warehouse);
                if (!warehouse.hasNoSpace(newCargo) && warehouse.newCargo(newCargo)) {
                    Dialogs.successfullyStoredCargoInWarehouse(name, newCargo, warehouse);
                } else {
                    System.out.println("Notifying Move");
                    notify();
                    wait();
                }
            }

        }
    }

    public void move() throws InterruptedException {
        String name = "Move Thread";
        Thread.sleep(500);
        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        while(!done){
            synchronized (this) {
                Warehouse mainWarehouse = warehouses.get(0);
                Cargo oldestCargo = mainWarehouse.getOldestCargoInWarehouse();


                Dialogs.tryingToStoreCargoInWarehouse(name, oldestCargo, mainWarehouse);

                Warehouse warehouseWithSpace = findWarehouseWithSpace(oldestCargo);

                printAllWarehouseVolumeStored();
                System.out.println("Move Cargo " + oldestCargo + " to " + warehouseWithSpace + "? [ENTER]");

                //deactivate to run without enter
                scanner.nextLine();

                if (warehouseWithSpace != null) {
                    warehouseWithSpace.newCargo(oldestCargo);
                    mainWarehouse.deleteCargo(Warehouse.getKeyByValueFromCargo(mainWarehouse.allCargo, oldestCargo));
                    notify();
                    wait();
                }else{
                    System.out.println("DONE");
                    done = true;
                }

            }
        }
    }



}

