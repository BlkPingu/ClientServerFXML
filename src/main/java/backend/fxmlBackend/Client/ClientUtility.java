package backend.fxmlBackend.Client;

import backend.fxmlBackend.TableObject;
import backend.serialization.SaveObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientUtility {

    private Socket client;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    public ClientUtility() throws IOException {
        client = new Socket("localhost", 1337);
        toServer = new ObjectOutputStream(client.getOutputStream());
        fromServer = new ObjectInputStream(client.getInputStream());

    }

    public final ObservableList<TableObject> tableData = FXCollections.observableArrayList();


    private void sendCode(int code) throws IOException {
        toServer.writeObject(code);
        toServer.flush();
    }



    public void addSingleServerData(SaveObject saveObject) throws IOException {
        sendCode(0x1);
        toServer.writeObject(saveObject);
        toServer.flush();
    }

    public void getAllServerData() throws IOException, ClassNotFoundException {
        sendCode(0x2);
        populateClientList((ArrayList<SaveObject>) fromServer.readObject());

    }

    public void deleteSingleServerData(SaveObject saveObject) throws IOException {
        sendCode(0x3);
        toServer.writeObject(saveObject);
        toServer.flush();
    }

    public void deleteAllServerData() throws IOException {
        sendCode(0x4);
        tableData.clear();
    }

    private void populateClientList(ArrayList<SaveObject> tol){
        tableData.clear();
        for (SaveObject so: tol){
            tableData.add(SaveObject2TableObject(so));
        }
    }

    public ArrayList<SaveObject> tableObjectList2SaveObjectList(){
        ArrayList<SaveObject> saveObjectArrayList = new ArrayList<>();
        for (TableObject to : tableData)
            saveObjectArrayList.add(tableObject2SaveObject(to));
        return saveObjectArrayList;
    }

    public static SaveObject tableObject2SaveObject(TableObject to){
        return new SaveObject(to.getType(), to.getCustomer(), to.getPosition(),
                to.getSize(), to.isRadioactive(), to.isFlammable(), to.isToxic(), to.isExplosive(), to.getProperties());
    }

    private static TableObject SaveObject2TableObject(SaveObject so){
        return new TableObject(so.getType(), so.getCustomer(), so.getPosition(),
                so.getSize(), so.getRadioactive(), so.getFlammable(), so.getToxic(), so.getExplosive(), so.getProperties());

    }
}
