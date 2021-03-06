package backend.fxmlBackend.Server;

import backend.fxmlBackend.FXMLWarehouse;
import backend.serialization.SaveObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    FXMLWarehouse warehouse;
    ObjectInputStream fromClient;
    ObjectOutputStream toClient;


    public Server(FXMLWarehouse warehouse){
        this.warehouse = warehouse;
    }



    public Object readObject(ObjectInputStream fromClient) throws IOException, ClassNotFoundException {
        return fromClient.readObject();
    }

    public void writeObject(ObjectOutputStream toClient, Object object) throws IOException {
        toClient.writeObject(object);
        toClient.flush();
    }




    @Override
    public void run(){
        try{

            ServerSocket serverSocket = new ServerSocket(1337);
            Socket socket = serverSocket.accept();
            System.out.println("Client: " + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println("Waiting for Client");

            fromClient = new ObjectInputStream(socket.getInputStream());
            toClient = new ObjectOutputStream(socket.getOutputStream());



            while(true) {
                //int command = (Integer) fromClient.readObject();
                int command = (Integer) readObject(fromClient);



                System.out.println("backend.fxmlBackend.Server.Server | command: " + command);
                switch (command) {
                    case 0x1:
                        //add
                        System.out.println("backend.fxmlBackend.Server.Server | command: add");
                        SaveObject so = (SaveObject) fromClient.readObject();
                        warehouse.addObject(so);
                        System.out.println("backend.fxmlBackend.Server.Server | Added new Object: " + so + " | List is now of size: " + warehouse.getAllCargo().size());
                        break;
                    case 0x2:
                        //get server data
                        System.out.println("command: get server data");




                        //toClient.writeObject(warehouse.getAllCargo());
                        writeObject(toClient, warehouse.getAllCargo());



                        System.out.println("Data List sent. Size: " + warehouse.getAllCargo().size());
                        break;
                    case 0x3:
                        //delete
                        System.out.println("command: delete");
                        SaveObject deleted = (SaveObject) fromClient.readObject();
                        for (SaveObject m : warehouse.getAllCargo()) {
                            System.out.println(warehouse.getAllCargo().indexOf(m) + " - " + m.getCustomer() + " / " + m.getPosition());


                            if (m.getCustomer().equals(deleted.getCustomer()) && (m.getPosition().equals(deleted.getPosition()))) {
                                System.out.println(m);
                                warehouse.deleteCargo(m);
                            }
                        }
                        break;
                    case 0x4:
                        //deleteAll
                        System.out.println("command: addAll");
                        warehouse.deleteAll();
                        break;
                    default:
                        System.out.println("ERROR! Something weird happened!");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        FXMLWarehouse fxmlWarehouse = new FXMLWarehouse(100, 10000);
        Thread t = new Server(fxmlWarehouse);
        t.start();
    }
}