import backend.fxmlBackend.Server.Server;

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> Server.main(args));
        t1.start();
        Thread t2 = new Thread(() -> Client.main(args));
        t2.start();
    }
}
