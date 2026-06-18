package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test11_AbrirVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "AR123", 120, 230);
    }

    @Test
    public void abrirVueloOkCambiaEstado() {
        retorno = s.abrirVuelo("AR123");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.obtenerInformacionDeVuelo("AR123");
        assertEquals("MVD:EZE;AR123;120;230;Abierto;0;0", retorno.getValorString());
    }

    @Test
    public void abrirVueloErrores() {
        assertEquals(Retorno.Resultado.ERROR_1, s.abrirVuelo(null).getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.abrirVuelo("").getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.abrirVuelo("NO_EXISTE").getResultado());

        s.abrirVuelo("AR123");
        assertEquals(Retorno.Resultado.ERROR_3, s.abrirVuelo("AR123").getResultado());
    }
}
