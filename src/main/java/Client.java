import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application{

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("cargoTable.fxml"));
        primaryStage.setTitle("Warehouseadministration");
        primaryStage.setScene(new Scene(root,815 ,500));
        primaryStage.show();
    }
}