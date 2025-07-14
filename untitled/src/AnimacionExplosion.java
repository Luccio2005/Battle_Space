import java.awt.*;

public class AnimacionExplosion {
    private int x, y;
    private int contador = 0;
    private final int duracion = 30;

    public AnimacionExplosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void actualizar() {
        contador++;
    }

    public boolean haTerminado() {
        return contador >= duracion;
    }

    public void dibujar(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.setColor(Color.ORANGE);

        String[] frame;

        if (contador < duracion / 2) {
            frame = new String[]{
                    "    **    ",
                    "   ****   ",
                    "    **    "
            };
        } else {
            frame = new String[]{
                    "  * ** *  ",
                    "   ****   ",
                    "  * ** *  "
            };
        }

        for (int i = 0; i < frame.length; i++) {
            g.drawString(frame[i], x, y + i * 20);
        }
    }
}
