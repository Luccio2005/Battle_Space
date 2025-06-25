import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JPanel implements KeyListener {

    nave nave;

    public Main() {
        nave = new nave(200, 100);
        JFrame frame = new JFrame("Nave Unicode Completa");
        frame.setSize(600, 600);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        nave.dibujar(g);
    }

    public void keyPressed(KeyEvent e) {
        char tecla = Character.toLowerCase(e.getKeyChar());
        if (tecla == 'a') nave.moverIzquierda();
        if (tecla == 'd') nave.moverDerecha();
        if (tecla == 'w') nave.moverArriba();
        if (tecla == 's') nave.moverAbajo();
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Main();
    }
}
