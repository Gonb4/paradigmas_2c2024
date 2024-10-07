public class Punto {
    public final int x;
    public final int y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Punto otroPto) {
        return (this.x == otroPto.x && this.y == otroPto.y);
    }
}
