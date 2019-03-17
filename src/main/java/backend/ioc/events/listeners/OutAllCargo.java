package backend.ioc.events.listeners;

import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import backend.ioc.events.listeners.interfaces.InputEventListener;

import backend.ioc.events.InputEvent;

public class OutAllCargo implements InputEventListener {
    /**
     * @param warehouse
     * Executes
     */
    public void outAllCargo(Warehouse warehouse) {
        warehouse.getAllCargoWithPosition();
    }

    @Override
    public void onInputEvent(Administration administration, InputEvent event) {
        if (null != event.getText() && event.getText().equals("printall")){

            System.out.println("Input => '" + event.getText() + "'");
            outAllCargo(administration.warehouses.get(0));

        }else{new NullPointerException();}
    }
}
