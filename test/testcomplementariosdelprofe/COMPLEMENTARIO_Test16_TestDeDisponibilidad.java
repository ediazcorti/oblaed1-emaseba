package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;
import dominio.Clase;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test16_TestDeDisponibilidad {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    private static final int D = 0; // disponible
    private static final int O = 1; // ocupado
    private static final int N = 2; // no disponible

    @Before
    public void setUp() {
        s.inicializarSistema();
    }

    @Test
    public void consultaDisponibilidadError1() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 0, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.consultaDisponibilidad(asientos, 0, Clase.EJECUTIVA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.consultaDisponibilidad(asientos, 0, Clase.TURISTA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.consultaDisponibilidad(asientos, -5, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.consultaDisponibilidad(asientos, -4, Clase.EJECUTIVA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.consultaDisponibilidad(asientos, -7, Clase.TURISTA);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void consultaDisponibilidadPrimeraClaseMayorA4() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 5, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
        assertEquals(0, retorno.getValorEntero());
    }

    @Test
    public void consultaDisponibilidadPrimeraClaseCantidadEs2() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        String respuestaEsperada = "A1-C1|C1-D1|D1-F1|A2-C2|C2-D2|D2-F2|A3-C3|C3-D3|D3-F3";

        retorno = s.consultaDisponibilidad(asientos, 2, Clase.PRIMERA);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(9, retorno.getValorEntero());
    }

    @Test
    public void consultaDisponibilidadPrimeraClaseUnaOpcionLibreSegundaFila() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{O, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {O, D, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {O, D, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {O, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 2, Clase.PRIMERA);

        String respuestaEsperada = "C2-D2";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(1, retorno.getValorEntero());
    }

    @Test
    public void consultaDisponibilidadPrimeraClaseUnaOpcionLibrePrimeraFila() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{O, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {O, D, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 2, Clase.PRIMERA);

        String respuestaEsperada = "C1-D1";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(1, retorno.getValorEntero());
    }

    @Test
    public void consultaDisponibilidadPrimeraClaseLlena() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{O, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {O, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {O, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {O, O, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 1, Clase.PRIMERA);

        String respuestaEsperada = "";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(0, retorno.getValorEntero());
    }

    @Test
    public void consultaDisponibilidadPrimeraClaseParcialementeLlenaYCantidadEs1() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{O, D, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {O, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, O, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, O, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 1, Clase.PRIMERA);

        String respuestaEsperada = "D1|F1|A2|C2|F2|C3|D3";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(7, retorno.getValorEntero());
    }

    @Test
    public void consultaDisponibilidad_claseEjecutivaLibreYCantidadEs6() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 6, Clase.EJECUTIVA);

        String respuestaEsperada = "A4-B4-C4-D4-E4-F4|A5-B5-C5-D5-E5-F5|A6-B6-C6-D6-E6-F6|A7-B7-C7-D7-E7-F7";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(4, retorno.getValorEntero());

    }

    @Test
    public void consultaDisponibilidad_claseEjecutivaSoloConCuatroCombinacionesYCantidadEs3() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ O, O, D, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, O, D, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ D, O, D, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ D, O, D, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 3, Clase.EJECUTIVA);

        String respuestaEsperada = "B4-C4-D4|C4-D4-E4|A6-B6-C6|B6-C6-D6";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(4, retorno.getValorEntero());

    }

    @Test
    public void consultaDisponibilidad_claseEjecutivaSoloConUnaCombinacionDisponibleEnFila4YCantidadEs3() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ D, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ D, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 3, Clase.EJECUTIVA);

        String respuestaEsperada = "D4-E4-F4";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(1, retorno.getValorEntero());

    }

    @Test
    public void consultaDisponibilidad_claseEjecutivaSoloConUnaCombinacionDisponibleEnFila5YCantidadEs4() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ O, D, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ O, D, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ O, D, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ O, D, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 4, Clase.EJECUTIVA);

        String respuestaEsperada = "C5-D5-E5-F5";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(1, retorno.getValorEntero());

    }

    @Test
    public void consultaDisponibilidad_claseEjecutivaSoloConUnaCombinacionDisponibleEnFila7YCantidadEs2() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ O, O, O, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ O, O, O, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 2, Clase.EJECUTIVA);

        String respuestaEsperada = "E7-F7";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(1, retorno.getValorEntero());

    }

    @Test
    public void consultaDisponibilidad_claseEjecutivaParcialementeLlenaYCantidadEs1() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{O, D, O, /* - */ O, O, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ O, D, O, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {O, D, D, /* - */ O, O, D, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, O, D, /* - */ D, O, O, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ O, D, O, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, O, /* - */ D, O, D, O, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},};

        retorno = s.consultaDisponibilidad(asientos, 1, Clase.EJECUTIVA);

        String respuestaEsperada = "D4|F4|B5|E5|C6|F6|D7|E7";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(8, retorno.getValorEntero());

    }

    @Test
    public void consultaDisponibilidad_claseTuristaLibreYCantidadEs6() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
            //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
            //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
            /*A*/{D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*B*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*C*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*D*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*E*/ {N, N, N, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D},
            /*F*/ {D, D, D, /* - */ D, D, D, D, /* - */ D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D, D}
        };

        retorno = s.consultaDisponibilidad(asientos, 6, Clase.TURISTA);

        String respuestaEsperada = "A8-B8-C8-D8-E8-F8|A9-B9-C9-D9-E9-F9|"
                + "A10-B10-C10-D10-E10-F10|A11-B11-C11-D11-E11-F11|"
                + "A12-B12-C12-D12-E12-F12|A13-B13-C13-D13-E13-F13|"
                + "A14-B14-C14-D14-E14-F14|A15-B15-C15-D15-E15-F15|"
                + "A16-B16-C16-D16-E16-F16|A17-B17-C17-D17-E17-F17|"
                + "A18-B18-C18-D18-E18-F18|A19-B19-C19-D19-E19-F19|"
                + "A20-B20-C20-D20-E20-F20|A21-B21-C21-D21-E21-F21|"
                + "A22-B22-C22-D22-E22-F22|A23-B23-C23-D23-E23-F23|"
                + "A24-B24-C24-D24-E24-F24|A25-B25-C25-D25-E25-F25|"
                + "A26-B26-C26-D26-E26-F26";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(19, retorno.getValorEntero());

    }
    
     @Test
    public void consultaDisponibilidad_claseTuristaSoloConCuatroCombinacionesYCantidadEs3() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
                //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
                //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
                /*A*/{D, D, D, /* - */ O, O, D, O, /* - */ O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O},
                /*B*/{N, N, N, /* - */ D, O, D, O, /* - */ O, D, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O},
                /*C*/{D, D, D, /* - */ D, O, D, O, /* - */ O, D, O, O, O, O, O, O, O, D, O, O, O, O, O, O, O, O, O},
                /*D*/{D, D, D, /* - */ D, O, D, O, /* - */ O, D, O, O, O, O, O, O, O, D, O, O, O, O, O, O, O, O, O},
                /*E*/{N, N, N, /* - */ D, O, O, O, /* - */ O, D, O, O, O, O, O, O, O, D, O, O, O, O, O, O, O, O, O},
                /*F*/{D, D, D, /* - */ O, O, O, O, /* - */ O, O, O, O, O, O, O, O, O, D, O, O, O, O, O, O, O, O, O}
        };
        
        retorno = s.consultaDisponibilidad(asientos, 3, Clase.TURISTA);

        String respuestaEsperada = "B9-C9-D9|C9-D9-E9|C17-D17-E17|D17-E17-F17";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(4, retorno.getValorEntero());
       
    }
    
    @Test
    public void consultaDisponibilidad_claseTuristaParcialementeLlenaYCantidadEs1() {
        /*
         * P primera
         * E ejecutiva
         * T tursistica
         */

        final int[][] asientos = {
                //    1  2  3          4  5  6  7          8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
                //    P  P  P  /* - */ E  E  E  E  /* - */ T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T  T
                /*A*/{O, D, O, /* - */ O, O, O, O, /* - */ O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O},
                /*B*/{N, N, N, /* - */ O, D, O, O, /* - */ O, O, D, O, O, O, O, O, D, O, O, O, O, O, O, D, O, O, O},
                /*C*/{O, D, D, /* - */ O, O, D, O, /* - */ O, O, O, O, D, O, O, O, O, D, O, O, O, D, O, O, O, O, O},
                /*D*/{D, O, D, /* - */ D, O, O, D, /* - */ O, O, O, O, O, O, O, D, O, O, O, O, O, O, O, O, O, D, O},
                /*E*/{N, N, N, /* - */ O, D, O, D, /* - */ O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O},
                /*F*/{D, D, O, /* - */ D, O, D, O, /* - */ O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O},
        };

        retorno = s.consultaDisponibilidad(asientos, 1, Clase.TURISTA);

        String respuestaEsperada = "B10|C12|D15|B16|C17|C21|B23|D25";

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(respuestaEsperada, retorno.getValorString());
        assertEquals(8, retorno.getValorEntero());
                   
    }

}
