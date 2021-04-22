
package store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import store.utils.MenuGenerator;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainScreen extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root = FXMLLoader.load(MainScreen.class.getResource("mainscreen.fxml"),
                ResourceBundle.getBundle("store.mainscreen"));


        Scene scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Store");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}