package sistemaViajes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    Test01_InicializarSistema.class,
    Test02_RegistrarPasajero.class,
    Test03_BuscarPasajero.class,
    Test04_ListarPasajeros.class,
    Test05_ListarPasajerosPorCategoria.class,
    Test06_RegistrarAeropuerto.class,
    Test07_ObtenerAeropuerto.class,
    Test08_RegistrarVuelo.class,
    Test09_ObtenerInformacionDeVuelo.class,
    Test10_AbrirVuelo.class,
    Test11_CerrarVuelo.class,
    Test12_RealizarReserva.class,
    Test13_RealizarCheckIn.class,
    Test14_EmbarqueYDespegueDeVuelo.class,
    Test15_ConsultaDisponibilidad.class
})
public class correrTestSuiteTest {
}