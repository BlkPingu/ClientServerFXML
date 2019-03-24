package backend.fxmlBackend.Server;

import backend.serialization.SaveObject;
import java.io.*;
import java.util.ArrayList;

public class ServerUtility {

    //final CopyOnWriteArrayList<SaveObject> saveObjects = new CopyOnWriteArrayList<>();



    public static byte[] toBytes(ArrayList<SaveObject> obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }





    /*
    private ArrayList<SaveObject> toObject(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return (ArrayList<SaveObject>) o;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return null;
    }


    public static SaveObject bytes2SaveObject (byte[] bytes) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);

        return (SaveObject) in.readObject();
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
    */
}
