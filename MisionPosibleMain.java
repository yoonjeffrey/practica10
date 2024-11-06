import java.io.IOException;
import java.util.Scanner;

public class MisionPosibleMain {
    public static void main(String[] args) {
        try {
            Escenario e = new Escenario("Nostromo");
            e.cargarEscenario("escenario.txt");
            System.out.println(e);

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Ingrese la posición de la bomba a detonar (renglon columna): ");
                int renglon = scanner.nextInt();
                int columna = scanner.nextInt();
                Elemento elemento = e.campoDeBatalla[renglon][columna];

                if (elemento instanceof Bomba) {
                    ((Bomba) elemento).explotar();
                } else {
                    System.out.println("No hay una bomba en esa posición.");
                }
            }
            System.out.println(e);
            e.guardarEscenario("escenario.txt");
        } catch (IOException ex) {
            System.out.println("Error al leer o escribir el archivo de configuración: " + ex.getMessage());
        }
    
    }
}
