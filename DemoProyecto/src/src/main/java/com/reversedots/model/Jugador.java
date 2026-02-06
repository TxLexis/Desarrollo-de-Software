package src.main.java.com.reversedots.model;


import java.io.Serializable;
import java.util.Objects;

/**
 * Representa un jugador en el sistema.
 */
public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasEmpatadas;

    /**
     * Constructor del jugador.
     * @param nombre nombre del jugador
     */
    public Jugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
        this.partidasGanadas = 0;
        this.partidasPerdidas = 0;
        this.partidasEmpatadas = 0;
    }

    /**
     * Constructor completo del jugador.
     * @param nombre nombre del jugador
     * @param partidasGanadas partidas ganadas
     * @param partidasPerdidas partidas perdidas
     * @param partidasEmpatadas partidas empatadas
     */
    public Jugador(String nombre, int partidasGanadas, int partidasPerdidas, int partidasEmpatadas) {
        this(nombre);
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.partidasEmpatadas = partidasEmpatadas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public int getPartidasEmpatadas() {
        return partidasEmpatadas;
    }

    /**
     * Incrementa las partidas ganadas.
     */
    public void incrementarGanadas() {
        this.partidasGanadas++;
    }

    /**
     * Incrementa las partidas perdidas.
     */
    public void incrementarPerdidas() {
        this.partidasPerdidas++;
    }

    /**
     * Incrementa las partidas empatadas.
     */
    public void incrementarEmpatadas() {
        this.partidasEmpatadas++;
    }

    /**
     * Obtiene el total de partidas jugadas.
     * @return total de partidas
     */
    public int getTotalPartidas() {
        return partidasGanadas + partidasPerdidas + partidasEmpatadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return nombre.equalsIgnoreCase(jugador.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("%s (G:%d P:%d E:%d)",
                nombre, partidasGanadas, partidasPerdidas, partidasEmpatadas);
    }
}