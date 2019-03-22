
import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import backend.ioc.events.ConsoleReader;
import userinterface.dialogs.Dialogs;
import backend.ioc.events.InputEventHandler;
import backend.ioc.events.listeners.*;
import backend.ioc.events.listeners.interfaces.InputEventListener;

public class Console {

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse(10, 100);
        Administration administration = Administration.getInstance();
        administration.warehouses.add(warehouse);

        Dialogs.options();

        ConsoleReader r=new ConsoleReader();
        InputEventHandler handler=new InputEventHandler();

        InputEventListener deleteCargo = new DeleteCargo();
        InputEventListener exitUi =new NewCargo();
        InputEventListener newCargo =new ExitUi();
        InputEventListener outAllCargo =new OutAllCargo();
        InputEventListener storeCargo =new StoreCargo();

        handler.add(deleteCargo);
        handler.add(exitUi);
        handler.add(newCargo);
        handler.add(outAllCargo);
        handler.add(storeCargo);


        r.setHandler(handler);
        r.start(warehouse, administration);
    }
}
