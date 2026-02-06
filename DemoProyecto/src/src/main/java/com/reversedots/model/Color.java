package src.main.java.com.reversedots.model;

/**
 * Enumeración que representa los colores de las fichas en el juego.
 */
public enum Color {
    NEGRO('B', "Negro"),
    BLANCO('W', "Blanco");

    private final char simbolo;
    private final String nombre;

    Color(char simbolo, String nombre) {
        this.simbolo = simbolo;
        this.nombre = nombre;
    }

    /**
     * Obtiene el símbolo del color.
     * @return símbolo de la ficha
     */
    public char getSimbolo() {
        return simbolo;
    }

    /**
     * Obtiene el nombre del color.
     * @return nombre del color
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el color opuesto.
     * @return color opuesto
     */
    public Color opuesto() {
        return this == NEGRO ? BLANCO : NEGRO;
    }

    /**
     * Convierte un símbolo en un color.
     * @param simbolo símbolo a convertir
     * @return color correspondiente
     */
    public static Color fromSimbolo(char simbolo) {
        for (Color color : values()) {
            if (color.simbolo == simbolo) {
                return color;
            }
        }
        throw new IllegalArgumentException("Símbolo de color inválido: " + simbolo);
    }
}