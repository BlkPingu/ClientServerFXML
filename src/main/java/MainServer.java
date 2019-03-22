import backend.fxmlBackend.FXMLWarehouse;
import backend.fxmlBackend.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public static void main(String[] args) {

        FXMLWarehouse warehouse = new FXMLWarehouse(100, 100000000);

        try (ServerSocket serverSocket = new ServerSocket(1337)) {


            while (true) {
                Socket socket = serverSocket.accept();
                backend.fxmlBackend.Server s = new backend.fxmlBackend.Server(warehouse, socket);
                System.out.println("Client: " + socket.getInetAddress() + ":" + socket.getPort());
                Thread t = new Thread(s);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}