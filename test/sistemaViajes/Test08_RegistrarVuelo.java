package sistemaViajes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test08_RegistrarVuelo {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Montevideo");
        s.registrarAeropuerto("GRU", "Sao Paulo");
    }

    @Test
    public void registrarVueloOk() {
        retorno = s.registrarVuelo("MVD", "GRU", "V001", 100, 500);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.registrarVuelo("GRU", "MVD", "V002", 200, 800);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void registrarVueloError01EnteroInvalido() {
        // ERROR_1: capacidad o costo <= 0
        retorno = s.registrarVuelo("MVD", "GRU", "V001", 0, 500);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarVuelo("MVD", "GRU", "V001", -1, 500);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarVuelo("MVD", "GRU", "V001", 100, 0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarVuelo("MVD", "GRU", "V001", 100, -1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void registrarVueloError02StringVacioONull() {
        // ERROR_2: strings vacíos o null
        retorno = s.registrarVuelo("", "GRU", "V001", 100, 500);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarVuelo("MVD", "", "V001", 100, 500);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarVuelo("MVD", "GRU", "", 100, 500);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarVuelo(null, "GRU", "V001", 100, 500);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarVuelo("MVD", null, "V001", 100, 500);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarVuelo("MVD", "GRU", null, 100, 500);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void registrarVueloError03OrigenNoExiste() {
        retorno = s.registrarVuelo("AAA", "GRU", "V001", 100, 500);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void registrarVueloError04DestinoNoExiste() {
        retorno = s.registrarVuelo("MVD", "AAA", "V001", 100, 500);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    @Test
    public void registrarVueloError05CodigoYaExiste() {
        s.registrarVuelo("MVD", "GRU", "V001", 100, 500);
        retorno = s.registrarVuelo("GRU", "MVD", "V001", 200, 800);
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());
    }
}