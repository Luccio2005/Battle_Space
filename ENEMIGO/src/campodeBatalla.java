public class campodeBatalla {
    private String[][] campo;
    private int filas;
    private int columnas;

    public campodeBatalla(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        campo = new String[filas][columnas];
        inicializarCampo();
    }

    private void inicializarCampo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                campo[i][j] = "."; // Campo vacío
            }
        }
    }

    public void colocarEnemigo(int x, int y) {
        if (x >= 0 && x < filas && y >= 0 && y < columnas) {
            campo[x][y] = "E"; // Colocar enemigo
        }
    }

    public void limpiarCelda(int x, int y) {
        if (x >= 0 && x < filas && y >= 0 && y < columnas) {
            campo[x][y] = ".";
        }
    }

    // Evitar Bugs
    public boolean estaOcupado(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return campo[fila][columna].equals("E");
        }
        return false;
    }

    // Pre Visualizacin del Campo
    public void mostrarCampo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(campo[i][j] + " ");
            }
            System.out.println();
        }
    }
}
