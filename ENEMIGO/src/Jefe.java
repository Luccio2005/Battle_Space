import java.awt.*;
import java.util.ArrayList;

public class Jefe {
    private int x, y;
    private int velocidad;
    private final int ancho = 240;
    private final int alto = 160;
    private int vida;

    public Jefe(int x, int y, int velocidad, int vida) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = vida;
    }

    public void disparar(ArrayList<ProyectilEnemigo> proyectiles) {
        int origenY = y + 10; // Ajuste de proyectile
        proyectiles.add(new ProyectilEnemigo(x + 20, origenY, 4));
        proyectiles.add(new ProyectilEnemigo(x + ancho - 40, origenY, 4));
    }


    public boolean fueraDePantalla(int alturaVentana) {
        return y > alturaVentana;
    }

    public void moverHorizontal(int anchoVentana) {
        x += velocidad;
        if (x <= 0 || x + ancho >= anchoVentana) {
            velocidad *= -1;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVelocidad() { return velocidad; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getVida() { return vida; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }
    public void recibirDanio() { vida--; }

    public void dibujar(Graphics g) {
        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 96));
        g.setColor(Color.RED);
        g.drawString("👾", x, y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
}


    public boolean fueraDePantalla(int alturaVentana) {
        return y > alturaVentana;
    }

    public void moverHorizontal(int anchoVentana) {
        x += velocidad;
        if (x <= 0 || x + ancho >= anchoVentana) {
            velocidad *= -1;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVelocidad() { return velocidad; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getVida() { return vida; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }
    public void recibirDanio() { vida--; }

    public void dibujar(Graphics g) {
        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 96));
        g.setColor(Color.RED);
        g.drawString("👾", x, y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
}
