package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;
import dominio.Categoria;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test14_RealizarCheckIn {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "AR123", 1, 230);
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.FRECUENTE);
        s.registrarPasajero("2.222.222-2", "Bruno", 40, Categoria.PLATINO);
        s.registrarPasajero("3.333.333-3", "Carlos", 30, Categoria.ESTANDAR);
    }

    @Test
    public void realizarCheckInOk() {
        s.realizarReserva("AR123", "1.111.111-1");
        s.abrirVuelo("AR123");

        retorno = s.realizarCheckIn("AR123", "1.111.111-1");

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MVD:EZE;AR123;1;230;Abierto;1;1", s.obtenerInformacionDeVuelo("AR123").getValorString());
    }

    @Test
    public void realizarCheckInErroresBasicos() {
        assertEquals(Retorno.Resultado.ERROR_1, s.realizarCheckIn(null, "1.111.111-1").getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.realizarCheckIn("AR123", "").getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.realizarCheckIn("AR123", "1.11.111-1").getResultado());
        assertEquals(Retorno.Resultado.ERROR_3, s.realizarCheckIn("NO_EXISTE", "1.111.111-1").getResultado());
        assertEquals(Retorno.Resultado.ERROR_4, s.realizarCheckIn("AR123", "3.444.333-3").getResultado());
        assertEquals(Retorno.Resultado.ERROR_5, s.realizarCheckIn("AR123", "3.333.333-3").getResultado());
        s.abrirVuelo("AR123");
        assertEquals(Retorno.Resultado.ERROR_6, s.realizarCheckIn("AR123", "3.333.333-3").getResultado());
        s.realizarReserva("AR123", "1.111.111-1");
        s.realizarCheckIn("AR123", "1.111.111-1");
        assertEquals(Retorno.Resultado.ERROR_7, s.realizarCheckIn("AR123", "1.111.111-1").getResultado());

    }

}
