package sistemaViajes;

/**
 * Representa un aeropuerto del sistema.
 * Contiene una Cola de vuelos cerrados que esperan embarcar y despegar.
 *
 * Implementa Comparable<Aeropuerto> para poder usar buscar() en
 * ListaSimple<Aeropuerto>. La comparacion es por codigo.
 * Como los aeropuertos se agregan con agregarInicio() (sin orden),
 * compareTo() solo se usa para encontrar coincidencias en buscar().
 */
public class Aeropuerto implements Comparable<Aeropuerto> {

    private String codigo;
    private String nombre;
    private Cola<Vuelo> colaVuelos;  // vuelos cerrados esperando despegar (FIFO)

    public Aeropuerto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.colaVuelos = new Cola<>();  // cola vacia al crear el aeropuerto
    }

    // ----------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    // ----------------------------------------------------------------
    // Operaciones sobre la cola de vuelos
    // ----------------------------------------------------------------

    /**
     * Agrega un vuelo cerrado a la cola de espera. O(1).
     * Se llama desde cerrarVuelo() (op 12).
     */
    public void encolarVuelo(Vuelo vuelo) {
        colaVuelos.encolar(vuelo);
    }

    /**
     * Saca y retorna el proximo vuelo a despegar. O(1).
     * Se llama desde embarqueYDespegueDeVuelo() (op 15).
     * Retorna null si la cola esta vacia.
     */
    public Vuelo desencolarVuelo() {
        return colaVuelos.desencolar();
    }

    /**
     * Retorna el proximo vuelo a despegar SIN sacarlo. O(1).
     */
    public Vuelo frenteVuelos() {
        return colaVuelos.frente();
    }

    /**
     * Indica si no hay vuelos esperando despegar.
     */
    public boolean colaEstaVacia() {
        return colaVuelos.estaVacia();
    }

    /**
     * Cantidad de vuelos actualmente en la cola de espera.
     * Se retorna en valorEntero en op 15.
     */
    public int getCantidadVuelosEnCola() {
        return colaVuelos.getCantidad();
    }

    // ----------------------------------------------------------------
    // Comparable
    // ----------------------------------------------------------------

    /**
     * Compara por codigo de aeropuerto (comparacion de texto simple).
     * Solo se usa para buscar() en ListaSimple — alcanza con que
     * retorne 0 cuando los codigos son iguales.
     *
     * Para buscar: aeropuertoAux = new Aeropuerto(codigo, null)
     * El null en nombre no causa problemas porque compareTo
     * solo usa this.codigo y otro.codigo.
     */
    @Override
    public int compareTo(Aeropuerto otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    // ----------------------------------------------------------------
    // toString
    // ----------------------------------------------------------------

    /**
     * Formato que usa op 08: "codigo;nombre"
     * La cantidad de vuelos en cola se retorna aparte en valorEntero.
     */
    @Override
    public String toString() {
        return codigo + ";" + nombre;
    }
}
