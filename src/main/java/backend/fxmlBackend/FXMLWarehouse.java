package backend.fxmlBackend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import backend.serialization.SaveObject;
import backend.storage.administration.Warehouse;

public class FXMLWarehouse {

    Warehouse warehouse;

    public boolean addObject(SaveObject saveObject){
        return warehouse.newCargo(CargoWrapper.SaveObject2Cargo(saveObject));
    }

    public void deleteCargo(SaveObject saveObject){
        warehouse.deleteCargo(Warehouse.getKeyByValueFromCargo(warehouse.allCargo, CargoWrapper.SaveObject2Cargo(saveObject)));
    }


    public SaveObject getSingleCargo(SaveObject saveObject){
        SaveObject so = CargoWrapper.Cargo2SaveObject(warehouse.allCargo.get(Warehouse.getKeyByValueForServer(warehouse.allCargo, CargoWrapper.SaveObject2Cargo(saveObject))));
        if(so == null){
            System.out.println("no such cargo found");
        }
        return so;
    }

    public FXMLWarehouse(int dimensions, int size) {
        this.warehouse = new Warehouse(dimensions, size);
        addObject(new SaveObject("Liquid", "Dave", 0, 10, true, true, false, true, "P--"));
        addObject(new SaveObject("Boxed", "Frank", 0, 5, false, true, false, true, "--F"));
        addObject(new SaveObject("Boxed", "Frank", 0, 15, false, true, false, true, "--F"));
        addObject(new SaveObject("Dry", "James", 0, 2, false, true, false, true, "-S-"));
    }

    public void deleteAll(){
        warehouse.allCargo.clear();
    }

    public ArrayList<SaveObject> getAllCargo() {
        ArrayList<SaveObject> saveObjectList = new ArrayList<>();
        warehouse.allCargo.values().forEach(c -> saveObjectList.add(CargoWrapper.Cargo2SaveObject(c)));
        return saveObjectList;
    }
}
