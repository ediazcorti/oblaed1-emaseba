package sistemaViajes;

/**
 * Representa una reserva de un pasajero en un vuelo.
 * Implementa Comparable<Reserva> para insertarse ordenado por cedula
 * del pasajero en la lista de reservas del vuelo.
 */
public class Reserva implements Comparable<Reserva> {

    private Pasajero pasajero;
    private boolean confirmada;  // false = solo reservo, true = hizo check-in

    public Reserva(Pasajero pasajero) {
        this.pasajero = pasajero;
        this.confirmada = false;  // toda reserva nueva empieza sin confirmar
    }

    // ----------------------------------------------------------------
    // Getters
    // ----------------------------------------------------------------

    public Pasajero getPasajero() {
        return pasajero;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    // ----------------------------------------------------------------
    // Confirmar (check-in)
    // ----------------------------------------------------------------

    /**
     * Marca la reserva como confirmada cuando el pasajero hace check-in.
     */
    public void confirmar() {
        this.confirmada = true;
    }

    // ----------------------------------------------------------------
    // Comparable
    // ----------------------------------------------------------------

    /**
     * Compara por cedula del pasajero (numerica, igual que en Pasajero).
     * Delega directamente en Pasajero.compareTo() para no repetir logica.
     *
     * Para buscar una reserva por cedula se usa un reservaAux:
     *   Pasajero pasajeroAux = new Pasajero(cedula, null, 0, null);
     *   Reserva reservaAux   = new Reserva(pasajeroAux);
     *   vuelo.getReservas().buscar(reservaAux);
     */
    @Override
    public int compareTo(Reserva otra) {
        return this.pasajero.compareTo(otra.pasajero);
    }

    // ----------------------------------------------------------------
    // toString
    // ----------------------------------------------------------------

    /**
     * Usa el toString() del pasajero directamente.
     * Formato: "cedula;nombre;edad;categoria"
     *
     * Al cerrar el vuelo (op 12) se listan los pasajeros confirmados
     * usando este toString, que ya tiene el formato correcto.
     */
    @Override
    public String toString() {
        return pasajero.toString();
    }
}
