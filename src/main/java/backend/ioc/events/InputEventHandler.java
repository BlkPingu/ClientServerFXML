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

    void handle(InputEvent event, Administration administration){
        for (InputEventListener listener : listenerList)
            listener.onInputEvent(administration, event);
    }
}
