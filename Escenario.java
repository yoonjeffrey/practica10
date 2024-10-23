import java.util.ArrayList;

class Escenario {
    private Elemento[][] campoDeBatalla;

    public Escenario(String nombre) {
        this.campoDeBatalla = new Elemento[10][10];
    }

    public void addElemento(Elemento e) {
        Posicion p = e.getPosicion();
        campoDeBatalla[p.getRenglon()][p.getColumna()] = e;
    }

    public void destruirElementos(Posicion p, int radio) {
        ArrayList<Elemento> elementos = new ArrayList<>();

        // agregar elementos dentro del radio de alcance al ArrayList
        for (int i = Math.max(0, p.getRenglon() - radio); i <= Math.min(campoDeBatalla.length - 1, p.getRenglon() + radio); i++) {
            for (int j = Math.max(0, p.getColumna() - radio); j <= Math.min(campoDeBatalla[i].length - 1, p.getColumna() + radio); j++) {
                if (campoDeBatalla[i][j] != null) {
                    elementos.add(campoDeBatalla[i][j]);
                }
            }
        }

        for (Elemento elem : elementos) {
            if (elem instanceof Destruible) {//separa los destruibles
                Destruible d = (Destruible) elem;
                System.out.println(d.destruir());
                campoDeBatalla[elem.getPosicion().getRenglon()][elem.getPosicion().getColumna()] = null; 
            }  //lo quita del tablero
        }
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