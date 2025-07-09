public class colisiones {
    public static boolean detectar(int disparoX, int disparoY, int objX, int objY, int ancho, int alto) {
        return disparoX >= objX && disparoX <= objX + ancho &&
                disparoY >= objY && disparoY <= objY + alto;
    }
}
