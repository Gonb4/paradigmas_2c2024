public class BloqueOpacoMovil extends Bloque {
    public BloqueOpacoMovil() {
        this.movil = true;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // terminar laser
        return null;
    }
}
