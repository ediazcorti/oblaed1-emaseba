package sistemaViajes;

import dominio.Clase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test15_ConsultaDisponibilidad {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    /*
     * Encoding correcto (igual al del profesor):
     *   D = 0  →  disponible  (asiento libre, SUMA a la cadena)
     *   O = 1  →  ocupado     (asiento tomado, ROMPE la cadena)
     *   N = 2  →  no aplica   (no existe ese asiento aquí, se IGNORA / transparente)
     *
     * Estructura del avión en todos los tests:
     *   Zona PRIMERA    = columnas con N en filas B (idx 1) y E (idx 4)
     *   Zona EJECUTIVA  = 4 columnas inmediatas después de PRIMERA (sin N)
     *   Zona TURISTA    = columnas restantes
     *
     * Diseño de matrices:  3P + 4E + 2T = 9 cols, 6 filas A-F
     *   Labels: P1 P2 P3 | E4 E5 E6 E7 | T8 T9
     *   Índices: 0  1  2 |  3  4  5  6 |  7  8
     */
    private static final int D = 0;
    private static final int O = 1;
    private static final int N = 2;

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    // ================================================================
    // ERROR_1
    // ================================================================

    @Test
    public void consultaError01MatrizNull() {
        retorno = s.consultaDisponibilidad(null, 2, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaError01CantidadCero() {
        final int[][] m = {{D, D, D, D, D, D, D, D, D}};
        retorno = s.consultaDisponibilidad(m, 0, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaError01CantidadNegativa() {
        final int[][] m = {{D, D, D, D, D, D, D, D, D}};
        retorno = s.consultaDisponibilidad(m, -1, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaError01ClaseNull() {
        final int[][] m = {{D, D, D, D, D, D, D, D, D}};
        retorno = s.consultaDisponibilidad(m, 2, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    // ================================================================
    // PRIMERA — clave: N en filas B y E se IGNORA (no rompe la cadena)
    // ================================================================

    @Test
    public void consultaPrimeraEncuentraBloquesCantidadDos() {
        /*
         * PRIMERA cols (0-2): filas disponibles = A(0) C(2) D(3) F(5), B y E son N (skip)
         * Pares consecutivos por col: A-C, C-D, D-F  →  3 bloques × 3 cols = 9
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8  T9
         */
        final int[][] m = {
            {D,  D,  D,  D,  D,  D,  D,  D,  D},  // A
            {N,  N,  N,  D,  D,  D,  D,  D,  D},  // B  ← N en PRIMERA
            {D,  D,  D,  D,  D,  D,  D,  D,  D},  // C
            {D,  D,  D,  D,  D,  D,  D,  D,  D},  // D
            {N,  N,  N,  D,  D,  D,  D,  D,  D},  // E  ← N en PRIMERA
            {D,  D,  D,  D,  D,  D,  D,  D,  D},  // F
        };
        retorno = s.consultaDisponibilidad(m, 2, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(9, retorno.getValorEntero());
        assertEquals(
            "A1-C1|C1-D1|D1-F1|A2-C2|C2-D2|D2-F2|A3-C3|C3-D3|D3-F3",
            retorno.getValorString()
        );
    }

    @Test
    public void consultaPrimeraEncuentraBloquesCantidadTres() {
        /*
         * Misma matriz. Filas efectivas: A C D F
         * Triples: A-C-D, C-D-F  →  2 bloques × 3 cols = 6
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8  T9
         */
        final int[][] m = {
            {D,  D,  D,  D,  D,  D,  D,  D,  D},
            {N,  N,  N,  D,  D,  D,  D,  D,  D},
            {D,  D,  D,  D,  D,  D,  D,  D,  D},
            {D,  D,  D,  D,  D,  D,  D,  D,  D},
            {N,  N,  N,  D,  D,  D,  D,  D,  D},
            {D,  D,  D,  D,  D,  D,  D,  D,  D},
        };
        retorno = s.consultaDisponibilidad(m, 3, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(6, retorno.getValorEntero());
        assertEquals(
            "A1-C1-D1|C1-D1-F1|A2-C2-D2|C2-D2-F2|A3-C3-D3|C3-D3-F3",
            retorno.getValorString()
        );
    }

    @Test
    public void consultaPrimeraSinResultadosTodoOcupado() {
        /*
         * PRIMERA cols: todos O (ocupado), N se mantiene en B y E.
         * → 0 bloques aunque la zona existe.
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8  T9
         */
        final int[][] m = {
            {O,  O,  O,  D,  D,  D,  D,  D,  D},  // A
            {N,  N,  N,  D,  D,  D,  D,  D,  D},  // B
            {O,  O,  O,  D,  D,  D,  D,  D,  D},  // C
            {O,  O,  O,  D,  D,  D,  D,  D,  D},  // D
            {N,  N,  N,  D,  D,  D,  D,  D,  D},  // E
            {O,  O,  O,  D,  D,  D,  D,  D,  D},  // F
        };
        retorno = s.consultaDisponibilidad(m, 2, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
        assertEquals("", retorno.getValorString());
    }

    // ================================================================
    // EJECUTIVA — O rompe la cadena, sin N en esta zona
    // ================================================================

    @Test
    public void consultaEjecutivaEncuentraBloquesCantidadDos() {
        /*
         * Solo col E4 (idx 3) tiene disponibles: B D C D → pares B4-C4 y C4-D4
         * Cols E5 E6 E7 (idx 4-6): todo ocupado → 0 bloques
         * Total: 2 bloques
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8
         */
        final int[][] m = {
            {D,  D,  D,  O,  O,  O,  O,  O},  // A
            {N,  N,  N,  D,  O,  O,  O,  O},  // B
            {D,  D,  D,  D,  O,  O,  O,  O},  // C
            {D,  D,  D,  D,  O,  O,  O,  O},  // D
            {N,  N,  N,  O,  O,  O,  O,  O},  // E
            {D,  D,  D,  O,  O,  O,  O,  O},  // F
        };
        retorno = s.consultaDisponibilidad(m, 2, Clase.EJECUTIVA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(2, retorno.getValorEntero());
        assertEquals("B4-C4|C4-D4", retorno.getValorString());
    }

    @Test
    public void consultaEjecutivaSinResultadosTodoOcupado() {
        /*
         * Toda la zona EJECUTIVA ocupada → 0 bloques
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8  T9
         */
        final int[][] m = {
            {D,  D,  D,  O,  O,  O,  O,  D,  D},
            {N,  N,  N,  O,  O,  O,  O,  D,  D},
            {D,  D,  D,  O,  O,  O,  O,  D,  D},
            {D,  D,  D,  O,  O,  O,  O,  D,  D},
            {N,  N,  N,  O,  O,  O,  O,  D,  D},
            {D,  D,  D,  O,  O,  O,  O,  D,  D},
        };
        retorno = s.consultaDisponibilidad(m, 2, Clase.EJECUTIVA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
        assertEquals("", retorno.getValorString());
    }

    // ================================================================
    // TURISTA — O rompe la cadena, sin N en esta zona
    // ================================================================

    @Test
    public void consultaTuristaEncuentraBloquesCantidadDos() {
        /*
         * T8 (idx 7): O D D D D O → cadena B-C-D-E (4 disponibles)
         *   pares: B8-C8, C8-D8, D8-E8  →  3 bloques
         * T9 (idx 8): todo ocupado → 0 bloques
         * Total: 3 bloques
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8  T9
         */
        final int[][] m = {
            {D,  D,  D,  D,  D,  D,  D,  O,  O},  // A
            {N,  N,  N,  D,  D,  D,  D,  D,  O},  // B  ← T8 disponible
            {D,  D,  D,  D,  D,  D,  D,  D,  O},  // C  ← T8 disponible
            {D,  D,  D,  D,  D,  D,  D,  D,  O},  // D  ← T8 disponible
            {N,  N,  N,  D,  D,  D,  D,  D,  O},  // E  ← T8 disponible
            {D,  D,  D,  D,  D,  D,  D,  O,  O},  // F
        };
        retorno = s.consultaDisponibilidad(m, 2, Clase.TURISTA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(3, retorno.getValorEntero());
        assertEquals("B8-C8|C8-D8|D8-E8", retorno.getValorString());
    }

    @Test
    public void consultaTuristaSinResultadosTodoOcupado() {
        /*
         * Zona TURISTA toda ocupada → 0 bloques
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8  T9
         */
        final int[][] m = {
            {D,  D,  D,  D,  D,  D,  D,  O,  O},
            {N,  N,  N,  D,  D,  D,  D,  O,  O},
            {D,  D,  D,  D,  D,  D,  D,  O,  O},
            {D,  D,  D,  D,  D,  D,  D,  O,  O},
            {N,  N,  N,  D,  D,  D,  D,  O,  O},
            {D,  D,  D,  D,  D,  D,  D,  O,  O},
        };
        retorno = s.consultaDisponibilidad(m, 2, Clase.TURISTA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void consultaTuristaCantidadUno() {
        /*
         * T8 (idx 7): O D O D O O → disponibles B y D → 2 bloques: B8, D8
         * T9 (idx 8): O O D O D O → disponibles C y E → 2 bloques: C9, E9
         * Total: 4 bloques
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8  T9
         */
        final int[][] m = {
            {D,  D,  D,  O,  O,  O,  O,  O,  O},  // A
            {N,  N,  N,  O,  O,  O,  O,  D,  O},  // B  ← T8 disponible
            {D,  D,  D,  O,  O,  O,  O,  O,  D},  // C  ← T9 disponible
            {D,  D,  D,  O,  O,  O,  O,  D,  O},  // D  ← T8 disponible
            {N,  N,  N,  O,  O,  O,  O,  O,  D},  // E  ← T9 disponible
            {D,  D,  D,  O,  O,  O,  O,  O,  O},  // F
        };
        retorno = s.consultaDisponibilidad(m, 1, Clase.TURISTA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(4, retorno.getValorEntero());
        assertEquals("B8|D8|C9|E9", retorno.getValorString());
    }

    // ================================================================
    // EDGE CASES
    // ================================================================

    @Test
    public void consultaCantidadMayorQueFilas() {
        /*
         * Matriz 6 filas, cantidad=7 → imposible formar bloques → 0
         *
         *        P1  P2  P3  E4  E5  E6  E7  T8
         */
        final int[][] m = {
            {D,  D,  D,  D,  D,  D,  D,  D},
            {N,  N,  N,  D,  D,  D,  D,  D},
            {D,  D,  D,  D,  D,  D,  D,  D},
            {D,  D,  D,  D,  D,  D,  D,  D},
            {N,  N,  N,  D,  D,  D,  D,  D},
            {D,  D,  D,  D,  D,  D,  D,  D},
        };
        retorno = s.consultaDisponibilidad(m, 7, Clase.TURISTA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(0, retorno.getValorEntero());
        assertEquals("", retorno.getValorString());
    }
}