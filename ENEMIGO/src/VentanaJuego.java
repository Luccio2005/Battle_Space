import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class VentanaJuego extends JPanel {
    private ArrayList<ENEMIGO> enemigos = new ArrayList<>();
    private ArrayList<ProyectilEnemigo> proyectiles = new ArrayList<>();
    private Timer timer;
    private Random random = new Random();
    private campodeBatalla campo;
    private int cicloDisparo = 0;

    public VentanaJuego() {
        setPreferredSize(new Dimension(500, 400));
        setBackground(Color.BLACK);
        campo = new campodeBatalla(20, 20);

        // Crear enemigos iniciales
        for (int i = 0; i < 5; i++) {
            crearEnemigoAleatorio();
        }

        timer = new Timer(60, e -> actualizar());
        timer.start();
    }

    private void crearEnemigoAleatorio() {
        int anchoVentana = getPreferredSize().width;
        int x = random.nextInt(anchoVentana - 40);
        int velocidad = 1 + random.nextInt(2);

        ENEMIGO nuevo = new ENEMIGO(x, 0, 100, velocidad);
        enemigos.add(nuevo);
    }

    private void actualizar() {
        cicloDisparo++;

        // Mover enemigos y disparar en intervalos
        Iterator<ENEMIGO> itEnemigos = enemigos.iterator();
        while (itEnemigos.hasNext()) {
            ENEMIGO enemigo = itEnemigos.next();
            enemigo.setY(enemigo.getY() + enemigo.getVelocidad());

            // Cada 20 ciclos, disparan
            if (cicloDisparo % 20 == 0) {
                proyectiles.add(new ProyectilEnemigo(enemigo.getX() + 10, enemigo.getY() + 30, 6));
            }

            // Eliminar enemigos fuera de pantalla
            if (enemigo.getY() > getHeight()) {
                itEnemigos.remove();
            }
        }

        // Mover proyectiles enemigos
        Iterator<ProyectilEnemigo> itProy = proyectiles.iterator();
        while (itProy.hasNext()) {
            ProyectilEnemigo p = itProy.next();
            p.mover();
            if (p.estaFueraDeCampo(getHeight())) {
                itProy.remove();
            }
        }

        // Aparecen enemigos nuevos (puedes ajustar esto si también quieres quitar el random aquí)
        if (enemigos.size() < 10 && cicloDisparo % 60 == 0) {
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
    }
}
