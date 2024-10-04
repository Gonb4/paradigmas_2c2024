public abstract class Bloque {
    Punto ptoCentro;
    Punto ptoArriba;
    Punto ptoAbajo;
    Punto ptoDerecha;
    Punto ptoIzquierda;
    boolean movil;

    public void mover(Punto ptoCen, Punto ptoArr, Punto ptoAbj, Punto ptoDer, Punto ptoIzq) { // creo que va a tener que recibir localidades en vez de puntos
        //if movil -> pide ocupar las 5 localidades -> si las ocupa (todas devuelven true) cambia sus 5 puntos y devuelve true
        this.ptoCentro = ptoCen;
        this.ptoArriba = ptoArr;
        this.ptoAbajo = ptoAbj;
        this.ptoDerecha = ptoDer;
        this.ptoIzquierda = ptoIzq;
    }

    public abstract Punto colisionar(Laser laser, Punto punto, Grilla grilla); // devuelve null o un puntoExtra (creo que solo el bloque de cristal)
}
