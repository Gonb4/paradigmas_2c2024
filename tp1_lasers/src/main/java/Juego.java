import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Juego extends Application {
    private Grilla grilla;
    private ArrayList<Laser> lasers;

    private VBox panelBotones = new VBox();
    private final GridPane grid = new GridPane();
    private MatrizStackPane matrizSP;
    private Rectangle fondo = new Rectangle(Constantes.JUEGO_SIZE, Constantes.JUEGO_SIZE, Color.WHITE);
    private StackPane panelJuego = new StackPane();
    private Rectangle bloqueo = new Rectangle(Constantes.JUEGO_SIZE, Constantes.JUEGO_SIZE, Color.TRANSPARENT);
    private int colMax;
    private int filMax;

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage stage) {
        var root = new HBox();
        poblarPanelBotones();
        poblarPanelJuego();

        root.getChildren().addAll(panelBotones, panelJuego);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Juego - Lasers");
        stage.show();
    }

    private void poblarPanelBotones() {
        panelBotones.setMinSize(Constantes.ANCHO_BOTON, Constantes.JUEGO_SIZE);

        Button btn1 = new Button("Level 1");
        Button btn2 = new Button("Level 2");
        Button btn3 = new Button("Level 3");
        Button btn4 = new Button("Level 4");
        Button btn5 = new Button("Level 5");
        Button btn6 = new Button("Level 6");

        btn1.setOnAction(event -> cargarNivel(1));
        btn2.setOnAction(event -> cargarNivel(2));
        btn3.setOnAction(event -> cargarNivel(3));
        btn4.setOnAction(event -> cargarNivel(4));
        btn5.setOnAction(event -> cargarNivel(5));
        btn6.setOnAction(event -> cargarNivel(6));

        panelBotones.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);
    }

    private void poblarPanelJuego() {
        panelJuego.setMaxSize(Constantes.JUEGO_SIZE, Constantes.JUEGO_SIZE);
        panelJuego.getChildren().addAll(fondo, grid);
        StackPane.setMargin(grid, new Insets(Constantes.MARGEN_GRILLA));
    }

    private void cargarNivel(int n) {
        Parser parser = null;
        switch (n) {
            case 1 -> parser = new Parser("src\\main\\resources\\level1.dat");
            case 2 -> parser = new Parser("src\\main\\resources\\level2.dat");
            case 3 -> parser = new Parser("src\\main\\resources\\level3.dat");
            case 4 -> parser = new Parser("src\\main\\resources\\level4.dat");
            case 5 -> parser = new Parser("src\\main\\resources\\level5.dat");
            case 6 -> parser = new Parser("src\\main\\resources\\level6.dat");
        }
        grilla = parser.getGrilla();
        lasers = parser.getLasers();
        for (Laser l : lasers) {l.trazarTrayectoria(grilla);}
        matrizSP = new MatrizStackPane(this, grilla, lasers);
        filMax = grilla.getMatrizLocs().length - 2;
        colMax = grilla.getMatrizLocs()[0].length - 2;
        fondo.setFill(Color.WHITE);
        poblarGrid();
        panelJuego.getChildren().remove(bloqueo);
    }

    private void poblarGrid() {
        grid.getChildren().clear();
        for (int i = 1; i < matrizSP.getMatriz().length; i+=2) {
            for (int j = 1; j < matrizSP.getMatriz()[i].length; j+=2) {
                grid.add(matrizSP.getMatriz()[i][j], j, i);
            }
        }
    }

    public void jugarTurno(Bloque bloque, int destX, int destY) {
        if ((destX > 0 && destX <= colMax) && (destY > 0 && destY <= filMax)) {
            grilla.moverBloque(bloque, new Punto(destX, destY));
            grilla.borrarPtosLaser();
            for (Laser l : lasers) {
                l.borrarTrayectoria(grilla);
                l.trazarTrayectoria(grilla);
            }
        }
        matrizSP.poblarMatriz();
        poblarGrid();
        controlarVictoria();
    }

    private void controlarVictoria() {
        if (grilla.objetivosAlcanzados()) {
            fondo.setFill(Color.LIGHTGREEN);
            panelJuego.getChildren().add(bloqueo);
        }
    }
}