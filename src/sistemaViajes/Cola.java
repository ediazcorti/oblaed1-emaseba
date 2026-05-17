package sistemaViajes;

/**
 * Cola generica FIFO (First In, First Out).
 * El primero que entra es el primero que sale.
 *
 * Usa NodoSimple<T> internamente (igual que ListaSimple).
 * NO necesita T extends Comparable porque no hay ordenamiento.
 *
 * En el obligatorio se usa dentro de Aeropuerto para los vuelos
 * cerrados que esperan despegar: el primero en cerrar es el primero
 * en embarcar (op 12 encola, op 15 desencola).
 *
 *   inicio → [vuelo1] → [vuelo2] → [vuelo3] ← fin
 *   desencolar() saca vuelo1   (el mas antiguo)
 *   encolar()    agrega al fin (el mas nuevo)
 */
public class Cola<T> {

    private NodoSimple<T> inicio;  // frente de la cola (el que sale primero)
    private NodoSimple<T> fin;     // fondo de la cola  (el que entro ultimo)
    private int cantidad;

    public Cola() {
        this.inicio = null;
        this.fin = null;
        this.cantidad = 0;
    }

    // ----------------------------------------------------------------
    // Consultas basicas
    // ----------------------------------------------------------------

    public boolean estaVacia() {
        return inicio == null;
    }

    public int getCantidad() {
        return cantidad;
    }

    /**
     * Retorna el elemento del frente SIN sacarlo. O(1).
     * Retorna null si la cola esta vacia.
     */
    public T frente() {
        if (inicio == null) {
            return null;
        }
        return inicio.getDato();
    }

    // ----------------------------------------------------------------
    // Encolar y desencolar
    // ----------------------------------------------------------------

    /**
     * Agrega un elemento al FONDO de la cola. O(1).
     * Es O(1) porque guardamos el puntero "fin" y no tenemos que recorrer.
     */
    public void encolar(T elemento) {
        NodoSimple<T> nuevo = new NodoSimple<>(elemento);

        if (fin == null) {
            // cola vacia: inicio y fin apuntan al unico nodo
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);  // el actual ultimo apunta al nuevo
            fin = nuevo;              // el nuevo pasa a ser el ultimo
        }
        cantidad++;
    }

    /**
     * Saca y retorna el elemento del FRENTE de la cola. O(1).
     * Retorna null si la cola esta vacia.
     *
     * Caso especial: si la cola queda vacia despues de sacar,
     * hay que poner fin = null tambien (sino fin queda apuntando
     * a un nodo que ya no esta en la cola).
     */
    public T desencolar() {
        if (inicio == null) {
            return null;
        }

        T dato = inicio.getDato();      // guardamos el dato a retornar
        inicio = inicio.getSiguiente(); // inicio avanza al siguiente

        if (inicio == null) {
            // la cola quedo vacia — fin tambien tiene que ser null
            fin = null;
        }

        cantidad--;
        return dato;
    }
}
