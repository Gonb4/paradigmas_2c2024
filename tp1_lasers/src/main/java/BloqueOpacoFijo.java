public class BloqueOpacoFijo extends Bloque {
    public BloqueOpacoFijo() {
        this.movil = false;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // terminar laser
        return null;
    }
}
