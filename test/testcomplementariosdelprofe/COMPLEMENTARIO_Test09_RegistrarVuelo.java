package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test09_RegistrarVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
    }

    @Test
    public void registrarVueloOk() {
        retorno = s.registrarVuelo("MVD", "EZE", "AR123", 120, 230);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void registrarVueloErroresBasicos() {
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarVuelo("MVD", "EZE", "AR001", 0, 230).getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.registrarVuelo("MVD", "EZE", "AR002", 10, -1).getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.registrarVuelo("MVD", "", "AR003", 10, 230).getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.registrarVuelo("MVD", "EZE", null, 10, 230).getResultado());
    }

    @Test
    public void registrarVueloErroresAeropuertosYDuplicado() {
        assertEquals(Retorno.Resultado.ERROR_3, s.registrarVuelo("XXX", "EZE", "AR004", 10, 230).getResultado());
        assertEquals(Retorno.Resultado.ERROR_4, s.registrarVuelo("MVD", "XXX", "AR005", 10, 230).getResultado());

        s.registrarVuelo("MVD", "EZE", "AR123", 120, 230);
        retorno = s.registrarVuelo("MVD", "EZE", "AR123", 80, 100);
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());
    }
}
