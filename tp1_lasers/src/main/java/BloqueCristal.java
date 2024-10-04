public class BloqueCristal extends Bloque {
    public BloqueCristal() {
        this.movil = true;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // ptoExtra = logica para elegirlo en funcion de en cual se produjo la colision
        // laser.agregarPtoTrayectoria(ptoExtra)
        return null; // return ptoExtra;
    }
}
