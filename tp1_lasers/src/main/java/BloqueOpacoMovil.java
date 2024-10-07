public class BloqueOpacoMovil extends Bloque {
    public BloqueOpacoMovil(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = 2;
    }

    @Override
    public Punto colisionar(Laser laser, Punto ptoColision, Grilla grilla) {
        laser.terminarTrayectoria();
        return null;
    }
}
