package sistemaViajes;

/**
 * Lista doblemente encadenada generica.
 *
 * Ventaja sobre ListaSimple: al guardar un puntero "fin" y cada nodo
 * tener puntero "anterior", podemos recorrer la lista al reves en O(n).
 * Eso es lo que nos permite listarDesc() sin costo extra.
 *
 * En el obligatorio se usa SOLO para la lista principal de pasajeros,
 * porque necesitamos listar ascendente (op 04) Y descendente (op 05).
 */
public class ListaDoble<T extends Comparable<T>> {

    private NodoDoble<T> inicio;
    private NodoDoble<T> fin;      // puntero extra al ultimo nodo
    private int cantidad;

    public ListaDoble() {
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

    // ----------------------------------------------------------------
    // Insertar ordenado
    // ----------------------------------------------------------------

    /**
     * Inserta manteniendo orden ascendente segun compareTo(). O(n).
     *
     * Es mas complejo que en ListaSimple porque hay 4 punteros a actualizar:
     *   nuevo.siguiente, nuevo.anterior,
     *   el nodo anterior al nuevo (.siguiente),
     *   el nodo posterior al nuevo (.anterior)
     *
     * Hay 3 casos: insertar al inicio, al final, o en el medio.
     */
    public void insertarOrdenado(T elemento) {
        NodoDoble<T> nuevo = new NodoDoble<>(elemento);

        // CASO 1: lista vacia
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
            // nuevo.anterior y nuevo.siguiente ya son null por el constructor
            cantidad++;
            return;
        }

        // CASO 2: el elemento va ANTES del inicio (es el mas chico)
        if (elemento.compareTo(inicio.getDato()) <= 0) {
            nuevo.setSiguiente(inicio);   // nuevo apunta al que era primero
            inicio.setAnterior(nuevo);    // el que era primero apunta atras a nuevo
            inicio = nuevo;              // nuevo pasa a ser el primero
            // nuevo.anterior ya es null — correcto, es el primero
            cantidad++;
            return;
        }

        // CASO 3: buscar posicion en el medio o al final
        // igual que en ListaSimple: avanzamos mientras el SIGUIENTE sea menor
        NodoDoble<T> aux = inicio;
        while (aux.getSiguiente() != null
                && aux.getSiguiente().getDato().compareTo(elemento) < 0) {
            aux = aux.getSiguiente();
        }

        // aux quedo justo ANTES del lugar donde insertar
        // Hay dos sub-casos: insertar en el medio vs insertar al final

        nuevo.setAnterior(aux);              // nuevo mira atras hacia aux
        nuevo.setSiguiente(aux.getSiguiente()); // nuevo mira adelante hacia quien seguia a aux

        if (aux.getSiguiente() != null) {
            // hay un nodo despues: ese nodo tiene que mirar atras hacia nuevo
            aux.getSiguiente().setAnterior(nuevo);
        } else {
            // aux era el ultimo — ahora nuevo es el nuevo fin
            fin = nuevo;
        }

        aux.setSiguiente(nuevo);  // aux ahora apunta adelante hacia nuevo
        cantidad++;
    }

    // ----------------------------------------------------------------
    // Buscar
    // ----------------------------------------------------------------

    /**
     * Busca el elemento donde compareTo() == 0. O(n).
     * Mismo patron que ListaSimple pero con NodoDoble.
     */
    public T buscar(T elemento) {
        NodoDoble<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().compareTo(elemento) == 0) {
                return aux.getDato();
            }
            aux = aux.getSiguiente();
        }
        return null;
    }

    // ----------------------------------------------------------------
    // Listar
    // ----------------------------------------------------------------

    /**
     * Recorre de inicio a fin (ascendente). O(n).
     * Mismo patron que ListaSimple.listar().
     */
    public String listarAsc() {
        if (inicio == null) {
            return "";
        }

        String resultado = "";
        NodoDoble<T> aux = inicio;          // arranca desde el primero

        while (aux != null) {
            resultado += aux.getDato().toString();
            if (aux.getSiguiente() != null) {
                resultado += "|";
            }
            aux = aux.getSiguiente();       // avanza hacia adelante
        }

        return resultado;
    }

    /**
     * Recorre de fin a inicio (descendente). O(n).
     * Esta es la razon de existir de la lista doble:
     * recorrer al reves sin costo extra, usando el puntero "anterior".
     */
    public String listarDesc() {
        if (fin == null) {
            return "";
        }

        String resultado = "";
        NodoDoble<T> aux = fin;             // arranca desde el ULTIMO

        while (aux != null) {
            resultado += aux.getDato().toString();
            if (aux.getAnterior() != null) {
                resultado += "|";
            }
            aux = aux.getAnterior();        // retrocede hacia atras
        }

        return resultado;
    }
}
