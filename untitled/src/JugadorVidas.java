public class Jugador Vidas {
    private int x, y;
    private int vidas = 5;
    private boolean estaVivo = true;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void recibirDaño() {
        if (vidas > 0) {
            vidas--;
            System.out.println("¡Daño recibido! Vidas restantes: " + vidas);
            if (vidas == 0) {
                estaVivo = false;
                System.out.println("¡Game Over!");
            }
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 40, 20); 

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.RED);
        g.drawString("Vidas: " + vidas, 10, 20);
    }

    public boolean estaVivo() {
        return estaVivo;
    }

    public void mover(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
