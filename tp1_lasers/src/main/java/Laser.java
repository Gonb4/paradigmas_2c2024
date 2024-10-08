import java.util.ArrayList;

public class Laser {
    public final Punto ubicEmisor;
    private ArrayList<Punto> trayectoria;
    private Direccion direccion;
    private boolean terminado;
    private final int maxPtosTrayectoria; // para evitar trayectoria infinita (puede ser cualquier valor grande)

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
        this.terminado = false;
    }

    public Punto siguientePunto() {
        return direccion.siguientePunto(trayectoria.getLast()); // devolver el nuevo punto
    }

    public void cambiarDireccion(Direccion dir) {
        this.direccion = dir;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public void terminarTrayectoria() {
        this.terminado = true;
    }

    public void trazarTrayectoria(Grilla grilla) {
        while (this.trayectoria.size() < maxPtosTrayectoria) {
            grilla.agregarPtoLaser(this, trayectoria.getLast());
            if (this.terminado) {
                break;
            }
            trayectoria.add(siguientePunto()); // agrego el ultimo a la grilla y despues avanzo en la trayectoria asi empieza por el emisor
        }
        terminarTrayectoria();
    }

    public void bifurcar(Grilla grilla, Punto ptoBifur, Direccion dirBifur) {
         Laser laserAdicional = new Laser(ptoBifur, dirBifur, this.maxPtosTrayectoria);
         laserAdicional.trazarTrayectoria(grilla);
    }
}
