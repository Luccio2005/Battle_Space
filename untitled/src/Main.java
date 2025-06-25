import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Main extends JPanel {

    private int x = 250;
    private int y = 400;
    private final char nave = '\u2B9D'; // ⮝
    private final char enemigoChar = '\u237E'; // ⍾

    // Campo
    private final int CAMPO_X = 10;
    private final int CAMPO_Y = 50;
    private final int CAMPO_ANCHO = 750;
    private final int CAMPO_ALTO = 490;

    // Enemigos
    private final ArrayList<Enemigo> enemigos = new ArrayList<>();
    private final Random random = new Random();

    public Main() {
        JFrame frame = new JFrame("Battle Space");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

        // Movimiento del jugador
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int paso = 10;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> {
                        if (y - paso >= CAMPO_Y + 20) y -= paso;
                    }
                    case KeyEvent.VK_S -> {
                        if (y + paso <= CAMPO_Y + CAMPO_ALTO - 10) y += paso;
                    }
                    case KeyEvent.VK_A -> {
                        if (x - paso >= CAMPO_X + 10) x -= paso;
                    }
                    case KeyEvent.VK_D -> {
                        if (x + paso <= CAMPO_X + CAMPO_ANCHO - 20) x += paso;
                    }
                }
                repaint();
            }
        });

        // Temporizador para animación del juego
        Timer timer = new Timer(100, e -> {
            generarEnemigos();
            moverEnemigos();
            repaint();
        });
        timer.start();
    }

    // Dibujo principal
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Fondo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Marco
        g.setColor(Color.WHITE);
        g.drawRect(CAMPO_X, CAMPO_Y, CAMPO_ANCHO, CAMPO_ALTO);

        // Dibujar nave
        g.setColor(Color.GREEN);
        g.setFont(new Font("Monospaced", Font.PLAIN, 24));
        g.drawString(String.valueOf(nave), x, y);

        // Dibujar enemigos
        g.setColor(Color.RED);
        for (Enemigo enemigo : enemigos) {
            g.drawString(String.valueOf(enemigoChar), enemigo.x, enemigo.y);
        }
    }

    // Crear nuevos enemigos aleatorios
    private void generarEnemigos() {
        if (random.nextDouble() < 0.1) { // 10% de probabilidad cada ciclo
            int enemigoX = CAMPO_X + 20 + random.nextInt(CAMPO_ANCHO - 40);
            enemigos.add(new Enemigo(enemigoX, CAMPO_Y + 10));
        }
    }

    // Mover enemigos hacia abajo y eliminar los que salen del campo
    private void moverEnemigos() {
        Iterator<Enemigo> iterator = enemigos.iterator();
        while (iterator.hasNext()) {
            Enemigo enemigo = iterator.next();
            enemigo.y += 10;
            if (enemigo.y > CAMPO_Y + CAMPO_ALTO) {
                iterator.remove(); // fuera del campo
            }
        }
    }

    // Clase interna para enemigos
    static class Enemigo {
        int x, y;

        public Enemigo(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}

