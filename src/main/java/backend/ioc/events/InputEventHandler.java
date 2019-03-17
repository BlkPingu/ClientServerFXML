package backend.ioc.events;

import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;
import backend.ioc.events.listeners.interfaces.InputEventListener;

import java.util.ArrayList;
import java.util.List;

public class InputEventHandler {




    private List<InputEventListener> listenerList=new ArrayList<InputEventListener>();
    public void add(InputEventListener listener) {
        this.listenerList.add(listener);
    }
    public void remove(InputEventListener listener) {
        this.listenerList.remove(listener);
    }
    public void handle(InputEvent event, Warehouse warehouse, Administration administration){
        for (InputEventListener listener : listenerList)
            listener.onInputEvent(administration, event);
    }
}
