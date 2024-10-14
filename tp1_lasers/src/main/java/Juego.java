import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

//        javafx.scene.layout.StackPane.setAlignment(grid, Pos.TOP_LEFT);
//
//        System.out.println(panelBotones.getChildren().getFirst().getLayoutBounds().getWidth());
//        cargarNivel(1);
//        System.out.println(grid.getLayoutBounds());
//        System.out.println(grid.getLayoutX());
//        System.out.println(matrizSP[3][5].getChildren().getFirst().getTranslateX());
        Button accion = new Button("probar");
        accion.setOnAction(e -> {
//            grilla.moverBloque(grilla.seleccionarBloque(new Punto(7,5)), new Punto(7,3));
//            for (Laser l : lasers) {
//                l.borrarTrayectoria(grilla);
//                l.trazarTrayectoria(grilla);
////                System.out.print(l.ubicEmisor.x + "," + l.ubicEmisor.y + ": ");
////                for (Punto p : l.getTrayectoria()) {
////                    System.out.print("(" + p.x + ", " + p.y + ") ");
////                }
////                System.out.println();
//            }
//
            matrizSP.poblarMatriz();
            poblarGrid();
//            fondo.setFill(Color.LIGHTGREEN);

        });
        panelBotones.getChildren().add(accion);

        root.getChildren().addAll(panelBotones, panelJuego);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Juego - Lasers");
        stage.show();
    }

    private void poblarPanelBotones() {
        panelBotones.setMinSize(53, Constantes.JUEGO_SIZE);

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
        panelJuego.getChildren().addAll(fondo, grid);//, linea, grdpr);
        StackPane.setMargin(grid, new Insets(30));
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
        //jugarTurno()
//        for (Laser l : lasers) {
//            l.trazarTrayectoria(grilla);
//            System.out.print(l.ubicEmisor.x + "," + l.ubicEmisor.y + ": ");
//            for (Punto p : l.getTrayectoria()) {
//                System.out.print("(" + p.x + ", " + p.y + ") ");
//            }
//            System.out.println();
//        }
//        poblarGrid();
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
//            for (Laser l : lasers) {
//                l.borrarTrayectoria(grilla);
//                l.trazarTrayectoria(grilla);
//            }

            for (Laser l : lasers) {
                l.borrarTrayectoria(grilla);
                l.trazarTrayectoria(grilla);
                System.out.print(l.ubicEmisor.x + "," + l.ubicEmisor.y + ": ");
                for (Punto p : l.getTrayectoria()) {
                    System.out.print("(" + p.x + ", " + p.y + ") ");
                }
                System.out.println();
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