package backend.ioc.events.listeners;


import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import backend.ioc.events.InputEvent;
import backend.ioc.events.listeners.interfaces.InputEventListener;

public class ExitUi implements InputEventListener {

    @Override
    public void onInputEvent(Administration administration, InputEvent event) {
        if(null!=event.getText() && event.getText().equals("exit"))
            System.exit(0);
    }
}
