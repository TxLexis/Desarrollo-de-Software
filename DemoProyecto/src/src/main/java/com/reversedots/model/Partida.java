package src.main.java.com.reversedots.model;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa una partida del juego.
 */
public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Jugador jugador1;
    private final Jugador jugador2;
    private final Color colorJugador1;
    private final Color colorJugador2;
    private final Tablero tablero;
    private Color turnoActual;
    private boolean finalizada;
    private Jugador ganador;
    private final LocalDateTime fechaCreacion;
    private int turnosPasadosConsecutivos;

    /**
     * Constructor de la partida.
     * @param jugador1 primer jugador
     * @param jugador2 segundo jugador
     * @param tamanoTablero tamaño del tablero
     * @param jugador1EmpiezaPrimero true si jugador1 empieza primero
     */
    public Partida(Jugador jugador1, Jugador jugador2, int tamanoTablero,
                   boolean jugador1EmpiezaPrimero) {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1.equals(jugador2)) {
            throw new IllegalArgumentException("Los jugadores deben ser diferentes");
        }

        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero = new Tablero(tamanoTablero);
        this.finalizada = false;
        this.ganador = null;
        this.fechaCreacion = LocalDateTime.now();
        this.turnosPasadosConsecutivos = 0;

        // Asignar colores: el que empieza primero es NEGRO
        if (jugador1EmpiezaPrimero) {
            this.colorJugador1 = Color.NEGRO;
            this.colorJugador2 = Color.BLANCO;
        } else {
            this.colorJugador1 = Color.BLANCO;
            this.colorJugador2 = Color.NEGRO;
        }

        // El turno siempre empieza con NEGRO
        this.turnoActual = Color.NEGRO;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Color getColorJugador1() {
        return colorJugador1;
    }

    public Color getColorJugador2() {
        return colorJugador2;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Color getTurnoActual() {
        return turnoActual;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public int getTurnosPasadosConsecutivos() {
        return turnosPasadosConsecutivos;
    }

    /**
     * Obtiene el jugador del turno actual.
     * @return jugador del turno actual
     */
    public Jugador getJugadorActual() {
        return turnoActual == colorJugador1 ? jugador1 : jugador2;
    }

    /**
     * Obtiene el color de un jugador.
     * @param jugador jugador
     * @return color del jugador
     */
    public Color getColorJugador(Jugador jugador) {
        if (jugador.equals(jugador1)) {
            return colorJugador1;
        } else if (jugador.equals(jugador2)) {
            return colorJugador2;
        }
        throw new IllegalArgumentException("El jugador no pertenece a esta partida");
    }

    /**
     * Cambia el turno al siguiente jugador.
     */
    public void cambiarTurno() {
        turnoActual = turnoActual.opuesto();
    }

    /**
     * Registra un turno pasado.
     */
    public void registrarTurnoPasado() {
        turnosPasadosConsecutivos++;
    }

    /**
     * Resetea el contador de turnos pasados consecutivos.
     */
    public void resetearTurnosPasados() {
        turnosPasadosConsecutivos = 0;
    }

    /**
     * Finaliza la partida y determina el ganador.
     */
    public void finalizar() {
        this.finalizada = true;

        int fichasJugador1 = tablero.contarFichas(colorJugador1);
        int fichasJugador2 = tablero.contarFichas(colorJugador2);

        if (fichasJugador1 > fichasJugador2) {
            this.ganador = jugador1;
            jugador1.incrementarGanadas();
            jugador2.incrementarPerdidas();
        } else if (fichasJugador2 > fichasJugador1) {
            this.ganador = jugador2;
            jugador2.incrementarGanadas();
            jugador1.incrementarPerdidas();
        } else {
            this.ganador = null; // Empate
            jugador1.incrementarEmpatadas();
            jugador2.incrementarEmpatadas();
        }
    }

    /**
     * Obtiene las estadísticas actuales de la partida.
     * @return arreglo con [fichasJugador1, fichasJugador2]
     */
    public int[] getEstadisticas() {
        return new int[] {
                tablero.contarFichas(colorJugador1),
                tablero.contarFichas(colorJugador2)
        };
    }
}