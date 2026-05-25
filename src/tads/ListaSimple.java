package tads;

import tads.NodoSimple;

/**
 * Lista simplemente encadenada generica.
 *
 * T extends Comparable<T> significa que el tipo T debe implementar compareTo().
 * Eso nos permite insertar ordenado y buscar sin saber el tipo concreto.
 *
 * Equivalente TypeScript aproximado:
 *   class ListaSimple<T extends { compareTo(otro: T): number }> { ... }
 */
public class ListaSimple<T extends Comparable<T>> {

    private NodoSimple<T> inicio;
    private int cantidad;

    public ListaSimple() {
        this.inicio = null;
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

    // ----------------------------------------------------------------
    // Agregar elementos
    // ----------------------------------------------------------------

    /**
     * Agrega al inicio. O(1).
     * Usar cuando el orden no importa (aeropuertos, vuelos).
     */
    public void agregarInicio(T elemento) {
        NodoSimple<T> nuevo = new NodoSimple<>(elemento);
        nuevo.setSiguiente(inicio);  // el nuevo apunta a quien era el primero
        inicio = nuevo;              // el nuevo pasa a ser el primero
        cantidad++;
    }

    /**
     * Agrega al final. O(n).
     * Recorre toda la lista para llegar al ultimo nodo.
     */
    public void agregarFinal(T elemento) {
        NodoSimple<T> nuevo = new NodoSimple<>(elemento);

        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoSimple<T> aux = inicio;
            while (aux.getSiguiente() != null) {  // avanza hasta el ultimo
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);  // el ultimo apunta al nuevo
        }
        cantidad++;
    }

    /**
     * Inserta manteniendo orden ascendente segun compareTo(). O(n).
     *
     * Logica: recorre hasta encontrar el primer nodo cuyo SIGUIENTE
     * es mayor que el elemento nuevo. Inserta entre aux y aux.getSiguiente().
     *
     * Ejemplo con cedulas (numerico): lista [100, 300, 500]
     *   insertarOrdenado(200):
     *     aux=100, siguiente=300, 300.compareTo(200) > 0 → parar
     *     inserta 200 entre 100 y 300 → [100, 200, 300, 500] ✓
     */
    public void insertarOrdenado(T elemento) {
        NodoSimple<T> nuevo = new NodoSimple<>(elemento);

        // Caso 1: lista vacia O el elemento va antes del primero
        if (inicio == null || elemento.compareTo(inicio.getDato()) <= 0) {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            cantidad++;
            return;
        }

        // Caso 2: buscar la posicion correcta
        NodoSimple<T> aux = inicio;
        // avanzamos mientras el PROXIMO elemento sea menor que el nuevo
        while (aux.getSiguiente() != null
                && aux.getSiguiente().getDato().compareTo(elemento) < 0) {
            aux = aux.getSiguiente();
        }
        // aux quedo justo antes del lugar donde va el nuevo
        nuevo.setSiguiente(aux.getSiguiente());
        aux.setSiguiente(nuevo);
        cantidad++;
    }

    // ----------------------------------------------------------------
    // Buscar y obtener
    // ----------------------------------------------------------------

    /**
     * Busca el elemento donde compareTo() == 0. O(n).
     * Retorna el elemento encontrado, o null si no existe.
     *
     * Para buscar por cedula: pasar un objeto "dummy" con esa cedula.
     * Ej: pasajeros.buscar(new Pasajero(cedula, null, 0, null))
     */
    public T buscar(T elemento) {
        NodoSimple<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().compareTo(elemento) == 0) {
                return aux.getDato();  // encontrado
            }
            aux = aux.getSiguiente();
        }
        return null;  // no existe
    }

    /**
     * Retorna el elemento en la posicion dada (base 1). O(n).
     * PRE: pos >= 1 && pos <= cantidad
     *
     * Mismo patron que mostro el profe en clase:
     *   NodoSimple<T> aux = inicio; (OK porque esta DENTRO del TAD)
     */
    public T obtenerElemento(int pos) {
        NodoSimple<T> aux = inicio;
        int cont = 1;
        while (aux != null && cont < pos) {
            aux = aux.getSiguiente();
            cont++;
        }
        return (aux != null) ? aux.getDato() : null;
    }

    // ----------------------------------------------------------------
    // Listar
    // ----------------------------------------------------------------

    /**
     * Recorre la lista y concatena todos los elementos separados por '|'.
     * Cada elemento usa su propio toString() para formatearse.
     * O(n).
     *
     * Retorna "" si la lista esta vacia.
     */
    public String listar() {
        if (inicio == null) {
            return "";
        }

        String resultado = "";
        NodoSimple<T> aux = inicio;

        while (aux != null) {
            resultado += aux.getDato().toString();
            if (aux.getSiguiente() != null) {
                resultado += "|";  // separador solo entre elementos, no al final
            }
            aux = aux.getSiguiente();
        }

        return resultado;
    }

    // ----------------------------------------------------------------
    // Eliminar
    // ----------------------------------------------------------------

    /**
     * Elimina el primer elemento donde compareTo() == 0. O(n).
     * Retorna true si lo encontro y elimino, false si no existia.
     */
    public boolean eliminar(T elemento) {
        if (inicio == null) {
            return false;
        }

        // Caso especial: el elemento a eliminar es el primero
        if (inicio.getDato().compareTo(elemento) == 0) {
            inicio = inicio.getSiguiente();  // inicio salta al siguiente
            cantidad--;
            return true;
        }

        // Caso general: buscar el nodo ANTERIOR al que hay que eliminar
        NodoSimple<T> aux = inicio;
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getDato().compareTo(elemento) == 0) {
                // "saltear" el nodo a eliminar: aux apunta al siguiente del siguiente
                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                cantidad--;
                return true;
            }
            aux = aux.getSiguiente();
        }

        return false;  // no se encontro
    }
}
