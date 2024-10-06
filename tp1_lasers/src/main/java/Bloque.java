public abstract class Bloque {
    Punto ptoCentro;
    Punto ptoArriba;
    Punto ptoAbajo;
    Punto ptoDerecha;
    Punto ptoIzquierda;
    boolean movil;
    int prioridadColision;

    public Bloque(Punto ptoCen) {
        establecerPuntos(ptoCen);
    }

    public boolean mover(Punto nuevoPtoCen) {
        if (this.movil) {
            establecerPuntos(nuevoPtoCen);
            return true;
        }
        return false;
    }

    public abstract Punto colisionar(Laser laser, Punto punto, Grilla grilla); // devuelve null o un puntoExtra (creo que solo el bloque de cristal)

    private void establecerPuntos(Punto ptoCen) {
        this.ptoCentro = ptoCen;
        this.ptoArriba = new Punto(ptoCen.x, ptoCen.y - 1);
        this.ptoAbajo = new Punto(ptoCen.x, ptoCen.y + 1);
        this.ptoDerecha = new Punto(ptoCen.x + 1, ptoCen.y);
        this.ptoIzquierda = new Punto(ptoCen.x - 1, ptoCen.y);
    }

    public int getPrioridadColision() {
        return prioridadColision;
    }
}
