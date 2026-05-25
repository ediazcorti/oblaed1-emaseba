package sistemaViajes;

import dominio.Categoria;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test13_RealizarCheckIn {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Montevideo");
        s.registrarAeropuerto("GRU", "Sao Paulo");
        s.registrarVuelo("MVD", "GRU", "V001", 2, 500); // capacidad exacta 2
        s.registrarPasajero("3.335.321-2", "Juan", 45, Categoria.ESPORADICO);
        s.registrarPasajero("6.430.147-9", "Maria", 30, Categoria.PLATINO);
        s.registrarPasajero("935.457-7", "Carlos", 22, Categoria.FRECUENTE);
        s.abrirVuelo("V001");
        s.realizarReserva("V001", "3.335.321-2");
        s.realizarReserva("V001", "6.430.147-9");
    }

    @Test
    public void realizarCheckInOk() {
        retorno = s.realizarCheckIn("V001", "3.335.321-2");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError01() {
        retorno = s.realizarCheckIn("", "3.335.321-2");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.realizarCheckIn("V001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.realizarCheckIn(null, "3.335.321-2");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.realizarCheckIn("V001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError02FormatoCedula() {
        retorno = s.realizarCheckIn("V001", "000000");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError03VueloNoExiste() {
        retorno = s.realizarCheckIn("V999", "3.335.321-2");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError04PasajeroNoExiste() {
        retorno = s.realizarCheckIn("V001", "1.234.567-8");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError05VueloNOAbierto() {
        s.registrarVuelo("MVD", "GRU", "V002", 10, 500);
        // V002 está PROGRAMADO, no ABIERTO
        retorno = s.realizarCheckIn("V002", "3.335.321-2");
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError06SinReserva() {
        // Carlos no tiene reserva en V001
        retorno = s.realizarCheckIn("V001", "935.457-7");
        assertEquals(Retorno.Resultado.ERROR_6, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError07YaRealizoCheckIn() {
        s.realizarCheckIn("V001", "3.335.321-2");
        retorno = s.realizarCheckIn("V001", "3.335.321-2"); // segunda vez
        assertEquals(Retorno.Resultado.ERROR_7, retorno.getResultado());
    }

    @Test
    public void realizarCheckInError08CapacidadMaxima() {
        // Capacidad exacta es 2, ya hay 2 reservas
        s.realizarCheckIn("V001", "3.335.321-2"); // 1 confirmado
        s.realizarCheckIn("V001", "6.430.147-9"); // 2 confirmados → lleno

        // Agregar 3ra reserva con overbooking y luego intentar check-in
        s.realizarReserva("V001", "935.457-7"); // entra por overbooking
        retorno = s.realizarCheckIn("V001", "935.457-7"); // no entra, capacidad exacta llena
        assertEquals(Retorno.Resultado.ERROR_8, retorno.getResultado());
    }
}
