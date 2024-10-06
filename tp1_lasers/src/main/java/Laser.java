import java.util.ArrayList;

public class Laser {
    private final Punto ubicEmisor;
    private ArrayList<Punto> trayectoria;
    private Direccion direccion;
    private boolean terminado;
    private final int maxPtosTrayectoria;

    public Laser(Punto ubicEmisor, Direccion dir, int maxPtosTray) {
        this.ubicEmisor = ubicEmisor;
        this.trayectoria = new ArrayList<Punto>();
        trayectoria.add(ubicEmisor);
        this.direccion = dir;
        this.terminado = false;
        this.maxPtosTrayectoria = maxPtosTray;
    }

    public void agregarPtoTrayectoria(Punto punto) {
        trayectoria.add(punto);
    }

    public void borrarTrayectoria(Grilla grilla) {
        this.trayectoria = new ArrayList<Punto>();
        trayectoria.add(ubicEmisor);
        grilla.borrarPtosLaser();
    }

    public Punto siguientePunto() {
        return direccion.siguientePunto(trayectoria.getLast()); // devolver el nuevo punto
    }

    public void cambiarDireccion(Direccion dir) {
        this.direccion = dir;
    }

    public void terminarTrayectoria() {
        this.terminado = true;
    }

    public void trazarTrayectoria(Grilla grilla) {
        // while not this.terminado
        // grilla.agregarPtoLaser(ultimo de trayectoria)
        // add this.siguientePunto a trayectoria // agrego el ultimo a la grilla y despues avanzo en la trayectoria asi empieza por el emisor
    }

    public void bifurcar(Grilla grilla, Punto ptoBifur, Direccion dirBifur) {
         Laser laserAdicional = new Laser(ptoBifur, dirBifur);
         laserAdicional.trazarTrayectoria(grilla);
    }
}
