package backend.ioc.events.listeners;

import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import backend.ioc.events.listeners.interfaces.InputEventListener;
import backend.ioc.events.InputEvent;
import java.util.Scanner;

public class DeleteCargo implements InputEventListener {

    Scanner scanner = new Scanner(System.in);

    public void deleteCargo(Warehouse warehouse){
        warehouse.allCargo.forEach((k,v) -> {
            System.out.println("[" + k + "] " + v.toString());
        });
        System.out.print("Which number?: ");
        try {
            int cargoNumber = scanner.nextInt();
            warehouse.deleteCargo(cargoNumber);
        }catch (Exception invalid){
            System.out.println("Cargo Number does not exist.");
        }
    }

    @Override
    public void onInputEvent(Administration administration, InputEvent event) {
        if (null != event.getText() && event.getText().equals("delete")){

            System.out.println("Input => '" + event.getText() + "'");
            deleteCargo(administration.warehouses.get(0));
            System.out.println("Cargo at Position deleted");

        }else{new NullPointerException();}
    }
}
