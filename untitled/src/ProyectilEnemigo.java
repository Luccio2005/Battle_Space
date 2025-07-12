public class ProyectilEnemigo {
    private int x, y, velocidad;

    public ProyectilEnemigo(int x, int y, int velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
    }

    public void mover() {
        y += velocidad;
    }

    public boolean estaFueraDeCampo(int limiteY) {
        return y >= limiteY;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVelocidad() { return velocidad; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }
}

