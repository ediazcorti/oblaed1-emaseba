package sistemaViajes;

import dominio.Categoria;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test12_RealizarReserva {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Montevideo");
        s.registrarAeropuerto("GRU", "Sao Paulo");
        s.registrarVuelo("MVD", "GRU", "V001", 2, 500); // capacidad 2 → limite con overbooking = ceil(2.2) = 3
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        s.registrarPasajero("6.430.147-9", "Maria", 30, Categoria.PLATINO);
        s.registrarPasajero("935.457-7", "Carlos", 22, Categoria.FRECUENTE);
    }

    @Test
    public void realizarReservaOkEstadoProgramado() {
        // Vuelo en PROGRAMADO acepta reservas
        retorno = s.realizarReserva("V001", "3.335.321-2");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void realizarReservaOkEstadoAbierto() {
        s.abrirVuelo("V001");
        retorno = s.realizarReserva("V001", "3.335.321-2");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void realizarReservaError01() {
        retorno = s.realizarReserva("", "3.335.321-2");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.realizarReserva("V001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.realizarReserva(null, "3.335.321-2");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.realizarReserva("V001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void realizarReservaError02FormatoCedula() {
        retorno = s.realizarReserva("V001", "000000");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.realizarReserva("V001", "3.3X5.321-2");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void realizarReservaError03VueloNoExiste() {
        retorno = s.realizarReserva("V999", "3.335.321-2");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void realizarReservaError04PasajeroNoExiste() {
        retorno = s.realizarReserva("V001", "1.234.567-8");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    @Test
    public void realizarReservaError05EstadoInvalido() {
        s.abrirVuelo("V001");
        s.cerrarVuelo("V001");
        retorno = s.realizarReserva("V001", "3.335.321-2"); // vuelo CERRADO
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());
    }

    @Test
    public void realizarReservaError06PasajeroYaTieneReserva() {
        s.realizarReserva("V001", "3.335.321-2");
        retorno = s.realizarReserva("V001", "3.335.321-2"); // misma cedula, mismo vuelo
        assertEquals(Retorno.Resultado.ERROR_6, retorno.getResultado());
    }

    @Test
    public void realizarReservaError07VueloLleno() {
        // Capacidad 2, overbooking 10% → limite = ceil(2.2) = 3
        s.realizarReserva("V001", "3.335.321-2");
        s.realizarReserva("V001", "6.430.147-9");
        s.realizarReserva("V001", "935.457-7");
        // 3 reservas = límite, la 4ta debe fallar
        s.registrarPasajero("1.234.567-8", "Ana", 28, Categoria.ESTANDAR);
        retorno = s.realizarReserva("V001", "1.234.567-8");
        assertEquals(Retorno.Resultado.ERROR_7, retorno.getResultado());
    }
}
