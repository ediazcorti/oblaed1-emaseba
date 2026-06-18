package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test07_RegistrarAeropuerto {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void registrarAeropuertoOk() {
        retorno = s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void registrarAeropuertoError01ParametrosInvalidos() {
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarAeropuerto(null, "Carrasco").getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarAeropuerto("MDV", null).getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarAeropuerto("", "Carrasco").getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarAeropuerto("MDV", "").getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarAeropuerto("   ", "Carrasco").getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarAeropuerto("MVD", "   ").getResultado());
    }

    @Test
    public void registrarAeropuertoError02CodigoRepetido() {
        s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        retorno = s.registrarAeropuerto("MVD", "Otro nombre");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
}
