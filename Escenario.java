import java.io.*;
import java.util.ArrayList;

class Escenario {
    Elemento[][] campoDeBatalla;

    public Escenario(String nombre) {
        this.campoDeBatalla = new Elemento[10][10]; 
    }

    public void agregarElemento(Elemento e) {
        Posicion p = e.getPosicion();
        campoDeBatalla[p.getRenglon()][p.getColumna()] = e;
    }

    public void destruirElementos(Posicion p, int radio) {
        ArrayList<Elemento> elementos = new ArrayList<>();

        // Agregar elementos dentro del radio de alcance al ArrayList
        for (int i = Math.max(0, p.getRenglon() - radio); i <= Math.min(campoDeBatalla.length - 1, p.getRenglon() + radio); i++) {
            for (int j = Math.max(0, p.getColumna() - radio); j <= Math.min(campoDeBatalla[i].length - 1, p.getColumna() + radio); j++) {
                if (campoDeBatalla[i][j] != null) {
                    elementos.add(campoDeBatalla[i][j]);
                }
            }
        }

        // Filtrar los elementos destruibles e invocar el método destruir()
        for (Elemento elem : elementos) {
            if (elem instanceof Destruible) {
                Destruible d = (Destruible) elem;
                System.out.println(d.destruir());
                campoDeBatalla[elem.getPosicion().getRenglon()][elem.getPosicion().getColumna()] = null; // Eliminar del escenario
            }
        }
    }

    public void cargarEscenario(String archivo) throws IOException {
        File file = new File(archivo);
        if (!file.exists()) {
            System.out.println("Archivo de configuración no encontrado. Creando uno nuevo...");
            guardarEscenario(archivo);
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.trim().split(" ");
            if (partes.length < 3) {
                System.out.println("Formato incorrecto en la línea: " + linea);
                continue;
            }
            String tipo = partes[0];
            int renglon = Integer.parseInt(partes[1]);
            int columna = Integer.parseInt(partes[2]);
            Posicion posicion = new Posicion(renglon, columna);
            switch (tipo) {
                case "Roca":
                    agregarElemento(new Roca(this, posicion));
                    break;
                case "Extraterrestre":
                    agregarElemento(new Extraterrestre("Alien", this, posicion));
                    break;
                case "Terricola":
                    agregarElemento(new Terricola("Ripley", this, posicion));
                    break;
                case "Bomba":
                    if (partes.length < 4) {
                        System.out.println("Falta el parámetro de radio para la bomba en la línea: " + linea);
                        continue;
                    }
                    int radio = Integer.parseInt(partes[3]);
                    agregarElemento(new Bomba(this, posicion, radio));
                    break;
                default:
                    System.out.println("Tipo de elemento desconocido en la línea: " + linea);
                    break;
            }
        }
        br.close();
    }

    public void guardarEscenario(String archivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        for (int i = 0; i < campoDeBatalla.length; i++) {
            for (int j = 0; j < campoDeBatalla[i].length; j++) {
                Elemento e = campoDeBatalla[i][j];
                if (e != null) {
                    String tipo = e.getClass().getSimpleName();
                    bw.write(tipo + " " + i + " " + j);
                    if (e instanceof Bomba) {
                        bw.write(" " + ((Bomba) e).getRadio());
                    }
                    bw.newLine();
                }
            }
        }
        bw.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campoDeBatalla.length; i++) {
            for (int j = 0; j < campoDeBatalla[i].length; j++) {
                if (campoDeBatalla[i][j] instanceof Terricola) {
                    sb.append("T ");
                } else if (campoDeBatalla[i][j] instanceof Extraterrestre) {
                    sb.append("E ");
                } else if (campoDeBatalla[i][j] instanceof Roca) {
                    sb.append("R ");
                } else if (campoDeBatalla[i][j] instanceof Bomba) {
                    sb.append("B ");
                } else {
                    sb.append("0 ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
