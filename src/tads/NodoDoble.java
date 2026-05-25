package tads;

/**
 * Nodo generico para lista doblemente encadenada.
 * A diferencia de NodoSimple, tiene DOS punteros:
 *   - siguiente: apunta al nodo de adelante
 *   - anterior:  apunta al nodo de atras
 *
 * Esto permite recorrer la lista en ambas direcciones.
 */
public class NodoDoble<T> {

    private T dato;
    private NodoDoble<T> siguiente;
    private NodoDoble<T> anterior;

    public NodoDoble(T dato) {
        this.dato = dato;
        this.siguiente = null;  // todo nodo nuevo no apunta a nadie
        this.anterior = null;
    }

    public T getDato() {
        return dato;
    }

    public NodoDoble<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble<T> siguiente) {
        this.siguiente = siguiente;
    }

    public NodoDoble<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }
}
