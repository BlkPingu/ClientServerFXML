package backend.fxmlBackend;

import backend.serialization.SaveObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Server implements Runnable {
    private Socket socket;
    private FXMLWarehouse warehouse;


    public Server(FXMLWarehouse warehouse, Socket socket){
        this.socket=socket;
        this.warehouse = warehouse;
    }








    @Override
    public void run(){


        System.out.println("Waiting for Client");

        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            int command = in.read();
            System.out.println("command: " + command);
            switch (command) {
                case 0x1:
                    //add
                    System.out.println("command: add");


                    SaveObject so = ServerUtility.bytes2SaveObject(in.readAllBytes());
                    warehouse.addObject(so);
                    System.out.println("Added new Object: " + so + " | List is now of size: " +  warehouse.getAllCargo().size());
                    break;
                case 0x2:
                    //get server data
                    System.out.println("command: get server data");
                    out.write(ServerUtility.toBytes(warehouse.getAllCargo()));
                    System.out.println("Data List sent. Size: " + warehouse.getAllCargo().size());
                    break;
                case 0x3:
                    //delete
                    System.out.println("command: delete");
                    SaveObject deleted = ServerUtility.bytes2SaveObject(in.readAllBytes());
                    for(SaveObject m : warehouse.getAllCargo()){
                        System.out.println(warehouse.getAllCargo().indexOf(m) + " - "+ m.getCustomer() + " / " + m.getPosition());


                        if(m.getCustomer().equals(deleted.getCustomer()) && (m.getPosition().equals(deleted.getPosition()))){
                            System.out.println(m);
                            warehouse.deleteCargo(m);
                        }
                    }
                    //System.out.println("Deleted the Object with Customer Name: " + deleted.getCustomer() + " | Pos:" + deleted.getPosition());
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public Socket getSocket() {
        return socket;
    }

    FXMLWarehouse getWarehouse() {
        return warehouse;
    }
}