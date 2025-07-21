import javax.swing.*;
import java.util.ArrayList;

public class ControladorJuego {
    private Timer timer;
    private nave nave;
    private ArrayList<Disparos> disparos;

    public ControladorJuego(Timer timer, nave nave, ArrayList<Disparos> disparos) {
        this.timer = timer;
        this.nave = nave;
        this.disparos = disparos;
    }

    public void pausar() {
        timer.stop();
        System.out.println("Juego pausado");
    }

    public void reanudar() {
        timer.start();
        System.out.println("Juego reanudado");
    }

    public void reiniciar() {
        nave.setX(300);
        nave.setY(500);
        disparos.clear();
        System.out.println("Juego reiniciado");
        timer.start();
    }
}
