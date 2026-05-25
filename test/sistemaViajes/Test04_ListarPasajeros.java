package sistemaViajes;

import dominio.Categoria;
import sistemaViajes.Retorno;
import sistemaViajes.ImplementacionSistema;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sistemaViajes.Sistema;

public class Test04_ListarPasajeros {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void listarPasajerosVacio() {
        retorno = s.listarPasajerosAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarPasajerosAscendenteSoloUnUsuario() {
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        retorno = s.listarPasajerosAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3.335.321-2;Juan;45;Esporádico", retorno.getValorString());
    }

    @Test
    public void listarPasajerosAscendenteIngresoOrdenado() {
        s.registrarPasajero("935.457-7", "Maria", 82, Categoria.PLATINO);
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        s.registrarPasajero("6.430.147-9", "Nicolas", 0, Categoria.ESTANDAR);
        retorno = s.listarPasajerosAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("935.457-7;Maria;82;Platino|3.335.321-2;Juan;45;Esporádico|6.430.147-9;Nicolas;0;Estándar", retorno.getValorString());
    }

    @Test
    public void listarPasajerosAscendenteIngresoDesordenado() {
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        s.registrarPasajero("6.430.147-9", "Nicolas", 0, Categoria.ESTANDAR);
        s.registrarPasajero("935.457-7", "Maria", 82, Categoria.PLATINO);
        retorno = s.listarPasajerosAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("935.457-7;Maria;82;Platino|3.335.321-2;Juan;45;Esporádico|6.430.147-9;Nicolas;0;Estándar", retorno.getValorString());
    }

    // ============================================================
// AGREGAMOS ESTO PORQUE LE FALTABA AL TEST04 BASE QUE NOS DIERON
// El test original solo cubría listarPasajerosAscendente (Op 04).
// Le faltaban todos los casos de listarPasajerosDescendente (Op 05).
// ============================================================
// Agregar dentro de la clase Test04_ListarPasajeros,
// como @Test nuevos debajo de los existentes:
    
    
    @Test
    public void listarDescendenteVacio() {
        retorno = s.listarPasajerosDescendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarDescendenteSoloUnUsuario() {
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        retorno = s.listarPasajerosDescendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3.335.321-2;Juan;45;Esporádico", retorno.getValorString());
    }

    @Test
    public void listarDescendenteIngresoOrdenado() {
        s.registrarPasajero("935.457-7", "Maria", 82, Categoria.PLATINO);
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        s.registrarPasajero("6.430.147-9", "Nicolas", 0, Categoria.ESTANDAR);
        retorno = s.listarPasajerosDescendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Mayor cédula numérica primero: 6430147 > 3335321 > 935457
        assertEquals("6.430.147-9;Nicolas;0;Estándar|3.335.321-2;Juan;45;Esporádico|935.457-7;Maria;82;Platino",
                retorno.getValorString());
    }

    @Test
    public void listarDescendenteIngresoDesordenado() {
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        s.registrarPasajero("6.430.147-9", "Nicolas", 0, Categoria.ESTANDAR);
        s.registrarPasajero("935.457-7", "Maria", 82, Categoria.PLATINO);
        retorno = s.listarPasajerosDescendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Sin importar el orden de inserción, el resultado siempre es descendente
        assertEquals("6.430.147-9;Nicolas;0;Estándar|3.335.321-2;Juan;45;Esporádico|935.457-7;Maria;82;Platino",
                retorno.getValorString());
    }

}
