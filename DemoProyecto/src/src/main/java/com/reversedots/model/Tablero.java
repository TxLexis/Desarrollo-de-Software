package src.main.java.com.reversedots.model;

import java.io.Serializable;

/**
 * Representa el tablero de juego.
 */
public class Tablero implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int tamano;
    private final Ficha[][] celdas;

    /**
     * Constructor del tablero.
     * @param tamano tamaño del tablero (debe ser par y >= 4)
     */
    public Tablero(int tamano) {
        if (tamano < 4 || tamano % 2 != 0) {
            throw new IllegalArgumentException("El tamaño debe ser par y mayor o igual a 4");
        }
        this.tamano = tamano;
        this.celdas = new Ficha[tamano][tamano];
        inicializarTablero();
    }

    /**
     * Inicializa el tablero con las fichas del centro.
     */
    private void inicializarTablero() {
        int centro = tamano / 2;
        celdas[centro - 1][centro - 1] = new Ficha(Color.BLANCO);
        celdas[centro - 1][centro] = new Ficha(Color.NEGRO);
        celdas[centro][centro - 1] = new Ficha(Color.NEGRO);
        celdas[centro][centro] = new Ficha(Color.BLANCO);
    }

    /**
     * Obtiene el tamaño del tablero.
     * @return tamaño del tablero
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * Obtiene la ficha en una posición.
     * @param fila fila de la posición
     * @param columna columna de la posición
     * @return ficha en la posición o null si está vacía
     */
    public Ficha getFicha(int fila, int columna) {
        validarPosicion(fila, columna);
        return celdas[fila][columna];
    }

    /**
     * Coloca una ficha en una posición.
     * @param fila fila de la posición
     * @param columna columna de la posición
     * @param ficha ficha a colocar
     */
    public void colocarFicha(int fila, int columna, Ficha ficha) {
        validarPosicion(fila, columna);
        celdas[fila][columna] = ficha;
    }

    /**
     * Verifica si una posición está vacía.
     * @param fila fila de la posición
     * @param columna columna de la posición
     * @return true si está vacía, false en caso contrario
     */
    public boolean estaVacia(int fila, int columna) {
        validarPosicion(fila, columna);
        return celdas[fila][columna] == null;
    }

    /**
     * Verifica si una posición es válida.
     * @param fila fila de la posición
     * @param columna columna de la posición
     * @return true si es válida, false en caso contrario
     */
    public boolean esValida(int fila, int columna) {
        return fila >= 0 && fila < tamano && columna >= 0 && columna < tamano;
    }

    /**
     * Valida una posición y lanza excepción si no es válida.
     * @param fila fila de la posición
     * @param columna columna de la posición
     */
    private void validarPosicion(int fila, int columna) {
        if (!esValida(fila, columna)) {
            throw new IllegalArgumentException(
                    String.format("Posición inválida: (%d, %d)", fila, columna));
        }
    }

    /**
     * Cuenta las fichas de un color.
     * @param color color a contar
     * @return cantidad de fichas
     */
    public int contarFichas(Color color) {
        int count = 0;
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (celdas[i][j] != null && celdas[i][j].getColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Verifica si el tablero está lleno.
     * @return true si está lleno, false en caso contrario
     */
    public boolean estaLleno() {
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (celdas[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Crea una copia del tablero.
     * @return copia del tablero
     */
    public Tablero copiar() {
        Tablero copia = new Tablero(this.tamano);
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (this.celdas[i][j] != null) {
                    copia.celdas[i][j] = new Ficha(this.celdas[i][j].getColor());
                }
            }
        }
        return copia;
    }
}