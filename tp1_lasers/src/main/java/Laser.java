import java.util.ArrayList;

public class Laser {
    private ArrayList<Punto> trayectoria;
    private Direccion direccion;
    private boolean terminado;

    public Laser(Punto ubicEmisor, Direccion dir) {
        // add ubicEmisor a trayectoria
        this.direccion = dir;
        this.terminado = false;
    }

    public void agregarPtoTrayectoria(Punto punto) {
        // add punto a trayectoria
    }

    public Punto siguientePunto() {
        // con el x e y del ultimo en trayectoria -> new Punto(direccion.siguienteX, direccion.siguienteY)
        return null; // devolver el nuevo punto
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
        // add this.siguientePunto a trayectoria
    }

    public void bifurcar(Grilla grilla, Punto punto, Direccion dir) {
        // laserAdicional = new Laser(punto, dir)
        // laserAdicional.trazarTrayectoria(grilla)
    }
}
