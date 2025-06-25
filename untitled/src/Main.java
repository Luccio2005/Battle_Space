import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JPanel {

    private int x = 250;
    private int y = 400;
    private final char nave = '\u2B9D'; // ⮝

    // Tamaño del campo
    private final int CAMPO_X = 10;
    private final int CAMPO_Y = 50;
    private final int CAMPO_ANCHO = 750;
    private final int CAMPO_ALTO = 490;

    public Main() {
        JFrame frame = new JFrame("Battle Space ");
        frame.setSize(800, 600); // ⬅️ Aumentamos el tamaño de la ventana
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

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
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Fondo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Marco del campo
        g.setColor(Color.WHITE);
        g.drawRect(CAMPO_X, CAMPO_Y, CAMPO_ANCHO, CAMPO_ALTO);

        // Nave
        g.setColor(Color.GREEN);
        g.setFont(new Font("Monospaced", Font.PLAIN, 24));
        g.drawString(String.valueOf(nave), x, y);
    }

    public static void main(String[] args) {
        new Main();
    }
}
