public class ProyectilEnemigo {
    private int x;
    private int y;
    private int velocidad;

    public ProyectilEnemigo(int x, int y, int velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
    }

    public void mover() {
        y += velocidad; // Se mueve hacia abajo
    }

    public boolean estaFueraDeCampo(int limiteY) {
        return y >= limiteY;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocidad() {
        return velocidad;
    }

    // Setters (opcional, por si quieres cambiar valores en el futuro)
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}
