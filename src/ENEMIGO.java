public class ENEMIGO {
    private String tipo;
    private int vida;
    private int x;
    private int y;
    private int velocidad;


    public ENEMIGO(int x, int y, int vida, int velocidad) {
        this.tipo = "Alien";
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.velocidad = velocidad;
    }

    public void mover() {
        y += velocidad;
    }

    public void recibirDanio(int cantidad) {
        cantidad=100;
        vida -= cantidad;
        if (vida <= 0) {
            vida = 0;
            System.out.println("BOOM!");
        }
    }

    public boolean Eliminacion() {
        return vida <= 0;
    }

    public void Spawn() {
        System.out.println("[Alien] Posición: (" + x + ", " + y + ") - Vida: " + vida + " - Velocidad: " + velocidad);
    }


    public String getTipo() {
        return tipo;
    }

    public int getVida() {
        return vida;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

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
