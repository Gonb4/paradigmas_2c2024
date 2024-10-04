public class BloqueVidrio extends Bloque {
    public BloqueVidrio() {
        this.movil = true;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // laser.bifurcar(punto, grilla)
        return null;
    }
}
