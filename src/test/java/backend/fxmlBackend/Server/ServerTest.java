package backend.fxmlBackend.Server;

import backend.fxmlBackend.FXMLWarehouse;
import backend.serialization.SaveObject;
import backend.storage.administration.Warehouse;
import javafx.fxml.FXML;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServerTest {



    /*
    @Test
    void run() throws IOException, ClassNotFoundException {


        // Ich habe ihnen hier mal meinen Ansatz aufgeschrieben wie ich testen würde.
        // Problem war dass writeObject final ist und Mockito keine finals kann.
        //
        // Ich habe das versucht hiermit:
        // https://www.baeldung.com/mockito-final
        // zu umgehen, aber ohne Erfolg.
        //
        //Ich habe ihnen jedenfalls mal meinen Ansatz da gelassen der auch funktionieren sollte wenn
        // Mockito mit finals umgehen könnte.


        //given
        FXMLWarehouse fw = new FXMLWarehouse(100,10000);
        Server server = mock(Server.class);

        //when
        when(server.fromClient.readObject()).thenReturn(0x2);

        server.start();

        //then
        verify(server).toClient.writeObject(fw.getAllCargo());

    }

    */


    @Test
    void run() throws IOException, ClassNotFoundException {

        //given
        FXMLWarehouse warehouse = spy(new FXMLWarehouse(100, 1000));
        Server server = spy(new Server(warehouse));

        //when
        doReturn(0x2).when(server).readObject(null);

        server.start();

        //then
        verify(server).writeObject(null, warehouse.getAllCargo());
    }
}