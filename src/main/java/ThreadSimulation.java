import backend.storage.administration.Administration;
import backend.storage.administration.Warehouse;

public class ThreadSimulation {
    public static void main(String[] args) throws InterruptedException{

        Warehouse w1 = new Warehouse(15, 100);
        Warehouse w2 = new Warehouse(15, 150);


        final Administration administration = Administration.getInstance();

        administration.warehouses.add(w1);
        administration.warehouses.add(w2);

        Thread store = new Thread(() -> {
            try {
                administration.store();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread move = new Thread(() -> {
            try {
                administration.move();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        store.start();
        move.start();

        store.join();
        move.join();



    }
}
