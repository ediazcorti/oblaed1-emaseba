package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;
import dominio.Categoria;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test12_CerrarVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "AR123", 3, 230);
        s.registrarPasajero("3.333.333-3", "Carlos", 30, Categoria.ESTANDAR);
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.FRECUENTE);
        s.registrarPasajero("2.222.222-2", "Bruno", 40, Categoria.PLATINO);
    }

    @Test
    public void cerrarVueloOkRetornaConfirmadosOrdenadosYNoConfirmados() {
        s.realizarReserva("AR123", "3.333.333-3");
        s.realizarReserva("AR123", "1.111.111-1");
        s.realizarReserva("AR123", "2.222.222-2");
        s.abrirVuelo("AR123");
        s.realizarCheckIn("AR123", "3.333.333-3");
        s.realizarCheckIn("AR123", "1.111.111-1");

        retorno = s.cerrarVuelo("AR123");

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("1.111.111-1;Ana;25;Frecuente|3.333.333-3;Carlos;30;Estándar", retorno.getValorString());
        assertEquals(1, retorno.getValorEntero());
    }

    @Test
    public void cerrarVueloErrores() {
        assertEquals(Retorno.Resultado.ERROR_1, s.cerrarVuelo(null).getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.cerrarVuelo("").getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.cerrarVuelo("NO_EXISTE").getResultado());
        assertEquals(Retorno.Resultado.ERROR_3, s.cerrarVuelo("AR123").getResultado());
    }
}
