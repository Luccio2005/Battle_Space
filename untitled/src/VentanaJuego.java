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
    private boolean jefeDerrotado = false;
    private boolean naveDestruida = false;
    private boolean juegoGanado = false;
    private String dificultad = "Medio";
    private int enemigosEliminados = 0; // Contador de enemigos eliminados (como en la Versión 1)

    // Jefe
    private Jefe jefe = null;
    private int contadorDisparoJefe = 0;

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

    public VentanaJuego(String dificultad) {
        this();
        this.dificultad = dificultad;
    }

    private void actualizarJuego() {
        // Mover disparos y eliminar los que salen de la pantalla
        for (Disparos d : disparos) d.mover();
        disparos.removeIf(d -> d.estaFuera());

        // Mover enemigos y generar proyectiles
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

        // Mover proyectiles enemigos y eliminar los que salen de la pantalla
        proyectiles.removeIf(p -> {
            p.mover();
            return p.estaFueraDeCampo(getHeight());
        });

        // Generar enemigos
        int frecuencia = switch (dificultad) {
            case "Fácil" -> 130;
            case "Difícil" -> 70;
            default -> 100;
        };

        if (random.nextInt(frecuencia) < 1 && enemigos.size() < 10) {
            crearEnemigoAleatorio();
        }

        // Detectar colisiones entre nave y enemigos
        Iterator<ENEMIGO> itEnemigos2 = enemigos.iterator();
        Rectangle rNave = new Rectangle(nave.getX(), nave.getY(), 30, 45);

        while (itEnemigos2.hasNext()) {
            ENEMIGO enemigo = itEnemigos2.next();
            Rectangle rEnemigo = new Rectangle(enemigo.getX(), enemigo.getY(), 30, 30);

            if (rNave.intersects(rEnemigo)) {
                itEnemigos2.remove();
                nave.recibirDanio();

                if (nave.estaDestruida()) {
                    naveDestruida = true;
                    explosion = new AnimacionExplosion(nave.getX(), nave.getY());
                }
                break;
            }
        }

        // Aparicion Jefe
        if (jefe == null && enemigosEliminados >= 6) { // Aparece después de 6 enemigos eliminados
            jefe = new Jefe(getWidth() / 2 - 120, 200, 4, nave.getVida() * 5);
        }

        if (jefe != null) {
            jefe.moverHorizontal(getWidth());

            // Disparo controlado por tiempo
            contadorDisparoJefe++;
            if (contadorDisparoJefe >= 60) {
                jefe.disparar(proyectiles);
                contadorDisparoJefe = 0;
            }

            if (jefe != null && jefe.getVida() <= 0 && !jefeDerrotado) {
                jefe = null;
                jefeDerrotado = true;
                juegoGanado = true;
                timer.stop();
                String nombre = JOptionPane.showInputDialog(this, "¡Has ganado! Ingresa tu nombre (4 letras):");
                if (nombre != null) {
                    if (nombre.length() > 4) nombre = nombre.substring(0, 4);
                    Puntaje.guardar(nombre, enemigosEliminados);
                }
                repaint();
            }
        }

        // Detección de colisiones (disparos vs enemigos, disparos vs jefe)
        detectarColisiones();

        // Colisión entre disparos y proyectiles enemigos
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

        // Colisión entre disparos y jefe
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

        // Colisión entre proyectiles enemigos y nave
        Iterator<ProyectilEnemigo> itProy = proyectiles.iterator();
        while (itProy.hasNext()) {
            ProyectilEnemigo p = itProy.next();
            Rectangle rProy = new Rectangle(p.getX(), p.getY(), 5, 10);

            if (rProy.intersects(rNave)) {
                itProy.remove();
                nave.recibirDanio();
                if (nave.estaDestruida()) {
                    naveDestruida = true;
                    explosion = new AnimacionExplosion(nave.getX(), nave.getY());
                }
            }
        }

        // Animación de explosión si la nave es destruida
        if (naveDestruida && explosion != null) {
            explosion.actualizar();
            if (explosion.haTerminado()) {
                timer.stop();
                String nombre = JOptionPane.showInputDialog(this, "Game Over. Ingresa tu nombre (4 letras):");
                if (nombre != null) {
                    if (nombre.length() > 4) nombre = nombre.substring(0, 4);
                    Puntaje.guardar(nombre, enemigosEliminados);
                }
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
                int velocidadBase = switch (dificultad) {
                    case "Fácil" -> 1;
                    case "Difícil" -> 3;
                    default -> 2; // Medio
                };

                int velocidad = velocidadBase + random.nextInt(2);
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
                        enemigosEliminados++; // Incrementa el contador de enemigos eliminados
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
        if (juegoGanado) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Segoe UI Emoji", Font.BOLD, 48));
            g.drawString("🎉 YOU WIN 🎉", getWidth() / 2 - 150, getHeight() / 2);
        }

        nave.dibujar(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.drawString("Dificultad ♦: " + dificultad, 10, 20);
        g.drawString("Vidas ♥: " + nave.getVida(), 10, 40);
        g.drawString("Puntaje ▄ : " + enemigosEliminados, 10, 60); // Muestra progreso

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
        ControladorEntrada.procesarTecla(e, nave, disparos, controlador, this);
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public boolean isJefeDerrotado() {
        return jefeDerrotado;
    }

    public void setJefeDerrotado(boolean jefeDerrotado) {
        this.jefeDerrotado = jefeDerrotado;
    }

    public boolean isJuegoGanado() {
        return juegoGanado;
    }

    public void setJuegoGanado(boolean juegoGanado) {
        this.juegoGanado = juegoGanado;}}

