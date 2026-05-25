package sistemaViajes;

import dominio.Categoria;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test11_CerrarVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Montevideo");
        s.registrarAeropuerto("GRU", "Sao Paulo");
        s.registrarVuelo("MVD", "GRU", "V001", 10, 500);
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        s.registrarPasajero("6.430.147-9", "Maria", 30, Categoria.PLATINO);
        s.registrarPasajero("935.457-7", "Carlos", 22, Categoria.FRECUENTE);
    }

    @Test
    public void cerrarVueloOkSinReservas() {
        s.abrirVuelo("V001");
        retorno = s.cerrarVuelo("V001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());   // nadie confirmado
        assertEquals(0, retorno.getValorEntero());    // nadie sin confirmar
    }

    @Test
    public void cerrarVueloOkConReservasSinCheckIn() {
        s.abrirVuelo("V001");
        s.realizarReserva("V001", "3.335.321-2");
        s.realizarReserva("V001", "6.430.147-9");
        retorno = s.cerrarVuelo("V001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());   // ninguno hizo check-in
        assertEquals(2, retorno.getValorEntero());    // 2 reservados sin confirmar
    }

    @Test
    public void cerrarVueloOkConCheckInsOrdenados() {
        s.abrirVuelo("V001");
        s.realizarReserva("V001", "3.335.321-2");
        s.realizarReserva("V001", "6.430.147-9");
        s.realizarReserva("V001", "935.457-7");
        s.realizarCheckIn("V001", "6.430.147-9");
        s.realizarCheckIn("V001", "935.457-7");
        retorno = s.cerrarVuelo("V001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        // Confirmados ordenados por cédula: 935457 < 6430147
        assertEquals("935.457-7;Carlos;22;Frecuente|6.430.147-9;Maria;30;Platino",
                retorno.getValorString());
        assertEquals(1, retorno.getValorEntero()); // Juan no hizo check-in
    }

    @Test
    public void cerrarVueloEncolarEnAeropuerto() {
        s.abrirVuelo("V001");
        s.cerrarVuelo("V001");
        // Verificamos que el aeropuerto tiene 1 vuelo en cola
        retorno = s.obtenerAeropuerto("MVD");
        assertEquals(1, retorno.getValorEntero());
    }

    @Test
    public void cerrarVueloError01() {
        retorno = s.cerrarVuelo("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.cerrarVuelo(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void cerrarVueloError02() {
        retorno = s.cerrarVuelo("V999");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void cerrarVueloError03VueloProgramado() {
        retorno = s.cerrarVuelo("V001"); // está PROGRAMADO, no ABIERTO
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void cerrarVueloError03VueloYaCerrado() {
        s.abrirVuelo("V001");
        s.cerrarVuelo("V001");
        retorno = s.cerrarVuelo("V001"); // ya está CERRADO
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
