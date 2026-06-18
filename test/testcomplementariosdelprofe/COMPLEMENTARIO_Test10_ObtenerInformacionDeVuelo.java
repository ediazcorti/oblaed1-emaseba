package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test10_ObtenerInformacionDeVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
    }

    @Test
    public void obtenerInformacionVueloProgramadoSinReservas() {
        s.registrarVuelo("MVD", "EZE", "AR123", 120, 230);

        retorno = s.obtenerInformacionDeVuelo("AR123");

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MVD:EZE;AR123;120;230;Programado;0;0", retorno.getValorString());
    }

    @Test
    public void obtenerInformacionVueloErrores() {
        assertEquals(Retorno.Resultado.ERROR_1, s.obtenerInformacionDeVuelo(null).getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.obtenerInformacionDeVuelo("").getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.obtenerInformacionDeVuelo("NO_EXISTE").getResultado());
    }
}
