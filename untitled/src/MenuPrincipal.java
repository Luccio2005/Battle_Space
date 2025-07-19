import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal extends JFrame {
    private String dificultad = "Medio";
    private Image fondo;

    public MenuPrincipal() {
        // Intenta cargar la imagen como recurso
        try {
            fondo = new ImageIcon("recursos/logo.jpg").getImage();
        } catch (Exception e) {
            System.out.println("⚠ Error al cargar la imagen: " + e.getMessage());
        }

        setTitle("Battle Space - Menú Principal");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel personalizado que dibuja el fondo
        JPanel panelFondo = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelFondo.setLayout(new BorderLayout());

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 10));
        panelBotones.setOpaque(false); // importante para que el fondo sea visible
        panelBotones.setBorder(BorderFactory.createEmptyBorder(120, 150, 120, 150));

        JButton btnIniciar = new JButton("Iniciar Juego");
        JButton btnInstrucciones = new JButton("Instrucciones");
        JButton btnDificultad = new JButton("Dificultad (" + dificultad + ")");
        JButton btnPuntuaciones = new JButton("Puntuaciones (futuro)");
        JButton btnSalir = new JButton("Salir");

        // Añadir botones
        panelBotones.add(btnIniciar);
        panelBotones.add(btnInstrucciones);
        panelBotones.add(btnDificultad);
        panelBotones.add(btnPuntuaciones);
        panelBotones.add(btnSalir);

        // Añadir al panel principal
        panelFondo.add(panelBotones, BorderLayout.CENTER);
        setContentPane(panelFondo);

        // Acciones
        btnIniciar.addActionListener(e -> {
            JFrame ventanaJuego = new JFrame("Battle Space");
            ventanaJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventanaJuego.setContentPane(new VentanaJuego(dificultad));
            ventanaJuego.pack();
            ventanaJuego.setLocationRelativeTo(null);
            ventanaJuego.setVisible(true);
            dispose();
        });

        btnInstrucciones.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "CONTROLES:\n" +
                            "- Mover: W, A, S, D\n" +
                            "- Disparar: Espacio\n" +
                            "- Pausar: ESC\n" +
                            "- Reanudar: P\n" +
                            "- Reiniciar: R\n\n" +
                            "Evita los proyectiles y colisiones. ¡Buena suerte!",
                    "Instrucciones",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnDificultad.addActionListener(e -> {
            String[] opciones = {"Fácil", "Medio", "Difícil"};
            String seleccion = (String) JOptionPane.showInputDialog(
                    this, "Selecciona la dificultad:",
                    "Dificultad",
                    JOptionPane.PLAIN_MESSAGE,
                    null, opciones, dificultad
            );
            if (seleccion != null) {
                dificultad = seleccion;
                btnDificultad.setText("Dificultad (" + dificultad + ")");
            }
        });

        btnPuntuaciones.setEnabled(true);
        btnPuntuaciones.addActionListener(e -> {
            java.util.List<String> top = Puntaje.obtenerTop(10);
            StringBuilder sb = new StringBuilder("🏆 TOP 10 JUGADORES 🏆\n\n");
            for (String linea : top) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    sb.append(partes[0]).append(" : ").append(partes[1]).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Puntuaciones", JOptionPane.INFORMATION_MESSAGE);
        });
        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
