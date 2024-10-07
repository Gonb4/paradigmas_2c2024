import java.util.ArrayList;
import java.util.HashSet;

public class Grilla { // ACA ES IMPORTANTE USAR SIEMPRE LA MISMA INSTANCIA DE UN PUNTO QUE TENGA UNAS COORDENADAS DETERMINADAS
    private final Localidad[][] matrizLocs; // creo que va a tener que ser con arraylists para ir generando la matriz con el archivo de texto
    private HashSet<Punto> puntosLaser;
    private ArrayList<Punto> puntosObjetivo;

    public Grilla(Localidad[][] matriz) {
        this.matrizLocs = matriz;
        this.puntosLaser = new HashSet<Punto>();
        this.puntosObjetivo = new ArrayList<Punto>();
    }

    public void agregarBloque(Bloque bloque, Punto ptoCen) { // creo que podemos asumir que no se va a tratar de agregar en localidades invalidas, usaremos este metodo solo para generar la grilla
        ocuparCelda(ptoCen, bloque);
    }

    public Bloque seleccionarBloque(Punto ptoCenBloque) {
        return matrizLocs[ptoCenBloque.y][ptoCenBloque.x].obtenerOcupante();
    }

    public void moverBloque(Bloque bloque, Punto ptoCenDest) { // podria recibir coordenadas o un punto, recibe instancia de bloque?
        if (esCentroCelda(ptoCenDest) && matrizLocs[ptoCenDest.y][ptoCenDest.x].esOcupable()) {
            Punto ptoCenOrig = bloque.ptoCentro;
            if (bloque.mover(ptoCenDest) == true) {
                ocuparCelda(ptoCenDest, bloque);
                liberarCelda(ptoCenOrig);
            }
        }
    }

    public void agregarObjetivo(Punto ptoObjetivo) { // asumir que es una localidad valida (o sea NO central), usar este metodo a medida que se crea la grilla (cuando se instancie un nuevo punto para agregar a la matriz tambien lo agrego a los puntosObjetivo)
        puntosObjetivo.add(ptoObjetivo);
    }

    public void agregarPtoLaser(Laser laser, Punto pto) {
        Localidad localidad = null;
        try {localidad = matrizLocs[pto.y][pto.x];}
        catch (ArrayIndexOutOfBoundsException _) {
            laser.terminarTrayectoria();
        }

        puntosLaser.add(localidad.punto);
        Punto ptoExtra = localidad.controlarColision(laser, this);
        if (ptoExtra != null) {
            this.agregarPtoLaser(laser, ptoExtra);
        }
    }

    public void borrarPtosLaser() {
        this.puntosLaser = new HashSet<>();
    }

    private boolean esCentroCelda(Punto punto) { // x e y tienen que ser ambos impares para que sea central, podria recibir un punto o coords
        return (punto.x % 2 != 0 && punto.y % 2 != 0);
    }

    public boolean objetivosAlcanzados() {
        for (Punto p : puntosObjetivo) {
            if (!puntosLaser.contains(p)) {
                return false;
            }
        }
        return true;
    }

    private void ocuparCelda(Punto ptoCen, Bloque bloque) {
        matrizLocs[ptoCen.y][ptoCen.x].agregarOcupante(bloque); // ocupo localidad centro
        matrizLocs[ptoCen.y][ptoCen.x].hacerInocupable();
        matrizLocs[ptoCen.y - 1][ptoCen.x].agregarOcupante(bloque); // ocupo localidad arriba
        matrizLocs[ptoCen.y + 1][ptoCen.x].agregarOcupante(bloque); // ocupo localidad abajo
        matrizLocs[ptoCen.y][ptoCen.x + 1].agregarOcupante(bloque); // ocupo localidad derecha
        matrizLocs[ptoCen.y][ptoCen.x - 1].agregarOcupante(bloque); // ocupo localidad izquierda
    }

    private void liberarCelda(Punto ptoCen) {
        Bloque bloqueOc = matrizLocs[ptoCen.y][ptoCen.x].obtenerOcupante();
        matrizLocs[ptoCen.y][ptoCen.x].quitarOcupante(bloqueOc); // libero localidad centro
        matrizLocs[ptoCen.y][ptoCen.x].hacerOcupable();
        matrizLocs[ptoCen.y - 1][ptoCen.x].quitarOcupante(bloqueOc); // libero localidad arriba
        matrizLocs[ptoCen.y + 1][ptoCen.x].quitarOcupante(bloqueOc); // libero localidad abajo
        matrizLocs[ptoCen.y][ptoCen.x + 1].quitarOcupante(bloqueOc); // libero localidad derecha
        matrizLocs[ptoCen.y][ptoCen.x - 1].quitarOcupante(bloqueOc); // libero localidad izquierda
    }

    //DESCARTADO
    //agregarLocalidad() // para construir la matriz al parsear el archivo de texto
}
