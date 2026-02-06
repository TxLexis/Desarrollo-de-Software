package src.main.java.com.reversedots.controller;


import src.main.java.com.reversedots.exception.MovimientoInvalidoException;
import src.main.java.com.reversedots.exception.RepositoryException;
import src.main.java.com.reversedots.model.*;
import src.main.java.com.reversedots.repository.JugadorRepository;
import src.main.java.com.reversedots.repository.PartidaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controlador principal del juego.
 */
public class JuegoController {

    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private Partida partidaActual;

    // Direcciones: N, NE, E, SE, S, SW, W, NW
    private static final int[][] DIRECCIONES = {
            {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
            {1, 0}, {1, -1}, {0, -1}, {-1, -1}
    };

    /**
     * Constructor del controlador.
     * @param jugadorRepository repositorio de jugadores
     * @param partidaRepository repositorio de partidas
     */
    public JuegoController(JugadorRepository jugadorRepository,
                           PartidaRepository partidaRepository) {
        if (jugadorRepository == null || partidaRepository == null) {
            throw new IllegalArgumentException("Los repositorios no pueden ser nulos");
        }
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
    }

    /**
     * Obtiene la partida actual.
     * @return partida actual o null si no hay partida
     */
    public Partida getPartidaActual() {
        return partidaActual;
    }

    /**
     * Registra un nuevo jugador.
     * @param nombre nombre del jugador
     * @return resultado de la operación
     */
    public ResultadoOperacion registrarJugador(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return ResultadoOperacion.ERROR_GUARDADO;
            }

            if (jugadorRepository.existe(nombre)) {
                return ResultadoOperacion.JUGADOR_YA_EXISTE;
            }

            Jugador jugador = new Jugador(nombre);
            jugadorRepository.guardar(jugador);
            return ResultadoOperacion.EXITO;
        } catch (RepositoryException e) {
            return ResultadoOperacion.ERROR_GUARDADO;
        }
    }

    /**
     * Lista todos los jugadores registrados.
     * @return lista de jugadores
     * @throws RepositoryException si ocurre un error
     */
    public List<Jugador> listarJugadores() throws RepositoryException {
        return jugadorRepository.listarTodos();
    }

    /**
     * Busca un jugador por nombre.
     * @param nombre nombre del jugador
     * @return jugador encontrado o null
     * @throws RepositoryException si ocurre un error
     */
    public Jugador buscarJugador(String nombre) throws RepositoryException {
        return jugadorRepository.buscarPorNombre(nombre).orElse(null);
    }

    /**
     * Inicia una nueva partida.
     * @param nombreJugador1 nombre del primer jugador
     * @param nombreJugador2 nombre del segundo jugador
     * @param tamanoTablero tamaño del tablero
     * @return resultado de la operación
     */
    public ResultadoOperacion iniciarPartida(String nombreJugador1,
                                             String nombreJugador2,
                                             int tamanoTablero) {
        try {
            Jugador jugador1 = jugadorRepository.buscarPorNombre(nombreJugador1)
                    .orElse(null);
            Jugador jugador2 = jugadorRepository.buscarPorNombre(nombreJugador2)
                    .orElse(null);

            if (jugador1 == null || jugador2 == null) {
                return ResultadoOperacion.JUGADOR_NO_ENCONTRADO;
            }

            // Seleccionar aleatoriamente quién empieza
            Random random = new Random();
            boolean jugador1Primero = random.nextBoolean();

            partidaActual = new Partida(jugador1, jugador2, tamanoTablero, jugador1Primero);
            return ResultadoOperacion.EXITO;
        } catch (RepositoryException e) {
            return ResultadoOperacion.ERROR_CARGA;
        } catch (IllegalArgumentException e) {
            return ResultadoOperacion.POSICION_INVALIDA;
        }
    }

    /**
     * Realiza un movimiento en el tablero.
     * @param fila fila del movimiento
     * @param columna columna del movimiento
     * @return resultado de la operación
     * @throws MovimientoInvalidoException si el movimiento es inválido
     */
    public ResultadoOperacion realizarMovimiento(int fila, int columna)
            throws MovimientoInvalidoException {
        if (partidaActual == null) {
            return ResultadoOperacion.PARTIDA_NO_INICIADA;
        }

        if (partidaActual.isFinalizada()) {
            return ResultadoOperacion.JUEGO_FINALIZADO;
        }

        Tablero tablero = partidaActual.getTablero();

        // Validar posición
        if (!tablero.esValida(fila, columna)) {
            throw new MovimientoInvalidoException(
                    String.format("Posición fuera del tablero: (%d, %d)", fila, columna));
        }

        if (!tablero.estaVacia(fila, columna)) {
            throw new MovimientoInvalidoException(
                    "La posición ya está ocupada");
        }

        // Verificar si el movimiento es válido
        List<Posicion> fichasAVoltear = obtenerFichasAVoltear(fila, columna,
                partidaActual.getTurnoActual());

        if (fichasAVoltear.isEmpty()) {
            throw new MovimientoInvalidoException(
                    "El movimiento no voltea ninguna ficha del oponente");
        }

        // Realizar el movimiento
        tablero.colocarFicha(fila, columna, new Ficha(partidaActual.getTurnoActual()));

        // Voltear fichas
        for (Posicion pos : fichasAVoltear) {
            Ficha ficha = tablero.getFicha(pos.fila, pos.columna);
            ficha.voltear();
        }

        // Resetear turnos pasados consecutivos
        partidaActual.resetearTurnosPasados();

        // Cambiar turno
        partidaActual.cambiarTurno();

        // Verificar si el siguiente jugador tiene movimientos
        if (!tieneMovimientosValidos(partidaActual.getTurnoActual())) {
            partidaActual.registrarTurnoPasado();
            partidaActual.cambiarTurno();

            // Si tampoco tiene movimientos, el juego termina
            if (!tieneMovimientosValidos(partidaActual.getTurnoActual())) {
                finalizarPartida();
                return ResultadoOperacion.JUEGO_FINALIZADO;
            }

            return ResultadoOperacion.TURNO_PASADO;
        }

        // Verificar si el tablero está lleno
        if (tablero.estaLleno()) {
            finalizarPartida();
            return ResultadoOperacion.JUEGO_FINALIZADO;
        }

        return ResultadoOperacion.EXITO;
    }

    /**
     * Pasa el turno al siguiente jugador.
     * @return resultado de la operación
     */
    public ResultadoOperacion pasarTurno() {
        if (partidaActual == null) {
            return ResultadoOperacion.PARTIDA_NO_INICIADA;
        }

        if (partidaActual.isFinalizada()) {
            return ResultadoOperacion.JUEGO_FINALIZADO;
        }

        partidaActual.registrarTurnoPasado();
        partidaActual.cambiarTurno();

        // Si se pasaron 2 turnos consecutivos, el juego termina
        if (partidaActual.getTurnosPasadosConsecutivos() >= 2) {
            finalizarPartida();
            return ResultadoOperacion.JUEGO_FINALIZADO;
        }

        return ResultadoOperacion.EXITO;
    }

    /**
     * Verifica si un color tiene movimientos válidos.
     * @param color color a verificar
     * @return true si tiene movimientos válidos
     */
    public boolean tieneMovimientosValidos(Color color) {
        if (partidaActual == null) {
            return false;
        }

        Tablero tablero = partidaActual.getTablero();

        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                if (tablero.estaVacia(i, j)) {
                    if (!obtenerFichasAVoltear(i, j, color).isEmpty()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Obtiene las posiciones válidas para un color.
     * @param color color del jugador
     * @return lista de posiciones válidas
     */
    public List<Posicion> obtenerMovimientosValidos(Color color) {
        List<Posicion> movimientos = new ArrayList<>();

        if (partidaActual == null) {
            return movimientos;
        }

        Tablero tablero = partidaActual.getTablero();

        for (int i = 0; i < tablero.getTamano(); i++) {
            for (int j = 0; j < tablero.getTamano(); j++) {
                if (tablero.estaVacia(i, j)) {
                    if (!obtenerFichasAVoltear(i, j, color).isEmpty()) {
                        movimientos.add(new Posicion(i, j));
                    }
                }
            }
        }

        return movimientos;
    }

    /**
     * Obtiene las fichas que se voltearían con un movimiento.
     * @param fila fila del movimiento
     * @param columna columna del movimiento
     * @param color color del jugador
     * @return lista de posiciones a voltear
     */
    private List<Posicion> obtenerFichasAVoltear(int fila, int columna, Color color) {
        List<Posicion> fichasAVoltear = new ArrayList<>();
        Tablero tablero = partidaActual.getTablero();

        // Verificar en las 8 direcciones
        for (int[] dir : DIRECCIONES) {
            List<Posicion> fichasEnDireccion = new ArrayList<>();
            int f = fila + dir[0];
            int c = columna + dir[1];

            // Buscar fichas del oponente en esta dirección
            while (tablero.esValida(f, c) && !tablero.estaVacia(f, c)) {
                Ficha ficha = tablero.getFicha(f, c);

                if (ficha.getColor() == color) {
                    // Encontramos una ficha propia, las intermedias se voltean
                    fichasAVoltear.addAll(fichasEnDireccion);
                    break;
                } else {
                    // Ficha del oponente
                    fichasEnDireccion.add(new Posicion(f, c));
                }

                f += dir[0];
                c += dir[1];
            }
        }

        return fichasAVoltear;
    }

    /**
     * Finaliza la partida actual.
     */
    private void finalizarPartida() {
        if (partidaActual != null && !partidaActual.isFinalizada()) {
            partidaActual.finalizar();

            // Actualizar estadísticas de los jugadores
            try {
                jugadorRepository.actualizar(partidaActual.getJugador1());
                jugadorRepository.actualizar(partidaActual.getJugador2());
            } catch (RepositoryException e) {
                System.err.println("Error al actualizar estadísticas: " + e.getMessage());
            }
        }
    }

    /**
     * Guarda la partida actual.
     * @param rutaArchivo ruta del archivo
     * @return resultado de la operación
     */
    public ResultadoOperacion guardarPartida(String rutaArchivo) {
        if (partidaActual == null) {
            return ResultadoOperacion.PARTIDA_NO_INICIADA;
        }

        try {
            partidaRepository.guardar(partidaActual, rutaArchivo);
            return ResultadoOperacion.EXITO;
        } catch (RepositoryException e) {
            return ResultadoOperacion.ERROR_GUARDADO;
        }
    }

    /**
     * Carga una partida desde un archivo.
     * @param rutaArchivo ruta del archivo
     * @return resultado de la operación
     */
    public ResultadoOperacion cargarPartida(String rutaArchivo) {
        try {
            partidaActual = partidaRepository.cargar(rutaArchivo);
            return ResultadoOperacion.EXITO;
        } catch (RepositoryException e) {
            return ResultadoOperacion.ERROR_CARGA;
        }
    }

    /**
     * Verifica si existe un archivo de partida.
     * @param rutaArchivo ruta del archivo
     * @return true si existe
     */
    public boolean existePartida(String rutaArchivo) {
        return partidaRepository.existe(rutaArchivo);
    }

    /**
     * Clase interna para representar posiciones.
     */
    public static class Posicion {
        public final int fila;
        public final int columna;

        public Posicion(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }
    }
}