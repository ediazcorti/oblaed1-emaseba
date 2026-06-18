package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;

import org.junit.Test;
import static org.junit.Assert.*;

public class COMPLEMENTARIO_Test01_InicializarSistema {

    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();

    @Test
    public void inicializarSistemaOk() {
        retorno = s.inicializarSistema();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());    
    }
}
