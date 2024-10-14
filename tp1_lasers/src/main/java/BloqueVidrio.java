public class BloqueVidrio extends Bloque {
    public BloqueVidrio(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = Constantes.PRIORIDAD_COLISION_VIDRIO;
    }

    @Override
    public Punto colisionar(Laser laser, Punto ptoColision, Grilla grilla) {
        if (ptoColision.equals(ptoArriba)) {
            switch (laser.getDireccion()) {
                case SURESTE -> laser.bifurcar(grilla, ptoColision, Direccion.NORESTE);
                case SUDOESTE -> laser.bifurcar(grilla, ptoColision, Direccion.NOROESTE);
            }
        } else if (ptoColision.equals(ptoAbajo)) {
            switch (laser.getDireccion()) {
                case NORESTE -> laser.bifurcar(grilla, ptoColision, Direccion.SURESTE);
                case NOROESTE -> laser.bifurcar(grilla, ptoColision, Direccion.SUDOESTE);
            }
        } else if (ptoColision.equals(ptoDerecha)) {
            switch (laser.getDireccion()) {
                case SUDOESTE -> laser.bifurcar(grilla, ptoColision, Direccion.SURESTE);
                case NOROESTE -> laser.bifurcar(grilla, ptoColision, Direccion.NORESTE);
            }
        } else if (ptoColision.equals(ptoIzquierda)) {
            switch (laser.getDireccion()) {
                case SURESTE -> laser.bifurcar(grilla, ptoColision, Direccion.SUDOESTE);
                case NORESTE -> laser.bifurcar(grilla, ptoColision, Direccion.NOROESTE);
            }
        }
        return null;
    }
}
