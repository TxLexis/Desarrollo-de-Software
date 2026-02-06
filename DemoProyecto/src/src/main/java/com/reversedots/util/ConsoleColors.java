package src.main.java.com.reversedots.util;

/**
 * Utilidad para colores ANSI en consola.
 */
public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";

    // Colores regulares
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Colores brillantes
    public static final String BRIGHT_BLACK = "\033[0;90m";
    public static final String BRIGHT_RED = "\033[0;91m";
    public static final String BRIGHT_GREEN = "\033[0;92m";
    public static final String BRIGHT_YELLOW = "\033[0;93m";
    public static final String BRIGHT_BLUE = "\033[0;94m";
    public static final String BRIGHT_PURPLE = "\033[0;95m";
    public static final String BRIGHT_CYAN = "\033[0;96m";
    public static final String BRIGHT_WHITE = "\033[0;97m";

    // Fondos
    public static final String BG_BLACK = "\033[40m";
    public static final String BG_WHITE = "\033[47m";
    public static final String BG_BRIGHT_WHITE = "\033[107m";

    // Estilos
    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";

    /**
     * Aplica color a un texto.
     * @param text texto
     * @param color color
     * @return texto con color
     */
    public static String colorear(String text, String color) {
        return color + text + RESET;
    }

    /**
     * Texto de error.
     * @param text texto
     * @return texto de error
     */
    public static String error(String text) {
        return BRIGHT_RED + "✗ " + text + RESET;
    }

    /**
     * Texto de éxito.
     * @param text texto
     * @return texto de éxito
     */
    public static String exito(String text) {
        return BRIGHT_GREEN + "✓ " + text + RESET;
    }

    /**
     * Texto de información.
     * @param text texto
     * @return texto de información
     */
    public static String info(String text) {
        return BRIGHT_CYAN + "ℹ " + text + RESET;
    }

    /**
     * Texto de advertencia.
     * @param text texto
     * @return texto de advertencia
     */
    public static String advertencia(String text) {
        return BRIGHT_YELLOW + "⚠ " + text + RESET;
    }
}