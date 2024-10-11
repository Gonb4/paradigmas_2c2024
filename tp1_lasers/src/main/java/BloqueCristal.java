public class BloqueCristal extends Bloque {
    public BloqueCristal(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = 2;
    }

    @Override
    public Punto colisionar(Laser laser, Punto ptoColision, Grilla grilla) {
        Punto ptoExtra = null;
        var direcc = laser.getDireccion();

        if (ptoColision.equals(ptoArriba)) {
            if (direcc == Direccion.SURESTE || direcc == Direccion.SUDOESTE) {
                ptoExtra = ptoAbajo;
            }
        } else if (ptoColision.equals(ptoAbajo)) {
            if (direcc == Direccion.NORESTE || direcc == Direccion.NOROESTE) {
                ptoExtra = ptoArriba;
            }
        } else if (ptoColision.equals(ptoDerecha)) {
            if (direcc == Direccion.SUDOESTE || direcc == Direccion.NOROESTE) {
                ptoExtra = ptoIzquierda;
            }
        } else if (ptoColision.equals(ptoIzquierda)) {
            if (direcc == Direccion.SURESTE || direcc == Direccion.NORESTE) {
                ptoExtra = ptoDerecha;
            }
        }
        if (ptoExtra != null) {laser.agregarPtoTrayectoria(ptoExtra);}
        return ptoExtra;
    }
}
