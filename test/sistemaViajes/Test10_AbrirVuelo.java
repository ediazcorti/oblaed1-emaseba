package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test10_AbrirVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Montevideo");
        s.registrarAeropuerto("GRU", "Sao Paulo");
        s.registrarVuelo("MVD", "GRU", "V001", 100, 500);
    }

    @Test
    public void abrirVueloOk() {
        retorno = s.abrirVuelo("V001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void abrirVueloVerificaEstadoCambia() {
        s.abrirVuelo("V001");
        retorno = s.obtenerInformacionDeVuelo("V001");
        assertTrue(retorno.getValorString().contains("Abierto"));
    }

    @Test
    public void abrirVueloError01() {
        retorno = s.abrirVuelo("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.abrirVuelo(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.abrirVuelo("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void abrirVueloError02() {
        retorno = s.abrirVuelo("V999");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void abrirVueloError03VueloYaAbierto() {
        s.abrirVuelo("V001");
        retorno = s.abrirVuelo("V001"); // ya está ABIERTO, no PROGRAMADO
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void abrirVueloError03VueloCerrado() {
        s.abrirVuelo("V001");
        s.cerrarVuelo("V001");
        retorno = s.abrirVuelo("V001"); // ya está CERRADO
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
