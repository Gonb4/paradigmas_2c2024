public class BloqueCristal extends Bloque {
    public BloqueCristal(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = 2;
    }

    @Override
    public Punto colisionar(Laser laser, Punto ptoColision, Grilla grilla) {
        Punto ptoExtra = null;

        if (ptoColision.equals(ptoArriba)) {
            ptoExtra = ptoAbajo;
        } else if (ptoColision.equals(ptoAbajo)) {
            ptoExtra = ptoArriba;
        } else if (ptoColision.equals(ptoDerecha)) {
            ptoExtra = ptoIzquierda;
        } else if (ptoColision.equals(ptoIzquierda)) {
            ptoExtra = ptoDerecha;
        }
        laser.agregarPtoTrayectoria(ptoExtra);
        return ptoExtra;
    }
}
