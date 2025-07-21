import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Dimension;

public class ControladorEntrada {

    public static void procesarTecla(KeyEvent e, nave nave, ArrayList<Disparos> disparos, ControladorJuego controlador, Component ventana) {
        char tecla = Character.toLowerCase(e.getKeyChar());
        int code = e.getKeyCode();

        Dimension size = ventana.getSize();
        int ancho = size.width;
        int alto = size.height;

        switch (tecla) {
            case 'a' -> nave.moverIzquierda(10);      // 10 píxeles desde el borde izquierdo
            case 'd' -> nave.moverDerecha(ancho - 10); // 10 píxeles desde el borde derecho
            case 'w' -> nave.moverArriba(80);         // 20 píxeles desde arriba
            case 's' -> nave.moverAbajo(alto - 10);   // 10 píxeles desde abajo
            case ' ' -> disparos.add(nave.disparar());
        }

        if (code == KeyEvent.VK_P) controlador.pausar();
        if (code == KeyEvent.VK_R) controlador.reanudar();
        if (code == KeyEvent.VK_ENTER) controlador.reiniciar();

        if (code == KeyEvent.VK_ESCAPE) {
            controlador.pausar();
            String[] opciones = { "Reanudar", "Reiniciar", "Menú Principal", "Salir" };
            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Menú de pausa",
                    "Battle Space",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (seleccion) {
                case 0 -> controlador.reanudar();
                case 1 -> controlador.reiniciar();
                case 2 -> {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ventana);
                    frame.dispose();
                    new MenuPrincipal();
                }
                case 3 -> System.exit(0);
            }
        }
    }
}
