package backend.ioc.events.listeners.interfaces;


import backend.ioc.events.InputEvent;
import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import java.util.EventListener;

public interface InputEventListener extends EventListener {
    void onInputEvent(Administration administration, InputEvent event);
}
