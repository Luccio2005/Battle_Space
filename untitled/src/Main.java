public class Main {
    public static void main(String[] args) {
        Campo campo = new Campo(100, 30);
        campo.dibujar();
    }
}
class Campo {
    private int ancho;
    private int alto;

    public Campo(int ancho, int alto) {
        if (ancho < 3 || alto < 3) {
            throw new IllegalArgumentException("El campo debe ser de al menos 3x3.");
        }
        this.ancho = ancho;
        this.alto = alto;
    }

    public void dibujar() {
        // Línea superior
        System.out.print("╔");
        for (int i = 0; i < ancho - 2; i++) {
            System.out.print("═");
        }
        System.out.println("╗");

        // Cuerpo
        for (int i = 0; i < alto - 2; i++) {
            System.out.print("║");
            for (int j = 0; j < ancho - 2; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // Línea inferior
        System.out.print("╚");
        for (int i = 0; i < ancho - 2; i++) {
            System.out.print("═");
        }
        System.out.println("╝");
    }
}
