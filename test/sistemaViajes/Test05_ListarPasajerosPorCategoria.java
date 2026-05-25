package sistemaViajes;

import dominio.Categoria;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test05_ListarPasajerosPorCategoria {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void listarCategoriaVacia() {
        retorno = s.listarPasajerosPorCategoría(Categoria.PLATINO);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarCategoriaUnPasajero() {
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.PLATINO);
        retorno = s.listarPasajerosPorCategoría(Categoria.PLATINO);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3.335.321-2;Juan;45;Platino", retorno.getValorString());
    }

    @Test
    public void listarCategoriaSoloMuestraLaCorrecta() {
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.PLATINO);
        s.registrarPasajero("6.430.147-9", "Maria", 30, Categoria.FRECUENTE);
        s.registrarPasajero("935.457-7", "Carlos", 22, Categoria.ESTANDAR);

        retorno = s.listarPasajerosPorCategoría(Categoria.PLATINO);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("3.335.321-2;Juan;45;Platino", retorno.getValorString());

        retorno = s.listarPasajerosPorCategoría(Categoria.FRECUENTE);
        assertEquals("6.430.147-9;Maria;30;Frecuente", retorno.getValorString());

        retorno = s.listarPasajerosPorCategoría(Categoria.ESTANDAR);
        assertEquals("935.457-7;Carlos;22;Estándar", retorno.getValorString());

        retorno = s.listarPasajerosPorCategoría(Categoria.ESPORADICO);
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarCategoriaVariosOrdenadosPorCedula() {
        // Insertados en orden inverso para verificar que la lista mantiene orden
        s.registrarPasajero("6.430.147-9", "Nicolas", 0, Categoria.FRECUENTE);
        s.registrarPasajero("935.457-7", "Maria", 82, Categoria.FRECUENTE);
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.FRECUENTE);

        retorno = s.listarPasajerosPorCategoría(Categoria.FRECUENTE);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Ordenados ascendentemente por cédula numérica
        assertEquals("935.457-7;Maria;82;Frecuente|3.335.321-2;Juan;45;Frecuente|6.430.147-9;Nicolas;0;Frecuente",
                retorno.getValorString());
    }

    @Test
    public void listarTodasLasCategorias() {
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.PLATINO);
        s.registrarPasajero("6.430.147-9", "Maria", 30, Categoria.FRECUENTE);
        s.registrarPasajero("935.457-7", "Carlos", 22, Categoria.ESTANDAR);
        s.registrarPasajero("1.234.567-8", "Ana", 28, Categoria.ESPORADICO);

        assertEquals(Retorno.Resultado.OK, s.listarPasajerosPorCategoría(Categoria.PLATINO).getResultado());
        assertEquals(Retorno.Resultado.OK, s.listarPasajerosPorCategoría(Categoria.FRECUENTE).getResultado());
        assertEquals(Retorno.Resultado.OK, s.listarPasajerosPorCategoría(Categoria.ESTANDAR).getResultado());
        assertEquals(Retorno.Resultado.OK, s.listarPasajerosPorCategoría(Categoria.ESPORADICO).getResultado());
    }
}
