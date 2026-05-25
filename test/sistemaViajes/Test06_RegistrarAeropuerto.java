package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test06_RegistrarAeropuerto {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void registrarAeropuertoOk() {
        retorno = s.registrarAeropuerto("MVD", "Montevideo");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.registrarAeropuerto("GRU", "Sao Paulo");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.registrarAeropuerto("EZE", "Buenos Aires");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void registrarAeropuertoError01() {
        retorno = s.registrarAeropuerto("", "Montevideo");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("MVD", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto(null, "Montevideo");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("MVD", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("  ", "Montevideo");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarAeropuerto("MVD", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void registrarAeropuertoError02() {
        s.registrarAeropuerto("MVD", "Montevideo");
        retorno = s.registrarAeropuerto("MVD", "Otro nombre");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
}
