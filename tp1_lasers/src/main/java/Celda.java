import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Celda {
    private final Juego juego;
    private final Bloque bloque;
    private final Rectangle fondo;
    private final Rectangle rectangulo;

    public Celda(Juego juego, Localidad localidad) {
        this.juego = juego;
        this.bloque = localidad.obtenerOcupante();
        this.fondo = new Rectangle(Constantes.CELL_SIZE, Constantes.CELL_SIZE, Color.LIGHTGRAY);
        this.rectangulo = new Rectangle(Constantes.CELL_SIZE, Constantes.CELL_SIZE);
        rectangulo.setStroke(Color.BLACK);

        switch (localidad.obtenerOcupante()) {
            case null -> {
                if (!localidad.esOcupable()) {
                    rectangulo.setFill(Color.WHITE);
                    rectangulo.setStroke(null);
                } else {rectangulo.setFill(Color.LIGHTGRAY);}
            }
            case BloqueOpacoFijo bloqueOpacoFijo -> rectangulo.setFill(Color.BLACK);
            case BloqueOpacoMovil bloqueOpacoMovil -> {
                rectangulo.setFill(Color.DIMGRAY);
                hacerArrastrable();}
            case BloqueCristal bloqueCristal -> {
                rectangulo.setFill(Color.DARKTURQUOISE);
                hacerArrastrable();}
            case BloqueEspejo bloqueEspejo -> {
                rectangulo.setFill(Color.DARKCYAN);
                hacerArrastrable();}
            case BloqueVidrio bloqueVidrio -> {
                rectangulo.setFill(Color.GHOSTWHITE);
                hacerArrastrable();}
            default -> {break;}
        }
    }

    private void hacerArrastrable() {
        final double[] click = new double[2];
        rectangulo.setOnMousePressed(ev -> {
            click[0] = ev.getSceneX();
            click[1] = ev.getSceneY();
            rectangulo.getParent().toFront();
        });
        rectangulo.setOnMouseDragged(ev -> {
            double dragX = ev.getSceneX() - click[0];
            double dragY = ev.getSceneY() - click[1];
            rectangulo.setTranslateX(dragX);
            rectangulo.setTranslateY(dragY);
        });
        rectangulo.setOnMouseReleased(ev -> {
//            rectangulo.setTranslateX(0);
//            rectangulo.setTranslateY(0);
            double releaseX = ev.getSceneX();
            double releaseY = ev.getSceneY();
            int destX = (int) ((releaseX - 83)/35);
            int destY = (int) ((releaseY - 30)/35); // VER COMO JUSTIFICAR ESTE 83
            if (destX % 2 == 0) {destX += 1;}
            if (destY % 2 == 0) {destY += 1;}
            System.out.println(destX + ", " + destY);
            juego.jugarTurno(bloque, destX, destY);
        });
    }

    public Rectangle getFondo() {
        return fondo;
    }

    public Rectangle getRectangulo() {
        return rectangulo;
    }
}
