package sistemaViajes;

/**
 * Representa un vuelo del sistema.
 * Contiene su estado, una lista ordenada de reservas, y un contador
 * de confirmados para no tener que recorrer la lista cada vez.
 *
 * Implementa Comparable<Vuelo> por codigoVuelo para poder usar
 * buscar() en ListaSimple<Vuelo>.
 */
public class Vuelo implements Comparable<Vuelo> {

    private String codigoVuelo;
    private Aeropuerto origen;
    private Aeropuerto destino;
    private int capacidad;
    private int costoEnDolares;
    private Estado estado;
    private ListaSimple<Reserva> reservas;
    private int cantidadConfirmados;  // contador O(1), se incrementa en check-in

    public Vuelo(String codigoVuelo, Aeropuerto origen, Aeropuerto destino,
                 int capacidad, int costoEnDolares) {
        this.codigoVuelo = codigoVuelo;
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.costoEnDolares = costoEnDolares;
        this.estado = Estado.PROGRAMADO;        // todo vuelo arranca programado
        this.reservas = new ListaSimple<>();
        this.cantidadConfirmados = 0;
    }

    // ----------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getCostoEnDolares() {
        return costoEnDolares;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCantidadReservas() {
        return reservas.getCantidad();
    }

    public int getCantidadConfirmados() {
        return cantidadConfirmados;
    }

    /**
     * Pasajeros que reservaron pero NO hicieron check-in.
     * Se retorna en valorEntero al cerrar el vuelo (op 12).
     */
    public int getCantidadSinConfirmar() {
        return reservas.getCantidad() - cantidadConfirmados;
    }

    // ----------------------------------------------------------------
    // Overbooking
    // ----------------------------------------------------------------

    /**
     * Limite maximo de reservas permitidas, incluyendo el 10% de overbooking.
     * Redondeo hacia arriba con Math.ceil.
     *
     * Ejemplo: capacidad 10 → limite 11 (Math.ceil(10 * 1.1) = ceil(11.0) = 11)
     * Ejemplo: capacidad 15 → limite 17 (Math.ceil(15 * 1.1) = ceil(16.5) = 17)
     *
     * SOLO aplica para reservas (op 13). El check-in (op 14) usa
     * la capacidad exacta sin overbooking.
     */
    public int getLimiteReservas() {
        return (int) Math.ceil(capacidad * 1.1);
    }

    // ----------------------------------------------------------------
    // Operaciones sobre reservas
    // ----------------------------------------------------------------

    /**
     * Agrega una reserva en orden por cedula del pasajero. O(n).
     */
    public void agregarReserva(Reserva reserva) {
        reservas.insertarOrdenado(reserva);
    }

    /**
     * Busca una reserva por cedula usando un reservaAux. O(n).
     *
     * Uso tipico desde ImplementacionSistema:
     *   Pasajero pasajeroAux = new Pasajero(cedula, null, 0, null);
     *   Reserva reservaAux   = new Reserva(pasajeroAux);
     *   Reserva encontrada   = vuelo.buscarReserva(reservaAux);
     */
    public Reserva buscarReserva(Reserva reservaAux) {
        return reservas.buscar(reservaAux);
    }

    /**
     * Marca una reserva como confirmada y actualiza el contador. O(1).
     * Se llama desde realizarCheckIn() (op 14) una vez encontrada la reserva.
     */
    public void confirmarReserva(Reserva reserva) {
        reserva.confirmar();
        cantidadConfirmados++;
    }

    // ----------------------------------------------------------------
    // Listar confirmados (para op 12 - cerrar vuelo)
    // ----------------------------------------------------------------

    /**
     * Recorre la lista de reservas y retorna un String con los datos
     * de los pasajeros que hicieron check-in, separados por '|'.
     * Como las reservas estan ordenadas por cedula, la lista de
     * confirmados sale ordenada automaticamente.
     *
     * Usa obtenerElemento(i) del TAD — la traversal queda
     * dentro de este metodo (no se exponen nodos al exterior).
     */
    public String listarConfirmados() {
        String resultado = "";

        for (int i = 1; i <= reservas.getCantidad(); i++) {
            Reserva r = reservas.obtenerElemento(i);
            if (r.isConfirmada()) {
                if (!resultado.isEmpty()) {
                    resultado += "|";
                }
                resultado += r.toString();
            }
        }

        return resultado;
    }

    // ----------------------------------------------------------------
    // Comparable
    // ----------------------------------------------------------------

    /**
     * Compara por codigoVuelo (comparacion de texto).
     * Solo se usa para buscar() en ListaSimple<Vuelo>.
     *
     * Para buscar: vueloAux = new Vuelo(codigoDeVuelo, null, null, 0, 0)
     * Los null en origen/destino no causan problema porque compareTo
     * solo usa this.codigoVuelo y otro.codigoVuelo.
     */
    @Override
    public int compareTo(Vuelo otro) {
        return this.codigoVuelo.compareTo(otro.codigoVuelo);
    }

    // ----------------------------------------------------------------
    // toString
    // ----------------------------------------------------------------

    /**
     * Formato que usa op 10: 
     * "codigoOrigen;codigoDestino;codigoVuelo;capacidad;costo;estado;cantReservas;cantConfirmados"
     *
     * estado.getTexto() devuelve el nombre del estado:
     *   PROGRAMADO → "Programado"
     *   ABIERTO    → "Abierto"
     *   CERRADO    → "Cerrrado"  (typo en el enum base del profe, no modificar)
     *   FINALIZADO → "Finalizado"
     */
    @Override
    public String toString() {
        return origen.getCodigo() + ";" +
               destino.getCodigo() + ";" +
               codigoVuelo + ";" +
               capacidad + ";" +
               costoEnDolares + ";" +
               estado.getTexto() + ";" +
               reservas.getCantidad() + ";" +
               cantidadConfirmados;
    }
}
