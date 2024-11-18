package co.edu.uniquindio;

import co.edu.uniquindio.viewController.AdminUsuarioViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    private Stage adminUsuarioVindowStage;
    private BorderPane rootLayoout;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Billetera Virtual");
        mostrarGestionUsuario();
    }

    private void mostrarGestionUsuario() {
        try {
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(App.class.getResource("adminUsuarioWindow.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            AdminUsuarioViewController adminUsuarioController= loader.getController();
            adminUsuarioController.setApp(this);
            primaryStage.setScene(scene);
            primaryStage.show();
        }   catch (IOException e)   {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}