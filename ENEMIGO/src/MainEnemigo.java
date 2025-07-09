import javax.swing.*;

public class MainEnemigo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Batalla Espacial");
            VentanaJuego panel = new VentanaJuego();

            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setContentPane(panel);
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

            // Aplazar la creación de enemigos
            panel.inicializarEnemigos();
        });
    }
}
