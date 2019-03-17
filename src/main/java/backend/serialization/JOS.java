package backend.serialization;

import backend.serialization.SaveObject;

import java.io.*;
import java.util.ArrayList;



public class JOS {

    public static void save(String filename, ArrayList<SaveObject> items){
        try (ObjectOutputStream oos=new ObjectOutputStream(
                new FileOutputStream(filename))){
            oos.writeObject(items);
        } catch (FileNotFoundException e) {}
        catch (IOException e) {}
    }


    public static ArrayList<SaveObject> load(String filename){
        try (ObjectInputStream ois=new ObjectInputStream(
                new FileInputStream(filename))){
            return (ArrayList<SaveObject>)ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("JBP Exception");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        return null;
    }
}
