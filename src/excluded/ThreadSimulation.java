import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import backend.storage.cargo.Cargo;
import userinterface.dialogs.Dialogs;

import java.util.Scanner;

public class ThreadSimulation {


    //from Warehouse
    private ExcludedWarehouse(){
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

    //from this class
    public static void main(String[] args) throws InterruptedException{

        Warehouse w1 = new Warehouse(15, 100);
        Warehouse w2 = new Warehouse(15, 150);


        final Administration administration = Administration.getInstance();

        administration.warehouses.add(w1);
        administration.warehouses.add(w2);

        Thread store = new Thread(() -> {
            try {
                administration.store();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread move = new Thread(() -> {
            try {
                administration.move();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        store.start();
        move.start();

        store.join();
        move.join();



    }
}
