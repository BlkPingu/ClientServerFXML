package backend.serialization;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;


//https://www.youtube.com/watch?v=H-aTpt4NG-s

public class JBP {
    FileOutputStream fos;
    XMLEncoder encoder;

    FileInputStream fis;
    XMLDecoder decoder;

    final String pathname = "./saveObject.xml";



    public void save(ArrayList<SaveObject> items) throws IOException{
        fos = new FileOutputStream(new File(pathname));
        encoder = new XMLEncoder(fos);

        encoder.writeObject(items);
        encoder.close();
        fos.close();
    }


    public ArrayList<SaveObject> load() throws IOException{

        fis = new FileInputStream(new File("./saveObject.xml"));
        decoder = new XMLDecoder(fis);

        ArrayList<SaveObject> sol = (ArrayList<SaveObject>) decoder.readObject();

        decoder.close();
        fis.close();

        return sol;
    }


    public static void main(String[] args) throws IOException {

        JBP jbp = new JBP();
        ArrayList<SaveObject> sol = new ArrayList<>();

        SaveObject s1 = new SaveObject("type", "customer1", 1, 11, true, true, false, false, "properties");
        SaveObject s2 = new SaveObject("type1", "customer2", 2, 12, true, true, false, false, "properties");
        SaveObject s3 = new SaveObject("type2", "customer3", 3, 13, true, true, false, false, "properties");

        sol.add(s1);
        sol.add(s2);
        sol.add(s3);


        jbp.save(sol);

        System.out.println(sol.toString());
        System.out.println(jbp.load().toString());

        //----

        /*
        SaveObject save = new SaveObject("type", "customer", 1, 5, true, true, false, false, "properties");
        FileOutputStream fos = new FileOutputStream(new File("./saveObject.xml"));
        XMLEncoder encoder = new XMLEncoder(fos);
        encoder.writeObject(save);
        encoder.close();
        fos.close();


        FileInputStream fis = new FileInputStream(new File("./saveObject.xml"));
        XMLDecoder decoder = new XMLDecoder(fis);

        SaveObject read = (SaveObject)decoder.readObject();
        decoder.close();
        fis.close();

        System.out.println(read);

       */
    }
}
























