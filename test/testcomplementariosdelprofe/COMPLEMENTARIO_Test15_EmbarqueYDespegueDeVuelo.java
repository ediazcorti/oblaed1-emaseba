package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;
import dominio.Categoria;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test15_EmbarqueYDespegueDeVuelo {

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
    public void embarqueYDespegueOk() {
        s.realizarReserva("AR123", "1.111.111-1");        
        s.realizarReserva("AR123", "2.222.222-2");
        
        s.abrirVuelo("AR123");
        s.realizarCheckIn("AR123", "1.111.111-1");    
        
        s.cerrarVuelo("AR123");
        retorno = s.embarqueYDespegueDeVuelo("MVD");
        
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MVD:EZE;AR123;1;230;Finalizado;2;1", s.obtenerInformacionDeVuelo("AR123").getValorString());
    }

    @Test
    public void embarqueYDespegueErroresBasicos() {
        assertEquals(Retorno.Resultado.ERROR_1, s.embarqueYDespegueDeVuelo(null).getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.embarqueYDespegueDeVuelo("").getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.embarqueYDespegueDeVuelo("    ").getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.embarqueYDespegueDeVuelo("NO_EXISTE").getResultado());
        assertEquals(Retorno.Resultado.ERROR_3, s.embarqueYDespegueDeVuelo("EZE").getResultado());     
    }

}
