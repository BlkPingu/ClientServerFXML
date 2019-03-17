package backend.ioc.events.listeners;

import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import backend.ioc.events.listeners.interfaces.InputEventListener;
import backend.ioc.events.InputEvent;
import userinterface.dialogs.Dialogs;

import java.util.Scanner;

public class StoreCargo implements InputEventListener {




    @Override
    public void onInputEvent(Administration administration, InputEvent event) {
        Scanner scanner = new Scanner(System.in);
        if (null != event.getText() && event.getText().equals("store")){

            System.out.println("Input => '" + event.getText() + "'");
            Dialogs.enterCargoNumber();
            int warehouseNumber = scanner.nextInt();

            Dialogs.enterWarehouseNumber();
            int cargoNumber = scanner.nextInt();


            administration.warehouses.get(warehouseNumber).storeCargoFromAdministration(administration, cargoNumber);

        }else{new NullPointerException();}
    }
}

