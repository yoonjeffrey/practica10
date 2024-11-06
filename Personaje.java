abstract class Personaje extends Elemento {
    protected String nombre;
    protected int puntosDeVida;

    public Personaje(String nombre, Escenario escenario, Posicion posicion) {
        super(escenario, posicion);
        this.nombre = nombre;
        this.puntosDeVida = 100; // Valor por defecto
    }

    public String getNombre() {
        return nombre;
    }
}

