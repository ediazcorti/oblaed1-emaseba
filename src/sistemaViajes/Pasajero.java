package sistemaViajes;

/**
 * Representa un pasajero del sistema.
 * Implementa Comparable<Pasajero> para poder insertarse ordenado
 * en ListaSimple y ListaDoble por cedula (comparacion numerica).
 */
public class Pasajero implements Comparable<Pasajero> {

    private String cedula;
    private String nombre;
    private int edad;
    private Categoria categoria;

    public Pasajero(String cedula, String nombre, int edad, Categoria categoria) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.categoria = categoria;
    }

    // ----------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    // ----------------------------------------------------------------
    // Comparable
    // ----------------------------------------------------------------

    /**
     * Compara dos pasajeros por cedula en forma NUMERICA.
     *
     * Por que numerica y no alfabetica?
     * Porque "845.345-4" < "4.985.345-4" numericamente (845345 < 4985345)
     * pero alfabeticamente "8..." > "4..." (el caracter '8' > '4').
     *
     * Solucion: sacamos todos los caracteres no numericos con replaceAll
     * y comparamos los numeros resultantes como Long.
     *
     * Ejemplo:
     *   "1.234.567-8" → replaceAll → "12345678" → Long: 12345678
     *   "845.345-4"   → replaceAll → "8453454"  → Long: 8453454
     *
     * Este metodo lo usa insertarOrdenado() y buscar() de los TADs.
     * Para buscar, se crea un pasajeroAux con solo la cedula:
     *   Pasajero pasajeroAux = new Pasajero(cedula, null, 0, null);
     *   compareTo() solo usa this.cedula y otro.cedula → no hay NPE.
     */
    @Override
    public int compareTo(Pasajero otro) {
        long num1 = Long.parseLong(this.cedula.replaceAll("[^0-9]", ""));
        long num2 = Long.parseLong(otro.cedula.replaceAll("[^0-9]", ""));
        return Long.compare(num1, num2);
    }

    // ----------------------------------------------------------------
    // toString
    // ----------------------------------------------------------------

    /**
     * Formato que exige la consigna: cedula;nombre;edad;categoria
     *
     * Ejemplo: "1.345.345-4;Pedro;25;Esporádico"
     *
     * categoria.getTexto() devuelve el nombre legible del enum:
     *   PLATINO    → "Platino"
     *   FRECUENTE  → "Frecuente"
     *   ESTANDAR   → "Estándar"
     *   ESPORADICO → "Esporádico"
     *
     * Este toString() lo llama listar(), listarAsc() y listarDesc()
     * de los TADs cuando construyen el String de retorno.
     */
    @Override
    public String toString() {
        return cedula + ";" + nombre + ";" + edad + ";" + categoria.getTexto();
    }
}
