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
    private final int cellSize = 70;

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage stage) {
        var root = new HBox();
        var botones = crearPanelBotones();
        var panelJuego = new StackPane();
        panelJuego.setMaxSize(600, 600);
        panelJuego.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-color: blue;");
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

            matrizSP = generarMatrizStackPane();
            poblarGrid();
        });
        botones.getChildren().add(accion);

        panelJuego.getChildren().addAll(fondo, grid);//, linea, grdpr);
        StackPane.setMargin(grid, new Insets(30));
//        StackPane.setMargin(grdpr, new Insets(30));
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
        matrizSP = generarMatrizStackPane();
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

    private StackPane[][] generarMatrizStackPane() {
        var matriz = new StackPane[grilla.getMatrizLocs().length][grilla.getMatrizLocs()[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for ( int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = new StackPane();
            }
        }
        agregarBloquesAMatrizSP(matriz);
        agregarObjetivosAMatrizSP(matriz);
        agregarEmisoresAMatrizSP(matriz);
        for (Laser l : lasers) {agregarTrayLaserAMatrizSP(matriz, l);}

        return matriz;
    }

    private void agregarBloquesAMatrizSP(StackPane[][] matrizSP) {
        var localidades = grilla.getMatrizLocs();
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

    private void agregarEmisoresAMatrizSP(StackPane[][] matrizSP) {
        for (Laser l : lasers) {
            Circle emisor = new Circle(7, Color.RED);
//            matrizSP[l.ubicEmisor.y][l.ubicEmisor.x].getChildren().add(emisor);
            var ptoCenCelda = calcularCoordsSPCelda(l.ubicEmisor);
            agregarCirculoAMatrizSP(matrizSP, ptoCenCelda, emisor, l.ubicEmisor);
        }
    }

    private void agregarObjetivosAMatrizSP(StackPane[][] matrizSP) {
        for (Punto po : grilla.getPuntosObjetivo()) {
            Circle objetivo = new Circle(5, Color.WHITE);
            objetivo.setStroke(Color.RED);
            objetivo.setStrokeWidth(3);
//            matrizSP[po.y][po.x].getChildren().add(objetivo);
            var ptoCenCelda = calcularCoordsSPCelda(po);
            agregarCirculoAMatrizSP(matrizSP, ptoCenCelda, objetivo, po);
        }
    }

    private Punto calcularCoordsSPCelda(Punto pto) {
        var x = pto.x;
        var y = pto.y;

        if (x == 0) {x += 1;}
        if (y == 0) {y += 1;}
        if (x % 2 == 0) {x -= 1;}
        if (y % 2 == 0) {y -= 1;}

        return new Punto(x, y);
    }

    private void agregarCirculoAMatrizSP(StackPane[][] matrizSP, Punto ptoCenCelda, Circle elem, Punto pto) {
        matrizSP[ptoCenCelda.y][ptoCenCelda.x].getChildren().add(elem);

        if (pto.x < ptoCenCelda.x) {StackPane.setAlignment(elem, Pos.CENTER_LEFT);} // solo pasa con x = 0, borde izquierdo de grilla
        if (pto.x > ptoCenCelda.x) {StackPane.setAlignment(elem, Pos.CENTER_RIGHT);}
        if (pto.y < ptoCenCelda.y) {StackPane.setAlignment(elem, Pos.TOP_CENTER);} // solo pasa con y = 0, borde inferior de grilla
        if (pto.y > ptoCenCelda.y) {StackPane.setAlignment(elem, Pos.BOTTOM_CENTER);}
    }

    private void agregarTrayLaserAMatrizSP(StackPane[][] matrizSP, Laser laser) {
        var trayectoria = laser.getTrayectoria();
        for (int i = 1; i < trayectoria.size(); i++) {
            var orig = trayectoria.get(i - 1);
            var dest = trayectoria.get(i);
            var ptoCenCelda = calcularCoordsSPCelda(orig, dest);
            var tramo = crearTramoTrayLaser(ptoCenCelda, orig, dest); // linea con largo y angulo correcto
            agregarLineaAMatrizSP(matrizSP, ptoCenCelda, tramo);
        }
    }

    private Punto calcularCoordsSPCelda(Punto origen, Punto destino) {
        var xAux = (origen.x + destino.x) / 2; // division entera
        var yAux = (origen.y + destino.y) / 2; // division entera

        if (xAux % 2 == 0) {xAux += 1;}
        if (yAux % 2 == 0) {yAux += 1;}

        return new Punto(xAux, yAux);
    }

    private Line crearTramoTrayLaser(Punto ptoCenCelda, Punto origen, Punto destino) {
        var origenCoords = new Punto(ptoCenCelda.x - 1, ptoCenCelda.y - 1);

        var xOrigAjust = (origen.x - origenCoords.x) * cellSize/2;
        var yOrigAjust = (origen.y - origenCoords.y) * cellSize/2;
        var xDestAjust = (destino.x - origenCoords.x) * cellSize/2;
        var yDestAjust = (destino.y - origenCoords.y) * cellSize/2;

        var tramo = new Line(xOrigAjust, yOrigAjust, xDestAjust, yDestAjust);
        tramo.setStroke(Color.RED);
//        tramo.setStrokeWidth(2);
        return tramo;
    }

    private void agregarLineaAMatrizSP(StackPane[][] matrizSP, Punto ptoCenCelda, Line linea) {
        matrizSP[ptoCenCelda.y][ptoCenCelda.x].getChildren().add(linea);

        if (linea.getStartY() == linea.getEndY()) {return;} // horizontal: -- (StackPane ya lo alinea en el centro)
        if (linea.getStartX() == linea.getEndX()) {return;} // vertical: | (StackPane ya lo alinea en el centro)

        if (Math.min(linea.getStartY(), linea.getEndY()) < cellSize/2) { // diagonales: /\ (superiores)
            if (Math.max(linea.getStartX(), linea.getEndX()) > cellSize/2) {StackPane.setAlignment(linea, Pos.TOP_RIGHT);} // diagonal: \
            else {StackPane.setAlignment(linea, Pos.TOP_LEFT);} // diagonal: /
        } else { // diagonales: \/ (inferiores)
            if (Math.max(linea.getStartX(), linea.getEndX()) > cellSize/2) {StackPane.setAlignment(linea, Pos.BOTTOM_RIGHT);} // diagonal: /
            else {StackPane.setAlignment(linea, Pos.BOTTOM_LEFT);} // diagonal: \
        }
//        System.out.println(ptoCenCelda.x + "," + ptoCenCelda.y + " " + linea);
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