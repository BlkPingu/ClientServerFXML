package backend.ioc.events;

import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;

import java.util.Scanner;

public class ConsoleReader {
    private InputEventHandler handler;
    public void setHandler(InputEventHandler handler) {
        this.handler = handler;
    }
    public void start(Warehouse warehouse, Administration administration){
        Scanner s=new Scanner(System.in);
            do {
                System.out.print("Enter Option: ");
                InputEvent e = new InputEvent(this,s.next());
                if(null != this.handler) handler.handle(e, administration);
            }while (true);

    }
}
