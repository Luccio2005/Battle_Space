import java.awt.Graphics;
import java.awt.Color;
public class Disparos {
    int x, y;
    int velocidad = 15;

    public Disparos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        y -= velocidad;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("|", x, y);  // Símbolo del disparo
    }

    public boolean estaFuera() {
        return y < 0;
    }

}
