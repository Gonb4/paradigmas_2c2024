public enum Direccion {
    NORESTE(1, -1),
    SURESTE(1, 1),
    SUDOESTE(-1, 1),
    NOROESTE(-1, -1);

    private final int modificadorX;
    private final int modificadorY;

    Direccion(int modificadorX, int modificadorY) {
        this.modificadorX = modificadorX;
        this.modificadorY = modificadorY;
    }

    public Punto siguientePunto(Punto punto) {
        int sigX = punto.x + modificadorX;
        int sigY = punto.y + modificadorY;
        return new Punto(sigX, sigY);
    }
}
