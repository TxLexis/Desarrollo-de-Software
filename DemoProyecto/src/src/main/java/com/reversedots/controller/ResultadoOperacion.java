package src.main.java.com.reversedots.controller;


/**
 * Enumeración que representa los resultados de las operaciones del controlador.
 */
public enum ResultadoOperacion {
    EXITO,
    MOVIMIENTO_INVALIDO,
    TURNO_PASADO,
    JUEGO_FINALIZADO,
    ERROR_CARGA,
    ERROR_GUARDADO,
    JUGADOR_NO_ENCONTRADO,
    JUGADOR_YA_EXISTE,
    POSICION_INVALIDA,
    SIN_MOVIMIENTOS_VALIDOS,
    PARTIDA_NO_INICIADA
}