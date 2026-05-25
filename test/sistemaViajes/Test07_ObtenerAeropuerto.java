package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test07_ObtenerAeropuerto {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void obtenerAeropuertoOk() {
        s.registrarAeropuerto("MVD", "Montevideo");
        retorno = s.obtenerAeropuerto("MVD");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MVD;Montevideo", retorno.getValorString());
        assertEquals(0, retorno.getValorEntero()); // sin vuelos en cola
    }

    @Test
    public void obtenerAeropuertoError01() {
        retorno = s.obtenerAeropuerto("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.obtenerAeropuerto(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.obtenerAeropuerto("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void obtenerAeropuertoError02() {
        retorno = s.obtenerAeropuerto("MVD");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void obtenerAeropuertoDistintosDatos() {
        s.registrarAeropuerto("GRU", "Sao Paulo");
        retorno = s.obtenerAeropuerto("GRU");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("GRU;Sao Paulo", retorno.getValorString());
    }
}
