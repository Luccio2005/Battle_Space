public class colisiones {

    /**
     * Verifica si un disparo ha colisionado con un objeto rectangular.
     *
     * @param disparoX Coordenada X del disparo
     * @param disparoY Coordenada Y del disparo
     * @param objX     Coordenada X del objeto
     * @param objY     Coordenada Y del objeto
     * @param ancho    Ancho del objeto
     * @param alto     Alto del objeto
     * @return true si hay colisión
     */
    public static boolean detectar(int disparoX, int disparoY, int objX, int objY, int ancho, int alto) {
        return disparoX >= objX && disparoX <= objX + ancho &&
                disparoY >= objY && disparoY <= objY + alto;
    }
}

