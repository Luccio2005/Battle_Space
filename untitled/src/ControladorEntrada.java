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

        if (code == KeyEvent.VK_P) controlador.pausar();
        if (code == KeyEvent.VK_R) controlador.reanudar();
        if (code == KeyEvent.VK_ENTER) controlador.reiniciar();

        if (code == KeyEvent.VK_ESCAPE) {
            controlador.pausar();
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
                case 0 -> controlador.reanudar();
                case 1 -> controlador.reiniciar();
                case 2 -> System.exit(0);
            }
        }
    }
}
