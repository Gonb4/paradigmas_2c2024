package gui;

import utilidades.*;
import logica.*;
import gui.*;
import app.*;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;

public class MatrizStackPane {
    private StackPane[][] matriz;

    private final Juego juego;
    private final Grilla grilla;
    private final ArrayList<Laser> lasers;
    private final int cellSize = Constantes.CELL_SIZE;

    public MatrizStackPane(Juego juego, Grilla grilla, ArrayList<Laser> lasers) {
        this.juego = juego;
        this.grilla = grilla;
        this.lasers = lasers;
        poblarMatriz();
    }

    public StackPane[][] getMatriz(){
        return matriz;
    }

    public void poblarMatriz() {
        matriz = new StackPane[grilla.getMatrizLocs().length][grilla.getMatrizLocs()[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for ( int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = new StackPane();
            }
        }
        agregarBloques();
        agregarObjetivos();
        agregarEmisores();
        for (Laser l : lasers) {agregarTrayLaser(l);}
    }

    private void agregarBloques() {
        var localidades = grilla.getMatrizLocs();
        for (int i = 1; i < localidades.length; i+=2) {
            for (int j = 1; j < localidades[i].length; j+=2) {
                Celda celda = new Celda(juego, localidades[i][j]);
                matriz[i][j].getChildren().addAll(celda.getFondo(), celda.getRectangulo());
            }
        }
    }

    private void agregarEmisores() {
        for (Laser l : lasers) {
            Circle emisor = new Circle(7, Color.RED);
            var ptoCenCelda = calcularCoordsCelda(l.ubicEmisor);
            agregarCirculo(ptoCenCelda, emisor, l.ubicEmisor);
        }
    }

    private void agregarObjetivos() {
        for (Punto po : grilla.getPuntosObjetivo()) {
            Circle objetivo = new Circle(5, Color.WHITE);
            objetivo.setStroke(Color.RED);
            objetivo.setStrokeWidth(3);
            var ptoCenCelda = calcularCoordsCelda(po);
            agregarCirculo(ptoCenCelda, objetivo, po);
        }
    }

    private Punto calcularCoordsCelda(Punto pto) {
        var x = pto.x;
        var y = pto.y;

        if (x == 0) {x += 1;}
        if (y == 0) {y += 1;}
        if (x % 2 == 0) {x -= 1;}
        if (y % 2 == 0) {y -= 1;}

        return new Punto(x, y);
    }

    private void agregarCirculo(Punto ptoCenCelda, Circle elem, Punto pto) {
        matriz[ptoCenCelda.y][ptoCenCelda.x].getChildren().add(elem);

        if (pto.x < ptoCenCelda.x) {StackPane.setAlignment(elem, Pos.CENTER_LEFT);} // solo pasa con x = 0, borde izquierdo de grilla
        if (pto.x > ptoCenCelda.x) {StackPane.setAlignment(elem, Pos.CENTER_RIGHT);}
        if (pto.y < ptoCenCelda.y) {StackPane.setAlignment(elem, Pos.TOP_CENTER);} // solo pasa con y = 0, borde inferior de grilla
        if (pto.y > ptoCenCelda.y) {StackPane.setAlignment(elem, Pos.BOTTOM_CENTER);}
    }

    private void agregarTrayLaser(Laser laser) {
        for (Laser rayo : laser.getRayos()) {
            var trayectoria = rayo.getTrayectoria();
            for (int i = 1; i < trayectoria.size(); i++) {
                var orig = trayectoria.get(i - 1);
                var dest = trayectoria.get(i);
                var ptoCenCelda = calcularCoordsCelda(orig, dest);
                var tramo = crearTramoTrayLaser(ptoCenCelda, orig, dest); // linea con largo y angulo correcto
                agregarLinea(ptoCenCelda, tramo);
            }
        }
    }

    private Punto calcularCoordsCelda(Punto origen, Punto destino) {
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

    private void agregarLinea(Punto ptoCenCelda, Line linea) {
        matriz[ptoCenCelda.y][ptoCenCelda.x].getChildren().add(linea);

        if (linea.getStartY() == linea.getEndY()) {return;} // horizontal: -- (StackPane ya lo alinea en el centro)
        if (linea.getStartX() == linea.getEndX()) {return;} // vertical: | (StackPane ya lo alinea en el centro)

        if (Math.min(linea.getStartY(), linea.getEndY()) < cellSize/2) { // diagonales: /\ (superiores)
            if (Math.max(linea.getStartX(), linea.getEndX()) > cellSize/2) {StackPane.setAlignment(linea, Pos.TOP_RIGHT);} // diagonal: \
            else {StackPane.setAlignment(linea, Pos.TOP_LEFT);} // diagonal: /
        } else { // diagonales: \/ (inferiores)
            if (Math.max(linea.getStartX(), linea.getEndX()) > cellSize/2) {StackPane.setAlignment(linea, Pos.BOTTOM_RIGHT);} // diagonal: /
            else {StackPane.setAlignment(linea, Pos.BOTTOM_LEFT);} // diagonal: \
        }
    }
}
