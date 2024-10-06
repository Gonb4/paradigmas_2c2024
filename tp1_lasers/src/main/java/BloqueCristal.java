public class BloqueCristal extends Bloque {
    public BloqueCristal(Punto ptoCen) {
        super(ptoCen);
        this.movil = true;
        this.prioridadColision = 2;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // ptoExtra = logica para elegirlo en funcion de en cual se produjo la colision
        // laser.agregarPtoTrayectoria(ptoExtra)
        return null; // return ptoExtra;
    }
}
