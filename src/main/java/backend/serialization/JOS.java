package backend.serialization;

import backend.serialization.SaveObject;

import java.io.*;
import java.util.ArrayList;



public class JOS {

    private static String filename = "./JOS.txt";

    public static void save(ArrayList<SaveObject> items) throws IOException{
        try (ObjectOutputStream oos=new ObjectOutputStream(
                new FileOutputStream(filename))){
            oos.writeObject(items);
        }
    }


    public static ArrayList<SaveObject> load() throws IOException, ClassNotFoundException{
        try (ObjectInputStream ois=new ObjectInputStream(
                new FileInputStream(filename))){
            return (ArrayList<SaveObject>)ois.readObject();
        }
    }
}
