import java.awt.Graphics;
import java.awt.Font;

public class nave {
    int x, y;  // posición central
    int velocidad = 10;

    public nave(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moverIzquierda() {
        x -= velocidad;
    }

    public void moverDerecha() {
        x += velocidad;
    }

    public void moverArriba() {
        y -= velocidad;
    }

    public void moverAbajo() {
        y += velocidad;
    }

    public void dibujar(Graphics g) {
        g.setFont(new Font("Monospaced", Font.PLAIN, 30));

        // Línea 1: ⮝
        g.drawString("\u2B9D", x, y);

        // Línea 2: ⯀
        g.drawString("\u2BC0", x, y + 14);

        // Línea 3: ◢ ⯀ ◣
        g.drawString("\u25E2", x - 17, y + 27);
        g.drawString("\u2BC0", x, y + 30);
        g.drawString("\u25E3", x + 17, y + 27);

        // Línea 4: ⮟
        g.drawString("\u2B9F", x, y + 45);
    }

    public Disparos disparar() {
        return new Disparos(x+5, y);  // disparo desde el centro superior
    }
}
