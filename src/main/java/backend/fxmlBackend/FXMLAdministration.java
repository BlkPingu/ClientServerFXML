package backend.fxmlBackend;

import backend.serialization.SaveObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class FXMLAdministration {

    final ObservableList<TableObject> tableData = FXCollections.observableArrayList();
    final CopyOnWriteArrayList<SaveObject> saveObjects = new CopyOnWriteArrayList<>();

    public static byte[] toBytes(CopyOnWriteArrayList<SaveObject> obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static SaveObject bytes2SaveObject (byte[] bytes) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);

        return (SaveObject) in.readObject();
    }

    ArrayList<SaveObject> tableObjectList2SaveObjectList(){
        ArrayList<SaveObject> saveObjectArrayList = new ArrayList<>();
        for (TableObject to : tableData)
            saveObjectArrayList.add(tableObject2SaveObject(to));
        return saveObjectArrayList;
    }


    static SaveObject tableObject2SaveObject(TableObject to){
        return new SaveObject(to.getType(), to.getCustomer(), to.getPosition(),
                to.getSize(), to.isRadioactive(), to.isFlammable(), to.isToxic(), to.isExplosive(), to.getProperties());
    }
    private TableObject SaveObject2TableObject(SaveObject so){
        return new TableObject(so.getType(), so.getCustomer(), so.getPosition(),
                so.getSize(), so.getRadioactive(), so.getFlammable(), so.getToxic(), so.getExplosive(), so.getProperties());

    }


    private CopyOnWriteArrayList<SaveObject> toObject(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return (CopyOnWriteArrayList<SaveObject>) o;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return null;
    }


    private byte[] toBytes(SaveObject so) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(so);
            out.flush();
            return bos.toByteArray();
        }  finally {
            bos.close();
        }
    }

    private void sendObject(Socket socket, SaveObject saveObject){
        try {
            new DataOutputStream(socket.getOutputStream()).write(toBytes(saveObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCode(Socket socket, char code){
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeChar(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    void addSingleServerData(SaveObject saveObject) throws IOException {
        Socket client = new Socket("localhost", 1337);
        sendCode(client,'A');
        sendObject(client,saveObject);
        client.close();
    }

    void getAllServerData() throws IOException {
        Socket client = new Socket("localhost", 1337);
        sendCode(client,'B');
        populateClientList(toObject(new DataInputStream(client.getInputStream()).readAllBytes()));
        client.close();

    }

    void deleteSingleServerData(SaveObject so) throws IOException {
        Socket client = new Socket("localhost", 1337);
        sendCode(client,'C');
        sendObject(client,so);
        client.close();
    }

    void deleteAllServerData() throws IOException {
        Socket client = new Socket("localhost", 1337);
        sendCode(client,'D');
        tableData.clear();
        saveObjects.clear();
        client.close();
    }

    void populateClientList(CopyOnWriteArrayList<SaveObject> tol){
        tableData.clear();
        saveObjects.clear();
        for (SaveObject so: tol){
            saveObjects.add(so);
            tableData.add(SaveObject2TableObject(so));
        }
    }
}
