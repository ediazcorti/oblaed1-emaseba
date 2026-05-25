package tads;

/**
 * Nodo generico para lista simplemente encadenada y Cola.
 * T es el tipo del dato que guarda el nodo (Pasajero, Vuelo, etc.)
 */
public class NodoSimple<T> {

    private T dato;
    private NodoSimple<T> siguiente;

    public NodoSimple(T dato) {
        this.dato = dato;
        this.siguiente = null;  // todo nodo nuevo apunta a null por defecto
    }

    public T getDato() {
        return dato;
    }

    public NodoSimple<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoSimple<T> siguiente) {
        this.siguiente = siguiente;
    }
}
