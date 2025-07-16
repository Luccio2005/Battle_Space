import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class VentanaJuego extends JPanel {
    private ArrayList<ENEMIGO> enemigos = new ArrayList<>();
    private ArrayList<ProyectilEnemigo> proyectiles = new ArrayList<>();
    private Timer timer;
    private Random random = new Random();
    private campodeBatalla campo;

    private Jefe jefe = null;
    private int enemigosEliminados = 0;

    public VentanaJuego() {
        setPreferredSize(new Dimension(500, 400));
        setBackground(Color.BLACK);
        campo = new campodeBatalla(20, 20); // campo lógico

        timer = new Timer(60, e -> actualizar());
        timer.start();
    }

    public void inicializarEnemigos() {
        for (int i = 0; i < 5; i++) {
            crearEnemigoAleatorio();
        }
    }

    private void crearEnemigoAleatorio() {
        int ancho = getWidth();
        if (ancho <= 60) ancho = 100;

        int intentos = 0;
        int maxIntentos = 20;
        while (intentos < maxIntentos) {
            int x = random.nextInt(ancho - 40);
            int fila = 0;
            int col = x / 20;
            if (!campo.estaOcupado(fila, col)) {
                int velocidad = 1 + random.nextInt(3);
                ENEMIGO nuevo = new ENEMIGO(x, 0, 100, velocidad);
                enemigos.add(nuevo);
                campo.colocarEnemigo(fila, col);
                break;
            }
            intentos++;
        }
    }

    private void actualizar() {
        Iterator<ENEMIGO> itEnemigos = enemigos.iterator();
        while (itEnemigos.hasNext()) {
            ENEMIGO enemigo = itEnemigos.next();
            enemigo.setY(enemigo.getY() + enemigo.getVelocidad());

            if (random.nextInt(50) < 1) {
                proyectiles.add(new ProyectilEnemigo(enemigo.getX() + 10, enemigo.getY() + 30, 6));
            }

            if (enemigo.getY() > getHeight()) {
                int fila = enemigo.getY() / 20;
                int col = enemigo.getX() / 20;
                campo.limpiarCelda(fila, col);
                itEnemigos.remove();
            }
        }

        // Mover proyectiles
        Iterator<ProyectilEnemigo> itProy = proyectiles.iterator();
        while (itProy.hasNext()) {
            ProyectilEnemigo p = itProy.next();
            p.mover();
            if (p.estaFueraDeCampo(getHeight())) {
                itProy.remove();
            }
        }

        // Crear jefe si no existe y se eliminaron suficientes enemigos
        if (jefe == null && enemigosEliminados >= 10) {
            jefe = new Jefe(getWidth() / 2 - 60, 100, 3, 20); // x, y, velocidad, vida
        }

        // Mover jefe
        if (jefe != null) {
            jefe.moverHorizontal(getWidth());
            if (random.nextInt(25) == 0) {
                // Ajustar para que los disparos salgan justo debajo del jefe
                proyectiles.add(new ProyectilEnemigo(jefe.getX() + jefe.getAncho() / 2, jefe.getY() + jefe.getAlto(), 6));
            }
        }

        // Agregar enemigos si no hay jefe todavía
        if (jefe == null && random.nextInt(100) < 1 && enemigos.size() < 10) {
            crearEnemigoAleatorio();
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        g.setColor(Color.WHITE);
        for (ENEMIGO enemigo : enemigos) {
            g.drawString("🛸", enemigo.getX(), enemigo.getY() + 30);
        }

        g.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        g.setColor(Color.YELLOW);
        for (ProyectilEnemigo p : proyectiles) {
            g.drawString("⇓", p.getX(), p.getY());
        }

        if (jefe != null) {
            jefe.dibujar(g);
        }
    }
}
