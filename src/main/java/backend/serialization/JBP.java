package backend.serialization;
import java.io.*;
import java.util.ArrayList;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;


//https://www.youtube.com/watch?v=H-aTpt4NG-s

public class JBP {

    private final static String filename = "./saveObject.xml";

    public static void save(ArrayList<SaveObject> items) throws IOException{
        FileOutputStream fos = new FileOutputStream(new File(filename));
        XMLEncoder encoder = new XMLEncoder(fos);

        encoder.writeObject(items);
        encoder.close();
        fos.close();
    }


    public static ArrayList<SaveObject> load() throws IOException{

        FileInputStream fis = new FileInputStream(new File(filename));
        XMLDecoder decoder = new XMLDecoder(fis);

        ArrayList<SaveObject> sol = (ArrayList<SaveObject>) decoder.readObject();

        decoder.close();
        fis.close();

        return sol;
    }

}
























