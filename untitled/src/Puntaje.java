import java.io.*;
import java.util.*;

public class Puntaje {
    private static final String ARCHIVO = "puntajes.txt";

    public static void guardar(String nombre, int puntaje) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(nombre + "," + puntaje);
        } catch (IOException e) {
            System.out.println("⚠ No se pudo guardar el puntaje: " + e.getMessage());
        }
    }

    public static List<String> obtenerTop(int cantidad) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) lineas.add(linea);
        } catch (IOException e) {
            return List.of("No hay puntajes aún.");
        }

        lineas.sort((a, b) -> {
            int pa = Integer.parseInt(a.split(",")[1]);
            int pb = Integer.parseInt(b.split(",")[1]);
            return Integer.compare(pb, pa); // descendente
        });

        return lineas.subList(0, Math.min(cantidad, lineas.size()));
    }
}