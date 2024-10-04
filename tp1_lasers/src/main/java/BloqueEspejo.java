public class BloqueEspejo extends Bloque {
    public BloqueEspejo() {
        this.movil = true;
    }

    @Override
    public Punto colisionar(Laser laser, Punto punto, Grilla grilla) {
        // cambiar direccion laser en funcion de en que punto se produjo la colision
        return null;
    }
}
