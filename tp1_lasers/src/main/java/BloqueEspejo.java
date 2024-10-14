public class BloqueEspejo extends Bloque {
    public BloqueEspejo(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = Constantes.PRIORIDAD_COLISION_ESPEJO;
    }

    @Override
    public Punto colisionar(Laser laser, Punto ptoColision, Grilla grilla) {
        // cambiar direccion laser en funcion de en que punto se produjo la colision
        if (ptoColision.equals(ptoArriba)) {
            switch (laser.getDireccion()) {
                case SURESTE -> laser.cambiarDireccion(Direccion.NORESTE);
                case SUDOESTE -> laser.cambiarDireccion(Direccion.NOROESTE);
            }
        } else if (ptoColision.equals(ptoAbajo)) {
            switch (laser.getDireccion()) {
                case NORESTE -> laser.cambiarDireccion(Direccion.SURESTE);
                case NOROESTE -> laser.cambiarDireccion(Direccion.SUDOESTE);
            }
        } else if (ptoColision.equals(ptoDerecha)) {
            switch (laser.getDireccion()) {
                case SUDOESTE -> laser.cambiarDireccion(Direccion.SURESTE);
                case NOROESTE -> laser.cambiarDireccion(Direccion.NORESTE);
            }
        } else if (ptoColision.equals(ptoIzquierda)) {
            switch (laser.getDireccion()) {
                case SURESTE -> laser.cambiarDireccion(Direccion.SUDOESTE);
                case NORESTE -> laser.cambiarDireccion(Direccion.NOROESTE);
            }
        }
        return null;
    }
}
