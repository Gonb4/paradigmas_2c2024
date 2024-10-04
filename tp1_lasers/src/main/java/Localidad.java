import java.util.ArrayList;

public class Localidad {
    private final Punto punto;
    private Bloque[] ocupantes;
    private boolean ocupable;

    public Localidad(Punto punto) {
        this.punto = punto;
        this.ocupable = true;
        this.ocupantes = new Bloque[3]; // 3 prioridades vidrio > espejo > cristal y opacos (como maximo habra 2 ocupantes)
    }

    //un metodo ocupar por cada clase de bloque (prioridades) que devuelva un boolean si fue ocupada

    public void quitarOcupante (Bloque ocupante) {
        // remove de ocupantes
    }

    public void hacerOcupable() {
        this.ocupable = true;
    }

    public void hacerInocupable() {
        this.ocupable = false;
    }

    public Punto controlarColision(Laser laser) {
        // por cada bloque en ocupantes: bloque.colisionar(this.punto, laser, grilla)
        // devuelve null o un puntoExtra (creo que solo el bloque de cristal)
        return null;
    }
}
