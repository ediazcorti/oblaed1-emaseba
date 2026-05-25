package sistemaViajes;

import dominio.Clase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test15_ConsultaDisponibilidad {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    // Matriz de 4 filas x 3 columnas
    // Valores: PRIMERA=0, EJECUTIVA=1, TURISTA=2
    //          col: 1    2    3
    private final int[][] matriz = {
        {0, 0, 1},  // fila A: primera, primera, ejecutiva
        {0, 1, 1},  // fila B: primera, ejecutiva, ejecutiva
        {0, 1, 2},  // fila C: primera, ejecutiva, turista
        {2, 2, 2}   // fila D: turista,  turista,  turista
    };

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void consultaEncontraBloquesDos() {
        // Buscamos 2 asientos contiguos de PRIMERA
        // Columna 1: A-B (ambas PRIMERA), B-C (ambas PRIMERA) → 2 bloques
        // Columna 2: solo fila A es PRIMERA → ningún bloque de 2
        retorno = s.consultaDisponibilidad(matriz, 2, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(2, retorno.getValorEntero());
        assertEquals("A1-B1|B1-C1", retorno.getValorString());
    }

    @Test
    public void consultaEncontraBloquesTres() {
        // 3 asientos contiguos de PRIMERA en columna 1: A-B-C
        retorno = s.consultaDisponibilidad(matriz, 3, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(1, retorno.getValorEntero());
        assertEquals("A1-B1-C1", retorno.getValorString());
    }

    @Test
    public void consultaSinResultados() {
        // TURISTA de 3 asientos: solo columna 3 tiene turista en D3,
        // pero no hay 3 contiguos de turista en ninguna columna
        retorno = s.consultaDisponibilidad(matriz, 3, Clase.TURISTA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void consultaEjecutivaDosBloques() {
        // EJECUTIVA cantidad 2
        // Col 2: B-C (ambas ejecutiva) → 1 bloque
        // Col 3: A-B (ambas ejecutiva) → 1 bloque
        retorno = s.consultaDisponibilidad(matriz, 2, Clase.EJECUTIVA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(2, retorno.getValorEntero());
    }

    @Test
    public void consultaCantidadUno() {
        // Cantidad 1 de TURISTA: D1, D2, D3 → 3 bloques de 1
        retorno = s.consultaDisponibilidad(matriz, 1, Clase.TURISTA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(4, retorno.getValorEntero());
        assertEquals("D1|D2|C3|D3", retorno.getValorString());

    }

    @Test
    public void consultaError01MatrizNull() {
        retorno = s.consultaDisponibilidad(null, 2, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaError01CantidadCero() {
        retorno = s.consultaDisponibilidad(matriz, 0, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaError01CantidadNegativa() {
        retorno = s.consultaDisponibilidad(matriz, -1, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaError01ClaseNull() {
        retorno = s.consultaDisponibilidad(matriz, 2, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaCantidadMayorQueFilas() {
        // Pedir 5 contiguos en matriz de 4 filas → no puede haber bloques
        retorno = s.consultaDisponibilidad(matriz, 5, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
    }
}
