import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControladorEntrada {

    public static void procesarTecla(KeyEvent e, nave nave, ArrayList<Disparos> disparos, ControladorJuego controlador) {
        char tecla = Character.toLowerCase(e.getKeyChar());
        int code = e.getKeyCode();

        switch (tecla) {
            case 'a' -> nave.moverIzquierda();
            case 'd' -> nave.moverDerecha();
            case 'w' -> nave.moverArriba();
            case 's' -> nave.moverAbajo();
            case ' ' -> disparos.add(nave.disparar());
        }

        // Teclas rápidas
        if (code == KeyEvent.VK_P) controlador.pausar();
        if (code == KeyEvent.VK_R) controlador.reanudar();
        if (code == KeyEvent.VK_ENTER) controlador.reiniciar();

        // MENÚ: con tecla ESC
        if (code == KeyEvent.VK_ESCAPE) {
            controlador.pausar(); // Pausa el juego mientras se abre el menú

            String[] opciones = { "Reanudar", "Reiniciar", "Salir" };
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
                case 0 -> controlador.reanudar();     // Reanudar
                case 1 -> controlador.reiniciar();    // Reiniciar
                case 2 -> System.exit(0);             // Salir del juego
            }
        }
    }
}