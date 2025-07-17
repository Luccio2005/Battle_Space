
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.Rectangle;


public class VentanaJuego extends JPanel implements KeyListener {

    private nave nave;
    private ArrayList<Disparos> disparos;
    private ArrayList<ENEMIGO> enemigos;
    private ArrayList<ProyectilEnemigo> proyectiles;
    private campodeBatalla campo;
    private Timer timer;
    private ControladorJuego controlador;
    private Random random = new Random();
    private AnimacionExplosion explosion;
    private boolean naveDestruida = false;

    // Agregar el jefe
    private Jefe jefe = null;
    private int contadorDisparoJefe = 0;  // Controla cuándo dispara el jefe

    public VentanaJuego() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.BLACK);

        nave = new nave(200, 100);
        disparos = new ArrayList<>();
        enemigos = new ArrayList<>();
        proyectiles = new ArrayList<>();
        campo = new campodeBatalla(30, 30);

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(30, e -> actualizarJuego());
        controlador = new ControladorJuego(timer, nave, disparos);
        timer.start();
    }

    private void actualizarJuego() {
        for (Disparos d : disparos) d.mover();
        disparos.removeIf(d -> d.estaFuera());

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

        proyectiles.removeIf(p -> {
            p.mover();
            return p.estaFueraDeCampo(getHeight());
        });

        if (random.nextInt(100) < 1 && enemigos.size() < 10) {
            crearEnemigoAleatorio();
            Iterator<ProyectilEnemigo> itProy = proyectiles.iterator();
        }
        Iterator<ENEMIGO> itEnemigos2 = enemigos.iterator();
        Rectangle rNave = new Rectangle(nave.getX(), nave.getY(), 30, 45);

        while (itEnemigos2.hasNext()) {
            ENEMIGO enemigo = itEnemigos2.next();
            Rectangle rEnemigo = new Rectangle(enemigo.getX(), enemigo.getY(), 30, 30);

            if (rNave.intersects(rEnemigo)) {
                itEnemigos2.remove(); // eliminar enemigo
                nave.recibirDanio(); // dañar nave

                if (nave.estaDestruida()) {
                    naveDestruida = true;
                    explosion = new AnimacionExplosion(nave.getX(), nave.getY());
                }
                break;
            }
        }


        // --- Manejar jefe ---
        if (jefe == null && enemigos.size() >= 10) {
            // Cuando hay 10 enemigos, aparece el jefe (por ejemplo)
            jefe = new Jefe(100, 50, 3, 10);
        }

        if (jefe != null) {
            jefe.moverHorizontal(getWidth());

            // Controlar disparo del jefe cada cierto tiempo
            contadorDisparoJefe++;
            if (contadorDisparoJefe >= 60) {  // Cada 60 ciclos aprox
                jefe.disparar(proyectiles);
                contadorDisparoJefe = 0;
            }

            // El jefe baja lentamente (opcional)
            // jefe.setY(jefe.getY() + 1);

            // Si el jefe muere, quitarlo
            if (jefe.getVida() <= 0) {
                jefe = null;
                // Quizás añadir más lógica al ganar jefe
            }
        }

        detectarColisiones();

        // Detectar colisiones entre disparos y proyectiles enemigos
        Iterator<Disparos> itDisparos = disparos.iterator();
        while (itDisparos.hasNext()) {
            Disparos disparo = itDisparos.next();
            Rectangle rDisparo = new Rectangle(disparo.x, disparo.y, 5, 10);

            Iterator<ProyectilEnemigo> itProy = proyectiles.iterator();
            while (itProy.hasNext()) {
                ProyectilEnemigo pEnemigo = itProy.next();
                Rectangle rProyEnemigo = new Rectangle(pEnemigo.getX(), pEnemigo.getY(), 5, 10);

                if (rDisparo.intersects(rProyEnemigo)) {
                    itDisparos.remove();
                    itProy.remove();
                    break;
                }
            }
        }


        // Detectar colisiones entre disparos y jefe
        if (jefe != null) {
            itDisparos = disparos.iterator();
            Rectangle rJefe = jefe.getBounds();
            while (itDisparos.hasNext()) {
                Disparos d = itDisparos.next();
                Rectangle rDisparo = new Rectangle(d.x, d.y, 5, 10);

                if (rDisparo.intersects(rJefe)) {
                    itDisparos.remove();
                    jefe.recibirDanio();
                    break;
                }
            }
        }

        repaint();

        Iterator<ProyectilEnemigo> itProy = proyectiles.iterator();
        while (itProy.hasNext()) {
            ProyectilEnemigo p = itProy.next();
            p.mover();

            Rectangle rProy = new Rectangle(p.getX(), p.getY(), 5, 10);
            rNave.setBounds(nave.getX(), nave.getY(), 30, 45);

            if (rProy.intersects(rNave)) {
                itProy.remove();
                nave.recibirDanio();
                if (nave.estaDestruida()) {
                    naveDestruida = true;
                    explosion = new AnimacionExplosion(nave.getX(), nave.getY());
                }
            } else if (p.estaFueraDeCampo(getHeight())) {
                itProy.remove();
            }
        }

        if (naveDestruida && explosion != null) {
            explosion.actualizar();
            if (explosion.haTerminado()) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "¡La nave fue destruida!", "Game Over", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }

        repaint();
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
                ENEMIGO enemigo = new ENEMIGO(x, 0, 1, velocidad);
                enemigos.add(enemigo);
                campo.colocarEnemigo(fila, col);
                break;
            }
            intentos++;
        }
    }

    private void detectarColisiones() {
        Iterator<Disparos> itDisparos = disparos.iterator();
        while (itDisparos.hasNext()) {
            Disparos d = itDisparos.next();
            Rectangle rDisparo = new Rectangle(d.x, d.y, 5, 10);

            Iterator<ENEMIGO> itEnemigos = enemigos.iterator();
            while (itEnemigos.hasNext()) {
                ENEMIGO enemigo = itEnemigos.next();
                Rectangle rEnemigo = new Rectangle(enemigo.getX(), enemigo.getY(), 30, 30);

                if (rDisparo.intersects(rEnemigo)) {
                    itDisparos.remove();
                    enemigo.setVida(enemigo.getVida() - 1);
                    if (enemigo.getVida() <= 0) {
                        int fila = enemigo.getY() / 20;
                        int col = enemigo.getX() / 20;
                        campo.limpiarCelda(fila, col);
                        itEnemigos.remove();
                    }
                    break;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (naveDestruida && explosion != null) {
            explosion.dibujar(g);
            return;
        }

        nave.dibujar(g);

        for (Disparos d : disparos) d.dibujar(g);

        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        g.setColor(Color.WHITE);
        for (ENEMIGO enemigo : enemigos) {
            g.drawString("🛸", enemigo.getX(), enemigo.getY() + 30);
        }

        if (jefe != null) {
            jefe.dibujar(g);
        }

        g.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        g.setColor(Color.YELLOW);
        for (ProyectilEnemigo p : proyectiles) {
            g.drawString("⇓", p.getX(), p.getY());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ControladorEntrada.procesarTecla(e, nave, disparos, controlador);
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}


