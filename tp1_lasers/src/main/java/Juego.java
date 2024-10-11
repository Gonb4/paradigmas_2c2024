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

    private final GridPane grid = new GridPane();
    private StackPane[][] matrizSP;

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage stage) {
        var root = new HBox();
        var botones = crearPanelBotones();
        var panelJuego = new StackPane();
        panelJuego.setMaxSize(600, 600);
        Rectangle fondo = new Rectangle(600, 600, Color.WHITE);


        cargarNivel(1);
        Button accion = new Button("probar");
        accion.setOnAction(e -> {
            grilla.moverBloque(grilla.seleccionarBloque(new Punto(7,5)), new Punto(7,3));
            for (Laser l : lasers) {
                l.borrarTrayectoria(grilla);
                l.trazarTrayectoria(grilla);
                System.out.print(l.ubicEmisor.x + "," + l.ubicEmisor.y + ": ");
                for (Punto p : l.getTrayectoria()) {
                    System.out.print("(" + p.x + ", " + p.y + ") ");
                }
                System.out.println();
            }

            matrizSP = new MatrizStackPane(grilla, lasers).getMatriz();
            poblarGrid();
        });
        botones.getChildren().add(accion);

        panelJuego.getChildren().addAll(fondo, grid);//, linea, grdpr);
        StackPane.setMargin(grid, new Insets(30));
//        StackPane.setAlignment(grid, Pos.CENTER);

        root.getChildren().addAll(botones, panelJuego);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Juego - Lasers");
        stage.show();
    }

    private VBox crearPanelBotones() {
        var panel = new VBox();
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

        panel.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);
        return panel;
    }

    private void cargarNivel(int n) {
        Parser parser = null;
        switch (n) {
            case 1 -> parser = new Parser("src\\main\\resources\\java\\level1.dat");
            case 2 -> parser = new Parser("src\\main\\resources\\java\\level2.dat");
            case 3 -> parser = new Parser("src\\main\\resources\\java\\level3.dat");
            case 4 -> parser = new Parser("src\\main\\resources\\java\\level4.dat");
            case 5 -> parser = new Parser("src\\main\\resources\\java\\level5.dat");
            case 6 -> parser = new Parser("src\\main\\resources\\java\\level6.dat");
        }
        grilla = parser.getGrilla();
        lasers = parser.getLasers();
        for (Laser l : lasers) {l.trazarTrayectoria(grilla);}
        matrizSP = new MatrizStackPane(grilla, lasers).getMatriz();
        poblarGrid();
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
        for (int i = 1; i < matrizSP.length; i+=2) {
            for (int j = 1; j < matrizSP[i].length; j+=2) {
                grid.add(matrizSP[i][j], j, i);
            }
        }
    }

//    private void iniciarTurno() {}
//    private void terminarTurno() {}
}