package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;
import dominio.Categoria;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test06_ListarPasajerosPorCategoria {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void listarCategoriaSinPasajerosRetornaVacio() {
        retorno = s.listarPasajerosPorCategoría(Categoria.FRECUENTE);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarCategoriaFiltraYOrdenaPorCedulaAscendente() {
        s.registrarPasajero("4.985.345-4", "Ana", 25, Categoria.FRECUENTE);
        s.registrarPasajero("935.457-7", "Zoe", 40, Categoria.PLATINO);
        s.registrarPasajero("1.345.345-4", "Alberto", 62, Categoria.FRECUENTE);

        retorno = s.listarPasajerosPorCategoría(Categoria.FRECUENTE);

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("1.345.345-4;Alberto;62;Frecuente|4.985.345-4;Ana;25;Frecuente", retorno.getValorString());
    }
}
