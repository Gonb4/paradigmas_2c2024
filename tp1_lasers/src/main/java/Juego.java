import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Juego extends Application {
    private HBox root;
    private VBox botones;
    private StackPane panelJuego;
    private GridPane grid;
    private  int cellSize;

    private Grilla grilla;
    private ArrayList<Laser> lasers;

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage stage) {
        root = new HBox();
        botones = new VBox();
        panelJuego = new StackPane();
        grid = new GridPane();
        cellSize = 70;

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

        botones.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);
        Rectangle fondo = new Rectangle(530, 570, Color.WHITE);
        panelJuego.getChildren().addAll(fondo, grid);
        StackPane.setMargin(grid, new Insets(30));
        StackPane.setAlignment(fondo, Pos.CENTER_RIGHT);
        root.getChildren().addAll(botones, panelJuego);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Juego - Lasers");
        stage.show();
    }

    public void cargarNivel(int n) {
        Parser parser = null;
        switch (n) {
            case 1 -> parser = new Parser("src\\main\\resources\\java\\level1.dat");
            case 2 -> parser = new Parser("src\\main\\resources\\java\\level2.dat");
            case 3 -> parser = new Parser("src\\main\\resources\\java\\level3.dat");
            case 4 -> parser = new Parser("src\\main\\resources\\java\\level4.dat");
            case 5 -> parser = new Parser("src\\main\\resources\\java\\level5.dat");
            case 6 -> parser = new Parser("src\\main\\resources\\java\\level6.dat");
        }
        grid.getChildren().clear();
        grilla = parser.getGrilla();
        lasers = parser.getLasers();
        poblarGrid(grilla.getMatrizLocs(), lasers, grilla.getPuntosObjetivo());
    }

    private void poblarGrid(Localidad[][] localidades, ArrayList<Laser> lasers, ArrayList<Punto> ptsObj) {
        var matrizSP = generarMatrizStackPane(localidades);

        agregarBloquesAMatrizSP(matrizSP, localidades);
        agregarLasersAMatrizSP(matrizSP, lasers);
        agregarObjetivosAMatrizSP(matrizSP, ptsObj);

        for (int i = 0; i < matrizSP.length; i++) {
            for (int j = 0; j < matrizSP[i].length; j++) {
                grid.add(matrizSP[i][j], j, i);
            }
        }
    }

    private StackPane[][] generarMatrizStackPane(Localidad[][] localidades) {
        var matrizSP = new StackPane[localidades.length][localidades[0].length];
        for (int i = 0; i < localidades.length; i++) {
            for ( int j = 0; j < localidades[i].length; j++) {
                matrizSP[i][j] = new StackPane();
            }
        }
        return matrizSP;
    }

    private void agregarBloquesAMatrizSP(StackPane[][] matrizSP, Localidad[][] localidades) {
        for (int i = 1; i < localidades.length; i+=2) {
            for (int j = 1; j < localidades[i].length; j+=2) {
                Localidad localidad = localidades[i][j];
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setStroke(Color.BLACK);

                switch (localidad.obtenerOcupante()) {
                    case null -> {
                        if (!localidad.esOcupable()) {
                            cell.setFill(Color.WHITE);
                            cell.setStroke(null);
                        } else {cell.setFill(Color.LIGHTGRAY);}
                    }
                    case BloqueOpacoFijo bloqueOpacoFijo -> cell.setFill(Color.BLACK);
                    case BloqueOpacoMovil bloqueOpacoMovil -> cell.setFill(Color.DIMGRAY);
                    case BloqueCristal bloqueCristal -> cell.setFill(Color.DARKTURQUOISE);
                    case BloqueEspejo bloqueEspejo -> cell.setFill(Color.DARKCYAN);
                    case BloqueVidrio bloqueVidrio -> cell.setFill(Color.GHOSTWHITE);
                    default -> {break;}
                }
                matrizSP[i][j].getChildren().add(cell);
            }
        }
    }

    private void agregarLasersAMatrizSP(StackPane[][] matrizSP, ArrayList<Laser> lasers) {
        for (Laser l : lasers) {
            Circle emisor = new Circle(7, Color.RED);
            matrizSP[l.ubicEmisor.y][l.ubicEmisor.x].getChildren().add(emisor);
        }
    }

    private void agregarObjetivosAMatrizSP(StackPane[][] matrizSP, ArrayList<Punto> ptsObj) {
        for (Punto o : ptsObj) {
            Circle objetivo = new Circle(7, Color.WHITE);
            objetivo.setStroke(Color.RED);
            matrizSP[o.y][o.x].getChildren().add(objetivo);
        }
    }
}



//    @Override
//    public void start(Stage stage) throws Exception {
//        var root = new VBox();
//        var scene = new Scene(root);
//
//        root.getChildren().add(new TextField());
//        root.getChildren().add(new Button());
//
//        stage.setScene(scene);
//        stage.show();
//
//        var d = this.hola(Direccion.SUDOESTE);
//    }
//
//    public Direccion hola(Direccion dir) {
//        return dir;
//    }
//}