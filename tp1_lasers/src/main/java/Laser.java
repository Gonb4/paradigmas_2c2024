import java.util.ArrayList;

public class Laser {
    public final Punto ubicEmisor;
    private ArrayList<Punto> trayectoria;
    private Direccion direccion;
    private Direccion direccOriginal;
    private boolean terminado;
    private final int maxPtosTrayectoria; // para evitar trayectoria infinita (puede ser cualquier valor grande)
    private ArrayList<Laser> rayos;

    public Laser(Punto ubicEmisor, Direccion dir, int maxPtosTray) {
        this.ubicEmisor = ubicEmisor;
        this.trayectoria = new ArrayList<Punto>();
        trayectoria.add(ubicEmisor);
        this.direccion = dir;
        this.direccOriginal = dir;
        this.terminado = false;
        this.maxPtosTrayectoria = maxPtosTray;
        this.rayos = new ArrayList<Laser>();
        rayos.add(this);
    }

    public void agregarPtoTrayectoria(Punto punto) {
        trayectoria.add(punto);
    }

    public void quitarUltPtoTrayectoria () {
        trayectoria.removeLast();
    }

    public void borrarTrayectoria(Grilla grilla) {
        this.trayectoria = new ArrayList<Punto>();
        trayectoria.add(ubicEmisor);
        this.terminado = false;
        this.direccion = direccOriginal;
        this.rayos.clear();
        this.rayos.add(this);
    }

    private Punto siguientePunto() {
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

    public ArrayList<Punto> getTrayectoria() {
        return trayectoria;
    }

    public void trazarTrayectoria(Grilla grilla) {
        while (trayectoria.size() < maxPtosTrayectoria) {
            grilla.agregarPtoLaser(this, trayectoria.getLast());
            if (terminado) {return;}
            trayectoria.add(siguientePunto()); // agrego el ultimo a la grilla y despues avanzo en la trayectoria asi empieza por el emisor
        }
        terminarTrayectoria();
    }

    public void bifurcar(Grilla grilla, Punto ptoBifur, Direccion dirBifur) {
         Laser laserAdicional = new Laser(ptoBifur, dirBifur, this.maxPtosTrayectoria);
         rayos.add(laserAdicional);
         laserAdicional.trazarTrayectoria(grilla);
    }

    public ArrayList<Laser> getRayos() {
        return rayos;
    }
}
