public class ENEMIGO {
    private int x, y, vida, velocidad;

    public ENEMIGO(int x, int y, int vida, int velocidad) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.velocidad = velocidad;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVida() { return vida; }
    public int getVelocidad() { return velocidad; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setVida(int vida) { this.vida = vida; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }
}
