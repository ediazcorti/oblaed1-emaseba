package sistemaViajes;

import dominio.Categoria;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test14_EmbarqueYDespegueDeVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Montevideo");
        s.registrarAeropuerto("GRU", "Sao Paulo");
    }

    private void cerrarVuelo(String codigo) {
        s.registrarVuelo("MVD", "GRU", codigo, 10, 500);
        s.abrirVuelo(codigo);
        s.cerrarVuelo(codigo); // queda encolado en MVD
    }

    @Test
    public void embarqueOkUnVuelo() {
        cerrarVuelo("V001");
        retorno = s.embarqueYDespegueDeVuelo("MVD");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("V001", retorno.getValorString());  // vuelo que despegó
        assertEquals(0, retorno.getValorEntero());       // cola vacía
    }

    @Test
    public void embarqueOkRespetaOrdenFIFO() {
        cerrarVuelo("V001");
        cerrarVuelo("V002");
        cerrarVuelo("V003");

        retorno = s.embarqueYDespegueDeVuelo("MVD");
        assertEquals("V001", retorno.getValorString()); // primero en cerrar, primero en despegar
        assertEquals(2, retorno.getValorEntero());

        retorno = s.embarqueYDespegueDeVuelo("MVD");
        assertEquals("V002", retorno.getValorString());
        assertEquals(1, retorno.getValorEntero());

        retorno = s.embarqueYDespegueDeVuelo("MVD");
        assertEquals("V003", retorno.getValorString());
        assertEquals(0, retorno.getValorEntero());
    }

    @Test
    public void embarqueVueloQuedaFinalizado() {
        cerrarVuelo("V001");
        s.embarqueYDespegueDeVuelo("MVD");
        retorno = s.obtenerInformacionDeVuelo("V001");
        assertTrue(retorno.getValorString().contains("Finalizado"));
    }

    @Test
    public void embarqueError01() {
        retorno = s.embarqueYDespegueDeVuelo("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.embarqueYDespegueDeVuelo(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.embarqueYDespegueDeVuelo("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void embarqueError02AeropuertoNoExiste() {
        retorno = s.embarqueYDespegueDeVuelo("AAA");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void embarqueError03ColaVacia() {
        // Aeropuerto existe pero no tiene vuelos en cola
        retorno = s.embarqueYDespegueDeVuelo("MVD");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void embarqueError03DespuesDeVaciarCola() {
        cerrarVuelo("V001");
        s.embarqueYDespegueDeVuelo("MVD"); // cola queda vacía
        retorno = s.embarqueYDespegueDeVuelo("MVD"); // ahora sí ERROR_3
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
