public class BloqueOpacoFijo extends Bloque {
    public BloqueOpacoFijo(Punto ptoCen) {
        super(ptoCen);
        this.movil = false;
        this.prioridadColision = Constantes.PRIORIDAD_COLISION_OPACO;
    }

    @Override
    public Punto colisionar(Laser laser, Punto ptoColision, Grilla grilla) {
        laser.terminarTrayectoria();
        return null;
    }
}
