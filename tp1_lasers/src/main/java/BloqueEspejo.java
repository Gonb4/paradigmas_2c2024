public class BloqueEspejo extends Bloque {
    public BloqueEspejo(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = 1;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // cambiar direccion laser en funcion de en que punto se produjo la colision
        return null;
    }
}
