
import java.io.*;
import java.net.*;

import backend.fxmlBackend.FXMLAdministration;
import backend.serialization.SaveObject;
import backend.fxmlBackend.FXMLWarehouse;


public class Server implements Runnable {
    private Socket socket;
    private FXMLWarehouse warehouse;


    private Server(FXMLWarehouse warehouse, Socket socket){
        this.socket=socket;
        this.warehouse = warehouse;
    }








    @Override public void run(){


        System.out.println("Waiting for Client");

        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            char command = in.readChar();
            System.out.println(command);
            switch (command) {
                case 'A':
                    // add
                    SaveObject so = FXMLAdministration.bytes2SaveObject(in.readAllBytes());
                    warehouse.getServerObjectsList().add(so);
                    System.out.println("Added new Object: " + so + " | List is now of size: " +  warehouse.getServerObjectsList().size());
                    break;
                case 'B':
                    //get server data
                    out.write(FXMLAdministration.toBytes(warehouse.getServerObjectsList()));
                    System.out.println("Data List sent. Size: " + warehouse.getServerObjectsList().size());
                    break;
                case 'C':
                    //delete
                    SaveObject deleted = FXMLAdministration.bytes2SaveObject(in.readAllBytes());
                    for(SaveObject m : warehouse.getServerObjectsList()){
                        System.out.println(warehouse.getServerObjectsList().indexOf(m) + " - "+ m.getCustomer() + " / " + m.getPosition());


                        if(m.getCustomer().equals(deleted.getCustomer()) && (m.getPosition().equals(deleted.getPosition()))){
                            System.out.println(warehouse.getServerObjectsList().contains(m));
                            System.out.println(m);
                            warehouse.getServerObjectsList().remove(m);
                            System.out.println(warehouse.getServerObjectsList().contains(m));
                        }
                    }
                    //System.out.println("Deleted the Object with Customer Name: " + deleted.getCustomer() + " | Pos:" + deleted.getPosition());
                    break;
                case 'D':
                    warehouse.getServerObjectsList().clear();
                    break;
                default:
                    System.out.println("ERROR! Something weird happened!");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        FXMLWarehouse warehouse = new FXMLWarehouse();

        try(ServerSocket serverSocket=new ServerSocket(1337)) {


            while(true){
                Socket socket = serverSocket.accept();
                Server s = new Server(warehouse,socket);
                System.out.println("Client: "+socket.getInetAddress()+":"+socket.getPort());
                Thread t = new Thread(s);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}