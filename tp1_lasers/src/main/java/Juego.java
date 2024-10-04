import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Juego extends Application {
    public static void main(String[] args) {
        launch(args);
    }

//    @Override
//    public void start(Stage stage) {
//
//    }

    @Override
    public void start(Stage stage) throws Exception {
        var root = new VBox();
        var scene = new Scene(root);

        root.getChildren().add(new TextField());
        root.getChildren().add(new Button());

        stage.setScene(scene);
        stage.show();

        var d = this.hola(Direccion.SUDOESTE);
    }

    public Direccion hola(Direccion dir) {
        return dir;
    }
}