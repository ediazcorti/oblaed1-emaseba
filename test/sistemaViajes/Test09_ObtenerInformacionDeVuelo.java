package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test09_ObtenerInformacionDeVuelo {

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
    public void obtenerInfoVueloOk() {
        retorno = s.obtenerInformacionDeVuelo("V001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Formato: origen;destino;codigo;capacidad;costo;estado;reservas;confirmados
        assertEquals("MVD;GRU;V001;100;500;Programado;0;0", retorno.getValorString());
    }

    @Test
    public void obtenerInfoVueloError01() {
        retorno = s.obtenerInformacionDeVuelo("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.obtenerInformacionDeVuelo(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.obtenerInformacionDeVuelo("  ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void obtenerInfoVueloError02() {
        retorno = s.obtenerInformacionDeVuelo("V999");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void obtenerInfoVueloReflestaCambiosDeEstado() {
        s.abrirVuelo("V001");
        retorno = s.obtenerInformacionDeVuelo("V001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MVD;GRU;V001;100;500;Abierto;0;0", retorno.getValorString());
    }
}
