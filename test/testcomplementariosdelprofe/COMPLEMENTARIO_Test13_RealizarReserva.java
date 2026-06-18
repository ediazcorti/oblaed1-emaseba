package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;
import dominio.Categoria;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test13_RealizarReserva {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
        s.registrarAeropuerto("MVD", "Aeropuerto de Carrasco");
        s.registrarAeropuerto("EZE", "Ezeiza");
        s.registrarVuelo("MVD", "EZE", "AR123", 2, 230);
        s.registrarPasajero("1.111.111-1", "Ana", 25, Categoria.FRECUENTE);
        s.registrarPasajero("2.222.222-2", "Bruno", 40, Categoria.PLATINO);
        s.registrarPasajero("3.333.333-3", "Carlos", 30, Categoria.ESTANDAR);
        s.registrarPasajero("935.457-7", "Diana", 50, Categoria.ESPORADICO);
    }

    @Test
    public void realizarReservaOkEnProgramadoYAbierto() {
        retorno = s.realizarReserva("AR123", "1.111.111-1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        s.abrirVuelo("AR123");
        retorno = s.realizarReserva("AR123", "2.222.222-2");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    public void realizarReservaErroresBasicos() {
        assertEquals(Retorno.Resultado.ERROR_1, s.realizarReserva(null, "1.111.111-1").getResultado());
        assertEquals(Retorno.Resultado.ERROR_1, s.realizarReserva("AR123", "").getResultado());
        assertEquals(Retorno.Resultado.ERROR_2, s.realizarReserva("AR123", "1.11.111-1").getResultado());
        assertEquals(Retorno.Resultado.ERROR_3, s.realizarReserva("NO_EXISTE", "1.111.111-1").getResultado());
        assertEquals(Retorno.Resultado.ERROR_4, s.realizarReserva("AR123", "4.444.444-4").getResultado());
    }

}
