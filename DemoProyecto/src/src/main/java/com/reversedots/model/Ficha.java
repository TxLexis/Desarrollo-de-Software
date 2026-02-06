package src.main.java.com.reversedots.model;

import java.io.Serializable;

/**
 * Representa una ficha en el tablero.
 */
public class Ficha implements Serializable {
    private static final long serialVersionUID = 1L;
    private Color color;

    /**
     * Constructor de la ficha.
     * @param color color de la ficha
     */
    public Ficha(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("El color no puede ser nulo");
        }
        this.color = color;
    }

    /**
     * Obtiene el color de la ficha.
     * @return color de la ficha
     */
    public Color getColor() {
        return color;
    }

    /**
     * Voltea la ficha al color opuesto.
     */
    public void voltear() {
        this.color = color.opuesto();
    }

    /**
     * Establece el color de la ficha.
     * @param color nuevo color
     */
    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("El color no puede ser nulo");
        }
        this.color = color;
    }

    @Override
    public String toString() {
        return String.valueOf(color.getSimbolo());
    }
}