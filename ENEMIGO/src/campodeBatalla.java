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
            campo[x][y] = "."; // Limpiar celda
        }
    }

    public boolean estaOcupado(int x, int y) {
        if (x >= 0 && x < filas && y >= 0 && y < columnas) {
            return !campo[x][y].equals(".");
        }
        return false;
    }

    public void mostrarCampo() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(campo[i][j] + " ");
            }
            System.out.println();
        }
    }
}
