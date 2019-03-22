package backend.fxmlBackend;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServerTest {

    @Test
    void run() throws IOException {

        //given
        FXMLWarehouse fw = new FXMLWarehouse(100,10000);
        Socket mockSocket = mock(Socket.class);
        DataInputStream mockInputStream = mock(DataInputStream.class);
        DataOutputStream mockOutputStream = mock(DataOutputStream.class);
        Server server = new Server(fw, mockSocket);

        when(mockSocket.getInputStream()).thenReturn(mockInputStream);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);
        when(mockInputStream.read()).thenReturn(0x2);

        //when
        server.run();

        //then
        ServerUtility.toBytes(fw.getAllCargo());
        verify(mockOutputStream).write(ServerUtility.toBytes(fw.getAllCargo()),0, 646);





    }
}