public class campo {
    private String[][] campo;

    public CampoDeBatalla(int filas, int columnas) {
        campo = new String[filas][columnas];
        inicializarCampo();
    }

    private void inicializarCampo() {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[i].length; j++) {
                campo[i][j] = "."; 
            }
        }
    }

    public void mostrarCampo() {
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[i].length; j++) {
                System.out.print(campo[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void colocarEnemigo(int x, int y) {
        if (x >= 0 && x < campo.length && y >= 0 && y < campo[0].length) {
            campo[x][y] = "E"; 
        }
    }
}

