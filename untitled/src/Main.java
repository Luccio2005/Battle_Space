import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JPanel implements KeyListener {

    nave nave;
    ArrayList<Disparos> disparos = new ArrayList<>();
    Timer timer;

    public Main() {
        nave = new nave(200, 100);

        // Timer para animación (disparos que suben)
        timer = new Timer(30, e -> {
            for (Disparos d : disparos) {
                d.mover();
            }
            disparos.removeIf(d -> d.estaFuera());
            repaint();
        });
        timer.start();

        // Configuración ventana
        JFrame frame = new JFrame("Nave con Disparos");
        frame.setSize(600, 600);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        nave.dibujar(g);
        for (Disparos d : disparos) {
            d.dibujar(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char tecla = Character.toLowerCase(e.getKeyChar());
        if (tecla == 'a') nave.moverIzquierda();
        if (tecla == 'd') nave.moverDerecha();
        if (tecla == 'w') nave.moverArriba();
        if (tecla == 's') nave.moverAbajo();
        if (tecla == ' ') disparos.add(nave.disparar());
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Main();
    }
}
