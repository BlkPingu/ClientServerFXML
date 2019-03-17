package backend.fxmlBackend;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import backend.serialization.SaveObject;

public class Warehouse {

    private CopyOnWriteArrayList<SaveObject> tableObjects = new CopyOnWriteArrayList<>(
                List.of(
                new SaveObject("Liquid", "Dave", 5, 10, true, true, false, true, "P--"),
                new SaveObject("Boxed", "Frank", 8, 5, false, true, false, true, "--F"),
                new SaveObject("Boxed", "Frank", 3, 15, false, true, false, true, "--F"),
                new SaveObject("Dry", "James", 6, 2, false, true, false, true, "-S-")
                )
            );


    public CopyOnWriteArrayList<SaveObject> getTableObjects() {
        return tableObjects;
    }


    /*

    */
}
