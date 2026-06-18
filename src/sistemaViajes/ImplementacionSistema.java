// Emanuel Diaz 275164 - Sebastián Hohl 327007 
package sistemaViajes;

// Agregar aquí nombres y números de estudiante de los integrantes del equipo

import dominio.Clase;
import dominio.Categoria;
import dominio.Estado;
import dominio.Aeropuerto;
import dominio.Reserva;
import dominio.Pasajero;
import dominio.Vuelo;
import tads.ListaSimple;
import tads.ListaDoble;

public class ImplementacionSistema implements Sistema {

    private ListaDoble<Pasajero> pasajeros;
    private ListaSimple<Pasajero>[] porCategoria;
    private ListaSimple<Aeropuerto> aeropuertos;
    private ListaSimple<Vuelo> vuelos;

    // ----------------------------------------------------------------
    // Helpers privados
    // ----------------------------------------------------------------

    private boolean esVacioONull(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean esFormatoCedulaValido(String cedula) {
        return cedula.matches("([1-9]\\.\\d{3}|\\d{3})\\.\\d{3}-\\d");
    }

    // ================================================================
    // Op 01 - Inicializar Sistema
    // ================================================================


    @SuppressWarnings("unchecked")
    @Override
    public Retorno inicializarSistema() {
        pasajeros = new ListaDoble<>();
        porCategoria = new ListaSimple[4];
        for (int i = 0; i < 4; i++) {
            porCategoria[i] = new ListaSimple<>();
        }
        aeropuertos = new ListaSimple<>();
        vuelos = new ListaSimple<>();
        return Retorno.ok();
    }

    // ================================================================
    // Op 02 - Registrar Pasajero
    // ================================================================

    @Override
    public Retorno registrarPasajero(String cedula, String nombre, int edad, Categoria categoria) {
        // ERROR_1: algún parámetro vacío, null, edad negativa o categoria null
        if (esVacioONull(cedula) || esVacioONull(nombre) || categoria == null) {
            return Retorno.error1();
        }
        // ERROR_2: formato de cédula inválido
        if (!esFormatoCedulaValido(cedula)) {
            return Retorno.error2();
        }
        
        // ERROR_3: Si la edad es menor a 0
        if (edad < 0 ) {
            return Retorno.error3();
        }
        
        // ERROR_4: ya existe un pasajero con esa cédula
        Pasajero pasajeroAux = new Pasajero(cedula, null, 0, null);
        if (pasajeros.buscar(pasajeroAux) != null) {
            return Retorno.error4();
        }
        // Registrar: insertar ordenado en lista principal y en la lista de su categoría
        Pasajero nuevo = new Pasajero(cedula, nombre, edad, categoria);
        pasajeros.insertarOrdenado(nuevo);
        porCategoria[categoria.getIndice()].insertarOrdenado(nuevo);
        return Retorno.ok();
    }

    // ================================================================
    // Op 03 - Buscar Pasajero
    // ================================================================

    @Override
    public Retorno buscarPasajero(String cedula) {
        // ERROR_1: cédula vacía, null o formato inválido
        if (esVacioONull(cedula) || !esFormatoCedulaValido(cedula)) {
            return Retorno.error1();
        }
        // ERROR_2: no existe pasajero con esa cédula
        Pasajero pasajeroAux = new Pasajero(cedula, null, 0, null);
        Pasajero encontrado = pasajeros.buscar(pasajeroAux);
        if (encontrado == null) {
            return Retorno.error2();
        }
        return Retorno.ok(encontrado.toString());
    }

    // ================================================================
    // Op 04 - Listar Pasajeros Ascendente
    // ================================================================

    @Override
    public Retorno listarPasajerosAscendente() {
        // No hay errores posibles — retorna lista vacía si no hay pasajeros
        return Retorno.ok(pasajeros.listarAsc());
    }

    // ================================================================
    // Op 05 - Listar Pasajeros Descendente
    // ================================================================

    @Override
    public Retorno listarPasajerosDescendente() {
        // No hay errores posibles — retorna lista vacía si no hay pasajeros
        return Retorno.ok(pasajeros.listarDesc());
    }

    // ================================================================
    // Op 06 - Listar Pasajeros por Categoría
    // ================================================================

    @Override
    public Retorno listarPasajerosPorCategoría(Categoria unaCategoria) {
    // Si unaCategoria es null retornamos error (Esto no viene en los requerimientos 
    // pero el profe nos dijo por teams que lo podiamos agregar si queremos)    
        if (unaCategoria == null) {
        return Retorno.error1();
    }
        // Acceso directo a la lista de esa categoría — O(k)
        return Retorno.ok(porCategoria[unaCategoria.getIndice()].listar());
    }

    // ================================================================
    // Op 07 - Registrar Aeropuerto
    // ================================================================

    @Override
    public Retorno registrarAeropuerto(String codigo, String nombre) {
        // ERROR_1: parámetros vacíos o null
        if (esVacioONull(codigo) || esVacioONull(nombre)) {
            return Retorno.error1();
        }
        // ERROR_2: ya existe un aeropuerto con ese código
        // Nota: la verificación es O(n) aunque el insert sea O(1).
        // El profe reconoció la contradicción y hablamos de la letra en la clase, especificando que esto estaba bien.
        Aeropuerto aeropuertoAux = new Aeropuerto(codigo, null);
        if (aeropuertos.buscar(aeropuertoAux) != null) {
            return Retorno.error2();
        }
        aeropuertos.agregarInicio(new Aeropuerto(codigo, nombre));
        return Retorno.ok();
    }

    // ================================================================
    // Op 08 - Obtener Aeropuerto
    // ================================================================

    @Override
    public Retorno obtenerAeropuerto(String codigo) {
        // ERROR_1: código vacío o null
        if (esVacioONull(codigo)) {
            return Retorno.error1();
        }
        // ERROR_2: no existe aeropuerto con ese código
        Aeropuerto aeropuertoAux = new Aeropuerto(codigo, null);
        Aeropuerto encontrado = aeropuertos.buscar(aeropuertoAux);
        if (encontrado == null) {
            return Retorno.error2();
        }
        // valorString: "codigo;nombre" | valorEntero: vuelos en cola
        return new Retorno(Retorno.Resultado.OK,
                encontrado.toString(),
                encontrado.getCantidadVuelosEnCola());
    }

    // ================================================================
    // Op 09 - Registrar Vuelo
    // ================================================================

    @Override
    public Retorno registrarVuelo(String codigoAeropuertoOrigen, String codigoAeropuertoDestino,
            String codigoDeVuelo, int capacidad, int costoEnDolares) {
        // ERROR_1: INTS recibidos vacios o null
        if (capacidad <= 0 || costoEnDolares <= 0) {
            return Retorno.error1();
        }
        // ERROR_2: Strings recibidos vacios o nulls
        if (esVacioONull(codigoAeropuertoOrigen) || esVacioONull(codigoAeropuertoDestino)
                || esVacioONull(codigoDeVuelo)) {
            return Retorno.error2();
        }
        
        // ERROR_3: aeropuerto origen no existe
        Aeropuerto aeropuertoAux = new Aeropuerto(codigoAeropuertoOrigen, null);
        Aeropuerto origen = aeropuertos.buscar(aeropuertoAux);
        if (origen == null) {
            return Retorno.error3();
        }
        // ERROR_4: aeropuerto destino no existe
        aeropuertoAux = new Aeropuerto(codigoAeropuertoDestino, null);
        Aeropuerto destino = aeropuertos.buscar(aeropuertoAux);
        if (destino == null) {
            return Retorno.error4();
        }
        // ERROR_5: ya existe un vuelo con ese código
        Vuelo vueloAux = new Vuelo(codigoDeVuelo, null, null, 0, 0);
        if (vuelos.buscar(vueloAux) != null) {
            return Retorno.error5();
        }
        vuelos.agregarInicio(new Vuelo(codigoDeVuelo, origen, destino, capacidad, costoEnDolares));
        return Retorno.ok();
    }

    // ================================================================
    // Op 10 - Obtener Información de Vuelo
    // ================================================================

    @Override
    public Retorno obtenerInformacionDeVuelo(String codigoDeVuelo) {
        // ERROR_1: código vacío o null
        if (esVacioONull(codigoDeVuelo)) {
            return Retorno.error1();
        }
        // ERROR_2: no existe vuelo con ese código
        Vuelo vueloAux = new Vuelo(codigoDeVuelo, null, null, 0, 0);
        Vuelo encontrado = vuelos.buscar(vueloAux);
        if (encontrado == null) {
            return Retorno.error2();
        }
        return Retorno.ok(encontrado.toString());
    }

    // ================================================================
    // Op 11 - Abrir Vuelo
    // ================================================================

    @Override
    public Retorno abrirVuelo(String codigoDeVuelo) {
        // ERROR_1: código vacío o null
        if (esVacioONull(codigoDeVuelo)) {
            return Retorno.error1();
        }
        // ERROR_2: no existe vuelo con ese código
        Vuelo vueloAux = new Vuelo(codigoDeVuelo, null, null, 0, 0);
        Vuelo vuelo = vuelos.buscar(vueloAux);
        if (vuelo == null) {
            return Retorno.error2();
        }
        // ERROR_3: el vuelo no está en estado PROGRAMADO
        if (vuelo.getEstado() != Estado.PROGRAMADO) {
            return Retorno.error3();
        }
        vuelo.setEstado(Estado.ABIERTO);
        return Retorno.ok();
    }

    // ================================================================
    // Op 12 - Cerrar Vuelo
    // ================================================================

    @Override
    public Retorno cerrarVuelo(String codigoDeVuelo) {
        // ERROR_1: código vacío o null
        if (esVacioONull(codigoDeVuelo)) {
            return Retorno.error1();
        }
        // ERROR_2: no existe vuelo con ese código
        Vuelo vueloAux = new Vuelo(codigoDeVuelo, null, null, 0, 0);
        Vuelo vuelo = vuelos.buscar(vueloAux);
        if (vuelo == null) {
            return Retorno.error2();
        }
        // ERROR_3: el vuelo no está en estado ABIERTO
        if (vuelo.getEstado() != Estado.ABIERTO) {
            return Retorno.error3();
        }
        vuelo.setEstado(Estado.CERRADO);
        // encolar en el aeropuerto de origen para esperar despegue
        vuelo.getOrigen().encolarVuelo(vuelo);
        // valorString: pasajeros confirmados ordenados por cédula
        // valorEntero: cantidad de reservados que NO hicieron check-in
        return new Retorno(Retorno.Resultado.OK,
                vuelo.listarConfirmados(),
                vuelo.getCantidadSinConfirmar());
    }

    // ================================================================
    // Op 13 - Realizar Reserva
    // ================================================================

    @Override
    public Retorno realizarReserva(String codigoDeVuelo, String cedula) {
        // ERROR_1: parámetros vacíos o null
        if (esVacioONull(codigoDeVuelo) || esVacioONull(cedula)) {
            return Retorno.error1();
        }
        
        // ERROR_2: La cedula no tiene el formato Correcto
        if(!esFormatoCedulaValido(cedula)){
            return Retorno.error2();
        }       
        
        // ERROR_3: vuelo no existe
        Vuelo vueloAux = new Vuelo(codigoDeVuelo, null, null, 0, 0);
        Vuelo vuelo = vuelos.buscar(vueloAux);
        if (vuelo == null) {
            return Retorno.error3();
        }
        
        // ERROR_5: vuelo no está en estado PROGRAMADO o ABIERTO
        // Ejecutamos esto primero para que el sistema no busque un pasajero innecesariamente 
        // antes de saber el estado del vuelo
        if (vuelo.getEstado() != Estado.PROGRAMADO && vuelo.getEstado() != Estado.ABIERTO) {
            return Retorno.error5();
        }  
       
        // ERROR_4: pasajero no existe en el sistema
        Pasajero pasajeroAux = new Pasajero(cedula, null, 0, null);
        Pasajero pasajero = pasajeros.buscar(pasajeroAux);
        if (pasajero == null) {
            return Retorno.error4();
        }
      
        // ERROR_6: el pasajero ya tiene una reserva en ese vuelo
        Reserva reservaAux = new Reserva(pasajeroAux);
        if (vuelo.buscarReserva(reservaAux) != null) {
            return Retorno.error6();
        }
        // ERROR_7: vuelo lleno (capacidad con overbooking del 10%)
        if (vuelo.getCantidadReservas() >= vuelo.getLimiteReservas()) {
            return Retorno.error7();
        }
        vuelo.agregarReserva(new Reserva(pasajero));
        return Retorno.ok();
    }

    // ================================================================
    // Op 14 - Realizar Check-In
    // ================================================================

    @Override
    public Retorno realizarCheckIn(String codigoDeVuelo, String cedula) {
        // ERROR_1: parámetros vacíos o null
        if (esVacioONull(codigoDeVuelo) || esVacioONull(cedula)) {
            return Retorno.error1();
        }
        
        // ERROR_2: La cedula no tiene el formato Correcto
        if(!esFormatoCedulaValido(cedula)){
            return Retorno.error2();
        }
        
        // ERROR_3: vuelo no existe
        Vuelo vueloAux = new Vuelo(codigoDeVuelo, null, null, 0, 0);
        Vuelo vuelo = vuelos.buscar(vueloAux);
        if (vuelo == null) {
            return Retorno.error3();
        }
        
         // ERROR_4: pasajero no existe en el sistema
        Pasajero pasajeroAux = new Pasajero(cedula, null, 0, null);
        Pasajero pasajero = pasajeros.buscar(pasajeroAux);
        if (pasajero == null) {
            return Retorno.error4();
        }
        
        // ERROR_5: el vuelo no está en estado ABIERTO
        // Ejecutamos esto primero para que el sistema no busque un pasajero innecesariamente 
        // antes de saber el estado del vuelo
        if (vuelo.getEstado() != Estado.ABIERTO) {
            return Retorno.error5();
        }
        
       
          
        // ERROR_6: el pasajero no tiene reserva en ese vuelo
        Reserva reservaAux = new Reserva(pasajeroAux);
        Reserva reserva = vuelo.buscarReserva(reservaAux);
        if (reserva == null) {
            return Retorno.error6();
        }
        // ERROR_7: el pasajero ya realizó el check-in
        if (reserva.isConfirmada()) {
            return Retorno.error7();
        }
        // ERROR_8: vuelo lleno (capacidad exacta, sin overbooking para check-in)
        if (vuelo.getCantidadConfirmados() >= vuelo.getCapacidad()) {
            return Retorno.error8();
        }
        vuelo.confirmarReserva(reserva);
        return Retorno.ok();
    }

    // ================================================================
    // Op 15 - Embarque y Despegue de Vuelo
    // ================================================================

    @Override
    public Retorno embarqueYDespegueDeVuelo(String codigoAeropuerto) {
        // ERROR_1: código vacío o null
        if (esVacioONull(codigoAeropuerto)) {
            return Retorno.error1();
        }
        // ERROR_2: aeropuerto no existe con ese codigo
        Aeropuerto aeropuertoAux = new Aeropuerto(codigoAeropuerto, null);
        Aeropuerto aeropuerto = aeropuertos.buscar(aeropuertoAux);
        if (aeropuerto == null) {
            return Retorno.error2();
        }
        // ERROR_3: Si no hay ningún vuelo esperando embarque y despegue en ese aeropuerto
        if (aeropuerto.colaEstaVacia()) {
            return Retorno.error3();
        }
        Vuelo vuelo = aeropuerto.desencolarVuelo();
        vuelo.setEstado(Estado.FINALIZADO);
        // valorString: código del vuelo que despegó
        // valorEntero: vuelos que quedan en cola en ese aeropuerto
        return new Retorno(Retorno.Resultado.OK,
                vuelo.getCodigoVuelo(),
                aeropuerto.getCantidadVuelosEnCola());
    }

    // ================================================================
    // Op 16 - Consulta Disponibilidad
    // ================================================================

@Override
public Retorno consultaDisponibilidad(int[][] matriz, int cantidad, Clase unaClase) {
    if (matriz == null || cantidad <= 0 || unaClase == null) {
        return Retorno.error1();
    }

    int filas = matriz.length;
    int columnas = (filas > 0) ? matriz[0].length : 0;
    if (filas == 0 || columnas == 0) {
        return new Retorno(Retorno.Resultado.OK, "", 0);
    }

    final int DISPONIBLE = 0; // D
    final int NO_APLICA   = 2; // N — skip, no rompe la cadena
    // O (=1) rompe la cadena implícitamente (no es DISPONIBLE ni NO_APLICA)

    // ── Detectar zona de PRIMERA: columnas que tienen algún N ──────────
    int primeraEnd = 0;
    for (int col = 0; col < columnas; col++) {
        boolean tieneN = false;
        for (int fil = 0; fil < filas; fil++) {
            if (matriz[fil][col] == NO_APLICA) { tieneN = true; break; }
        }
        if (tieneN) primeraEnd = col + 1;
        else        break; // zona PRIMERA es contigua desde col 0
    }

    // ── Límites de zona para la clase pedida ──────────────────────────
    // Avión de la consigna: 3 cols PRIMERA, 4 cols EJECUTIVA, resto TURISTA
    int ejecutivaCount = 4; // fijo para este avión
    int ejecutivaStart = primeraEnd;
    int ejecutivaEnd   = Math.min(ejecutivaStart + ejecutivaCount, columnas);

    int colStart, colEnd;
    switch (unaClase.getIndice()) {
        case 0:  colStart = 0;              colEnd = primeraEnd;   break; // PRIMERA
        case 1:  colStart = ejecutivaStart; colEnd = ejecutivaEnd; break; // EJECUTIVA
        default: colStart = ejecutivaEnd;   colEnd = columnas;     break; // TURISTA
    }

    // ── Buscar bloques con ventana deslizante ─────────────────────────
    String resultado = "";
    int cantBloques = 0;
    boolean primero = true;

    for (int col = colStart; col < colEnd; col++) {
        // Lista de índices de filas D actualmente en la ventana
        int[] ventana = new int[cantidad];
        int tamVentana = 0;

        for (int fila = 0; fila < filas; fila++) {
            int valor = matriz[fila][col];

            if (valor == DISPONIBLE) {
                ventana[tamVentana++] = fila;

                if (tamVentana == cantidad) {
                    // Armar string del bloque con los índices reales de fila
                    String bloque = "";
                    for (int i = 0; i < cantidad; i++) {
                        if (i > 0) bloque += "-";
                        bloque += (char) ('A' + ventana[i]);
                        bloque += (col + 1);
                    }
                    if (!primero) resultado += "|";
                    resultado += bloque;
                    primero = false;
                    cantBloques++;

                    // Deslizar: descartar el primer elemento de la ventana
                    for (int i = 0; i < cantidad - 1; i++) ventana[i] = ventana[i + 1];
                    tamVentana--;
                }

            } else if (valor != NO_APLICA) {
                // O (ocupado) — rompe la cadena
                tamVentana = 0;
            }
            // N (no aplica) — no hace nada, ventana se mantiene
        }
    }

    return new Retorno(Retorno.Resultado.OK, resultado, cantBloques);
}
}
