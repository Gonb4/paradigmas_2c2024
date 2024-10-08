import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;

public class Localidad {
    public final Punto punto;
    private ArrayList<Bloque> ocupantes;
    private boolean ocupable;

    public Localidad(Punto punto) {
        this.punto = punto;
        this.ocupable = true;
        this.ocupantes = new ArrayList<Bloque>(Arrays.asList(new Bloque[4])); // 3 prioridades vidrio > espejo > cristal y opacos (como maximo habra 2 ocupantes)
    }

    public void agregarOcupante(Bloque ocupante) {
        ocupantes.add(ocupante.getPrioridadColision(), ocupante);
    }

    public void quitarOcupante (Bloque ocupante) {
        for (int i = 0; i < ocupantes.size(); i++) {
            if (ocupantes.get(i) == ocupante) {
                ocupantes.set(i, null);
            }
        }
    }

    public Bloque obtenerOcupante() { // solo deberia usar este metodo con localidades centrales porque deberian tener un unico ocupante
        Bloque ocupante = null;
        for (Bloque b : ocupantes) {
            if (b == null) {
                continue;
            }
            ocupante = b;
        }
        return ocupante;
    }

    public boolean esOcupable() {
        return ocupable;
    }

    public void hacerOcupable() {
        this.ocupable = true;
    }

    public void hacerInocupable() {
        this.ocupable = false;
    }

    public Punto controlarColision(Laser laser, Grilla grilla) {
        Punto ptoExtra = null;
        for (Bloque b : ocupantes) {
            if (b == null) {
                continue;
            }
            ptoExtra = b.colisionar(laser, this.punto, grilla); // (solo BloqueCristal devuelve un punto extra)
        }
        return ptoExtra;
    }
}
