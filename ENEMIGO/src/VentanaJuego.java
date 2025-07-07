import javax.swing.*;
import java.awt.*;

public class VentanaJuego extends JPanel {
    private ENEMIGO enemigo;
    private int direccion = 1; // 1 = derecha, -1 = izquierda
    private Timer timer;

    public VentanaJuego() {
        enemigo = new ENEMIGO(100, 100, 150, 2);

        setPreferredSize(new Dimension(500, 300));
        setBackground(Color.BLACK);

        timer = new Timer(30, e -> actualizar());
        timer.start();
    }

    private void actualizar() {
        int nuevaX = enemigo.getX() + direccion * enemigo.getVelocidad();
        if (nuevaX <= 0 || nuevaX >= getWidth() - 60) {
            direccion *= -1;
        }
        enemigo.setX(enemigo.getX() + direccion * enemigo.getVelocidad());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;

        int x = enemigo.getX();
        int y = enemigo.getY();

        // Diseño Nave
        g2d.setColor(Color.RED);
        int[] px = { x + 10, x + 50, x + 40, x + 20 };
        int[] py = { y,      y,      y + 30,    y + 30 };
        g2d.fillPolygon(px, py, 4);

        // Ojo de Nave
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x + 23, y + 10, 5, 5);
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Battle Space - Nave Enemiga");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.add(new VentanaJuego());
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
