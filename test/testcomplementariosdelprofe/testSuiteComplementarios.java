package testcomplementariosdelprofe;
import sistemaViajes.Retorno;
import sistemaViajes.Sistema;
import sistemaViajes.ImplementacionSistema;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    COMPLEMENTARIO_Test01_InicializarSistema.class,
    COMPLEMENTARIO_Test02_RegistrarPasajero.class,
    COMPLEMENTARIO_Test03_BuscarPasajero.class,
    COMPLEMENTARIO_Test04_ListarPasajerosAscendente.class,
    COMPLEMENTARIO_Test05_ListarPasajerosDescendente.class,
    COMPLEMENTARIO_Test06_ListarPasajerosPorCategoria.class,
    COMPLEMENTARIO_Test07_RegistrarAeropuerto.class,
    COMPLEMENTARIO_Test08_ObtenerAeropuerto.class,
    COMPLEMENTARIO_Test09_RegistrarVuelo.class,
    COMPLEMENTARIO_Test10_ObtenerInformacionDeVuelo.class,
    COMPLEMENTARIO_Test11_AbrirVuelo.class,
    COMPLEMENTARIO_Test12_CerrarVuelo.class,
    COMPLEMENTARIO_Test13_RealizarReserva.class,
    COMPLEMENTARIO_Test14_RealizarCheckIn.class,
    COMPLEMENTARIO_Test15_EmbarqueYDespegueDeVuelo.class,
    COMPLEMENTARIO_Test16_TestDeDisponibilidad.class
})
public class testSuiteComplementarios {
}