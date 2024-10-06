public class BloqueVidrio extends Bloque {
    public BloqueVidrio(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = 0;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // laser.bifurcar(punto, grilla)
        return null;
    }
}
