public class BloqueOpacoFijo extends Bloque {
    public BloqueOpacoFijo(Punto ptoCen) {
        super(ptoCen);
        this.movil = false;
        this.prioridadColision = 2;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // terminar laser
        return null;
    }
}
