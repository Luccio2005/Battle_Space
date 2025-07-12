import java.awt.*;

public class nave {
    int x, y;
    int velocidad = 10;
    private int vida = 5; // NUEVO: vida de la nave

    public nave(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moverIzquierda() { x -= velocidad; }
    public void moverDerecha()   { x += velocidad; }
    public void moverArriba()    { y -= velocidad; }
    public void moverAbajo()     { y += velocidad; }

    public void dibujar(Graphics g) {
        g.setFont(new Font("Monospaced", Font.PLAIN, 30));
        g.setColor(Color.CYAN);
        g.drawString("\u2B9D", x, y);
        g.drawString("\u2BC0", x, y + 14);
        g.drawString("\u25E2", x - 17, y + 27);
        g.drawString("\u2BC0", x, y + 30);
        g.drawString("\u25E3", x + 17, y + 27);
        g.drawString("\u2B9F", x, y + 45);
    }

    public Disparos disparar() {
        return new Disparos(x + 5, y);
    }
    // NUEVOS MÉTODOS
    public void recibirDanio() {
        vida--;
        System.out.println("Nave recibió daño. Vida restante: " + vida);
    }

    public boolean estaDestruida() {
        return vida <= 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVida() { return vida; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}

