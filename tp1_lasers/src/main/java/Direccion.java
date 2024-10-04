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


    public int siguienteX(int x) {
        return x + modificadorX;
    }

    public int siguienteY(int y) {
        return y + modificadorY;
    }
}
