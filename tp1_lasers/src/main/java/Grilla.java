import java.util.ArrayList;
import java.util.HashSet;

public class Grilla { // ACA ES IMPORTANTE USAR SIEMPRE LA MISMA INSTANCIA DE UN PUNTO QUE TENGA UNAS COORDENADAS DETERMINADAS
    private Localidad[][] matrizCoords; // creo que va a tener que ser con arraylists para ir generando la matriz con el archivo de texto
    private HashSet<Punto> puntosLaser;
    private ArrayList<Punto> puntosObjetivo;

    public void agregarBloque(Bloque bloque) { // creo que podemos asumir que no se va a tratar de agregar en localidades invalidas, usaremos este metodo solo para generar la grilla
        // ocupar las 5 localidades, cambiar la central a inocupable
    }

    public void moverBloque() { // podria recibir coordenadas o un punto, recibe instancia de bloque?
        // if bloque.mover(5 localidades nuevas) == true
            // lo saco de las 5 localidades donde estaba

    }

    public void agregarObjetivo(Punto ptoObjetivo) { // asumir que es una localidad valida (o sea NO central), usar este metodo a medida que se crea la grilla (cuando se instancie un nuevo punto para agregar a la matriz tambien lo agrego a los puntosObjetivo)

    }

    //agregarLocalidad() // para construir la matriz al parsear el archivo de texto

    /*agregarPtoLaser(laser, punto)
        usar coords punto para buscar localidad en matriz
		setPtosLaser.Add(instancia de punto guardado en la localidad)
        if (ptoExtra = localidad.controlarColision()) != NULL
		    this.agregarPtoLaser(laser, ptoExtra)*/

    //private boolean sonCoordsCentrales() // x e y tienen que ser ambos impares para que sea central, podria recibir un punto o coords
    //public boolean hayGanador()
}
