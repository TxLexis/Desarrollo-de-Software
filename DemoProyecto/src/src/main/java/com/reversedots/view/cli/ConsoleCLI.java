package src.main.java.com.reversedots.view.cli;

import src.main.java.com.reversedots.controller.JuegoController;
import src.main.java.com.reversedots.controller.JuegoController.Posicion;
import src.main.java.com.reversedots.controller.ResultadoOperacion;
import src.main.java.com.reversedots.exception.MovimientoInvalidoException;
import src.main.java.com.reversedots.exception.RepositoryException;
import src.main.java.com.reversedots.model.*;
import src.main.java.com.reversedots.repository.JugadorRepository;
import src.main.java.com.reversedots.repository.PartidaRepository;
import src.main.java.com.reversedots.repository.JugadorRepositoryImpl;
import src.main.java.com.reversedots.repository.PartidaRepositoryImpl;
import src.main.java.com.reversedots.util.ConsoleColors;

import java.util.List;
import java.util.Scanner;

/**
 * Vista de consola para el juego Reverse Dots.
 */
public class ConsoleCLI {

    private final JuegoController controller;
    private final Scanner scanner;
    private boolean ejecutando;

    /**
     * Constructor de la vista CLI.
     */
    public ConsoleCLI() {
        JugadorRepository jugadorRepo = new JugadorRepositoryImpl();
        PartidaRepository partidaRepo = new PartidaRepositoryImpl();
        this.controller = new JuegoController(jugadorRepo, partidaRepo);
        this.scanner = new Scanner(System.in);
        this.ejecutando = true;
    }

    /**
     * Inicia la aplicación.
     */
    public void iniciar() {
        mostrarBienvenida();

        while (ejecutando) {
            try {
                mostrarMenuPrincipal();
                String comando = leerComando();
                procesarComandoMenuPrincipal(comando);
            } catch (Exception e) {
                System.out.println(ConsoleColors.error("Error inesperado: " + e.getMessage()));
                System.out.println(ConsoleColors.info("Escriba 'help' para ver los comandos disponibles"));
            }
        }

        scanner.close();
    }

    /**
     * Muestra el mensaje de bienvenida.
     */
    private void mostrarBienvenida() {
        limpiarConsola();
        System.out.println(ConsoleColors.BRIGHT_CYAN + "╔════════════════════════════════════════╗");
        System.out.println("║                                        ║");
        System.out.println("║         REVERSE DOTS GAME              ║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝" + ConsoleColors.RESET);
        System.out.println();
    }

    /**
     * Muestra el menú principal.
     */
    private void mostrarMenuPrincipal() {
        System.out.println(ConsoleColors.BOLD + "\n=== MENÚ PRINCIPAL ===" + ConsoleColors.RESET);
        System.out.println("1. " + ConsoleColors.GREEN + "new" + ConsoleColors.RESET + "     - Iniciar nueva partida");
        System.out.println("2. " + ConsoleColors.YELLOW + "load" + ConsoleColors.RESET + "    - Cargar partida guardada");
        System.out.println("3. " + ConsoleColors.CYAN + "players" + ConsoleColors.RESET + " - Ver jugadores registrados");
        System.out.println("4. " + ConsoleColors.RED + "exit" + ConsoleColors.RESET + "    - Salir del juego");
        System.out.println("5. " + ConsoleColors.BLUE + "help" + ConsoleColors.RESET + "    - Ayuda");
        System.out.print("\n" + ConsoleColors.BRIGHT_WHITE + "Comando: " + ConsoleColors.RESET);
    }

    /**
     * Procesa los comandos del menú principal.
     * @param comando comando ingresado
     */
    private void procesarComandoMenuPrincipal(String comando) {
        String[] partes = comando.trim().split("\\s+");
        String cmd = partes[0].toLowerCase();

        switch (cmd) {
            case "new":
            case "1":
                iniciarNuevaPartida();
                break;
            case "load":
            case "2":
                if (partes.length > 1) {
                    cargarPartida(partes[1]);
                } else {
                    cargarPartidaInteractivo();
                }
                break;
            case "players":
            case "3":
                mostrarJugadores();
                break;
            case "exit":
            case "4":
                salir();
                break;
            case "help":
            case "5":
                mostrarAyuda();
                break;
            default:
                System.out.println(ConsoleColors.error("Comando no reconocido: " + comando));
                System.out.println(ConsoleColors.info("Escriba 'help' para ver los comandos disponibles"));
        }
    }

    /**
     * Inicia una nueva partida.
     */
    private void iniciarNuevaPartida() {
        try {
            limpiarConsola();
            System.out.println(ConsoleColors.BOLD + "\n=== NUEVA PARTIDA ===" + ConsoleColors.RESET);

            // Seleccionar o registrar jugadores
            String nombreJugador1 = seleccionarORegistrarJugador(1);
            if (nombreJugador1 == null) return;

            String nombreJugador2 = seleccionarORegistrarJugador(2);
            if (nombreJugador2 == null) return;

            if (nombreJugador1.equalsIgnoreCase(nombreJugador2)) {
                System.out.println(ConsoleColors.error("Los jugadores deben ser diferentes"));
                esperarEnter();
                return;
            }

            // Solicitar tamaño del tablero
            int tamano = solicitarTamanoTablero();
            if (tamano == -1) return;

            // Iniciar partida
            ResultadoOperacion resultado = controller.iniciarPartida(nombreJugador1, nombreJugador2, tamano);

            if (resultado == ResultadoOperacion.EXITO) {
                System.out.println(ConsoleColors.exito("¡Partida iniciada correctamente!"));
                mostrarInfoInicio();
                esperarEnter();
                bucleJuego();
            } else {
                System.out.println(ConsoleColors.error("No se pudo iniciar la partida: " + resultado));
                esperarEnter();
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.error("Error al iniciar partida: " + e.getMessage()));
            esperarEnter();
        }
    }

    /**
     * Selecciona o registra un jugador.
     * @param numero número de jugador
     * @return nombre del jugador o null si se cancela
     */
    private String seleccionarORegistrarJugador(int numero) {
        try {
            System.out.println(ConsoleColors.BRIGHT_CYAN + "\n--- Jugador " + numero + " ---" + ConsoleColors.RESET);
            List<Jugador> jugadores = controller.listarJugadores();

            if (!jugadores.isEmpty()) {
                System.out.println("\nJugadores registrados:");
                for (int i = 0; i < jugadores.size(); i++) {
                    System.out.println((i + 1) + ". " + jugadores.get(i));
                }
                System.out.println((jugadores.size() + 1) + ". Registrar nuevo jugador");
            } else {
                System.out.println(ConsoleColors.info("No hay jugadores registrados"));
            }

            System.out.print("\nIngrese el número o nombre del jugador: ");
            String entrada = scanner.nextLine().trim();

            // Intentar parsear como número
            try {
                int opcion = Integer.parseInt(entrada);
                if (opcion > 0 && opcion <= jugadores.size()) {
                    return jugadores.get(opcion - 1).getNombre();
                } else if (opcion == jugadores.size() + 1) {
                    return registrarNuevoJugador();
                }
            } catch (NumberFormatException e) {
                // Es un nombre
                Jugador jugador = controller.buscarJugador(entrada);
                if (jugador != null) {
                    return jugador.getNombre();
                } else {
                    System.out.print(ConsoleColors.info("Jugador no encontrado. ¿Desea registrarlo? (s/n): "));
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    if (respuesta.equals("s") || respuesta.equals("si")) {
                        ResultadoOperacion resultado = controller.registrarJugador(entrada);
                        if (resultado == ResultadoOperacion.EXITO) {
                            System.out.println(ConsoleColors.exito("Jugador registrado correctamente"));
                            return entrada;
                        } else {
                            System.out.println(ConsoleColors.error("No se pudo registrar el jugador"));
                            return null;
                        }
                    }
                }
            }

            System.out.println(ConsoleColors.error("Opción inválida"));
            return null;
        } catch (RepositoryException e) {
            System.out.println(ConsoleColors.error("Error al acceder a los jugadores: " + e.getMessage()));
            return null;
        }
    }

    /**
     * Registra un nuevo jugador.
     * @return nombre del jugador o null si se cancela
     */
    private String registrarNuevoJugador() {
        System.out.print("Ingrese el nombre del nuevo jugador: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println(ConsoleColors.error("El nombre no puede estar vacío"));
            return null;
        }

        ResultadoOperacion resultado = controller.registrarJugador(nombre);

        if (resultado == ResultadoOperacion.EXITO) {
            System.out.println(ConsoleColors.exito("Jugador registrado correctamente"));
            return nombre;
        } else if (resultado == ResultadoOperacion.JUGADOR_YA_EXISTE) {
            System.out.println(ConsoleColors.advertencia("El jugador ya existe"));
            return nombre;
        } else {
            System.out.println(ConsoleColors.error("No se pudo registrar el jugador"));
            return null;
        }
    }

    /**
     * Solicita el tamaño del tablero.
     * @return tamaño del tablero o -1 si es inválido
     */
    private int solicitarTamanoTablero() {
        while (true) {
            System.out.print("\nIngrese el tamaño del tablero (número par >= 4): ");
            String entrada = scanner.nextLine().trim();

            try {
                int tamano = Integer.parseInt(entrada);
                if (tamano >= 4 && tamano % 2 == 0) {
                    return tamano;
                } else {
                    System.out.println(ConsoleColors.error("El tamaño debe ser un número par mayor o igual a 4"));
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.error("Debe ingresar un número válido"));
            }
        }
    }

    /**
     * Muestra información del inicio de la partida.
     */
    private void mostrarInfoInicio() {
        Partida partida = controller.getPartidaActual();
        if (partida == null) return;

        System.out.println("\n" + ConsoleColors.BRIGHT_CYAN + "═══════════════════════════════════════" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD + "        INFORMACIÓN DE LA PARTIDA" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BRIGHT_CYAN + "═══════════════════════════════════════" + ConsoleColors.RESET);

        String colorJ1 = partida.getColorJugador1() == Color.NEGRO ?
                ConsoleColors.BRIGHT_BLACK + "●" : ConsoleColors.BRIGHT_WHITE + "○";
        String colorJ2 = partida.getColorJugador2() == Color.NEGRO ?
                ConsoleColors.BRIGHT_BLACK + "●" : ConsoleColors.BRIGHT_WHITE + "○";

        System.out.println(colorJ1 + ConsoleColors.RESET + " " + partida.getJugador1().getNombre() +
                " (" + partida.getColorJugador1().getNombre() + ")");
        System.out.println(colorJ2 + ConsoleColors.RESET + " " + partida.getJugador2().getNombre() +
                " (" + partida.getColorJugador2().getNombre() + ")");
        System.out.println("\nTablero: " + partida.getTablero().getTamano() + "x" + partida.getTablero().getTamano());
        System.out.println("Comienza: " + partida.getJugadorActual().getNombre());
        System.out.println(ConsoleColors.BRIGHT_CYAN + "═══════════════════════════════════════" + ConsoleColors.RESET);
    }

    /**
     * Bucle principal del juego.
     */
    private void bucleJuego() {
        while (true) {
            Partida partida = controller.getPartidaActual();

            if (partida == null || partida.isFinalizada()) {
                if (partida != null && partida.isFinalizada()) {
                    mostrarResultadoFinal();
                }
                break;
            }

            limpiarConsola();
            mostrarTablero();
            mostrarEstadisticas();
            mostrarTurnoActual();

            // Verificar si tiene movimientos válidos
            if (!controller.tieneMovimientosValidos(partida.getTurnoActual())) {
                System.out.println(ConsoleColors.advertencia(
                        "¡" + partida.getJugadorActual().getNombre() +
                                " no tiene movimientos válidos!"));
                System.out.println(ConsoleColors.info("Pasando turno..."));
                ResultadoOperacion resultado = controller.pasarTurno();

                if (resultado == ResultadoOperacion.JUEGO_FINALIZADO) {
                    mostrarTablero();
                    mostrarResultadoFinal();
                    break;
                }

                esperarEnter();
                continue;
            }

            mostrarMenuJuego();
            String comando = leerComando();

            boolean continuar = procesarComandoJuego(comando);
            if (!continuar) {
                break;
            }
        }

        esperarEnter();
    }

    /**
     * Muestra el tablero.
     */
    private void mostrarTablero() {
        Partida partida = controller.getPartidaActual();
        if (partida == null) return;

        Tablero tablero = partida.getTablero();
        int tamano = tablero.getTamano();

        System.out.println("\n" + ConsoleColors.BRIGHT_CYAN + "╔" + "═══".repeat(tamano) + "═╗" + ConsoleColors.RESET);

        // Encabezado de columnas
        System.out.print(ConsoleColors.BRIGHT_CYAN + "║" + ConsoleColors.RESET + "  ");
        for (int j = 0; j < tamano; j++) {
            System.out.print(ConsoleColors.YELLOW + String.format("%2d ", j) + ConsoleColors.RESET);
        }
        System.out.println(ConsoleColors.BRIGHT_CYAN + "║" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BRIGHT_CYAN + "╠" + "═══".repeat(tamano) + "═╣" + ConsoleColors.RESET);

        // Filas del tablero
        for (int i = 0; i < tamano; i++) {
            System.out.print(ConsoleColors.BRIGHT_CYAN + "║" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW + String.format("%2d", i) + ConsoleColors.RESET + " ");

            for (int j = 0; j < tamano; j++) {
                Ficha ficha = tablero.getFicha(i, j);
                if (ficha == null) {
                    System.out.print(ConsoleColors.BRIGHT_BLACK + "·" + ConsoleColors.RESET + "  ");
                } else if (ficha.getColor() == Color.NEGRO) {
                    System.out.print(ConsoleColors.BRIGHT_BLACK + ConsoleColors.BG_BRIGHT_WHITE + "●" + ConsoleColors.RESET + "  ");
                } else {
                    System.out.print(ConsoleColors.BG_BLACK + ConsoleColors.BRIGHT_WHITE + "○" + ConsoleColors.RESET + "  ");
                }
            }

            System.out.println(ConsoleColors.BRIGHT_CYAN + "║" + ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.BRIGHT_CYAN + "╚" + "═══".repeat(tamano) + "═╝" + ConsoleColors.RESET);
    }

    /**
     * Muestra las estadísticas de la partida.
     */
    private void mostrarEstadisticas() {
        Partida partida = controller.getPartidaActual();
        if (partida == null) return;

        int[] stats = partida.getEstadisticas();

        String simboloJ1 = partida.getColorJugador1() == Color.NEGRO ?
                ConsoleColors.BRIGHT_BLACK + "●" : ConsoleColors.BRIGHT_WHITE + "○";
        String simboloJ2 = partida.getColorJugador2() == Color.NEGRO ?
                ConsoleColors.BRIGHT_BLACK + "●" : ConsoleColors.BRIGHT_WHITE + "○";

        System.out.println("\n" + ConsoleColors.BOLD + "═══ MARCADOR ===" + ConsoleColors.RESET);
        System.out.println(simboloJ1 + ConsoleColors.RESET + " " +
                partida.getJugador1().getNombre() + ": " +
                ConsoleColors.BRIGHT_GREEN + stats[0] + ConsoleColors.RESET);
        System.out.println(simboloJ2 + ConsoleColors.RESET + " " +
                partida.getJugador2().getNombre() + ": " +
                ConsoleColors.BRIGHT_GREEN + stats[1] + ConsoleColors.RESET);
    }

    /**
     * Muestra el turno actual.
     */
    private void mostrarTurnoActual() {
        Partida partida = controller.getPartidaActual();
        if (partida == null) return;

        String simbolo = partida.getTurnoActual() == Color.NEGRO ?
                ConsoleColors.BRIGHT_BLACK + "●" : ConsoleColors.BRIGHT_WHITE + "○";

        System.out.println("\n" + ConsoleColors.BOLD + "Turno de: " + ConsoleColors.RESET +
                simbolo + ConsoleColors.RESET + " " +
                ConsoleColors.BRIGHT_YELLOW + partida.getJugadorActual().getNombre() + ConsoleColors.RESET);
    }

    /**
     * Muestra el menú durante el juego.
     */
    private void mostrarMenuJuego() {
        System.out.println("\n" + ConsoleColors.BOLD + "Opciones:" + ConsoleColors.RESET);
        System.out.println("  " + ConsoleColors.GREEN + "move <fila> <columna>" + ConsoleColors.RESET + " - Colocar ficha");
        System.out.println("  " + ConsoleColors.YELLOW + "pass" + ConsoleColors.RESET + "               - Pasar turno");
        System.out.println("  " + ConsoleColors.CYAN + "stats" + ConsoleColors.RESET + "              - Ver estadísticas");
        System.out.println("  " + ConsoleColors.PURPLE + "save <archivo>" + ConsoleColors.RESET + "    - Guardar partida");
        System.out.println("  " + ConsoleColors.BLUE + "board" + ConsoleColors.RESET + "              - Mostrar tablero");
        System.out.println("  " + ConsoleColors.RED + "exit" + ConsoleColors.RESET + "               - Salir del juego");
        System.out.print("\n" + ConsoleColors.BRIGHT_WHITE + "Comando: " + ConsoleColors.RESET);
    }

    /**
     * Procesa los comandos durante el juego.
     * @param comando comando ingresado
     * @return true si debe continuar el juego, false para salir
     */
    private boolean procesarComandoJuego(String comando) {
        String[] partes = comando.trim().split("\\s+");
        String cmd = partes[0].toLowerCase();

        try {
            switch (cmd) {
                case "move":
                case "m":
                    if (partes.length >= 3) {
                        realizarMovimiento(partes[1], partes[2]);
                    } else {
                        System.out.println(ConsoleColors.error("Formato: move <fila> <columna>"));
                        System.out.println(ConsoleColors.info("Ejemplo: move 2 3"));
                        esperarEnter();
                    }
                    break;

                case "pass":
                case "p":
                    ResultadoOperacion resultado = controller.pasarTurno();
                    if (resultado == ResultadoOperacion.JUEGO_FINALIZADO) {
                        return false;
                    }
                    System.out.println(ConsoleColors.info("Turno pasado"));
                    esperarEnter();
                    break;

                case "stats":
                case "s":
                    // Ya se muestran las estadísticas
                    esperarEnter();
                    break;

                case "save":
                    if (partes.length >= 2) {
                        guardarPartida(partes[1]);
                    } else {
                        guardarPartidaInteractivo();
                    }
                    esperarEnter();
                    break;

                case "board":
                case "b":
                    // El tablero se muestra automáticamente
                    esperarEnter();
                    break;

                case "exit":
                    if (confirmarSalida()) {
                        return false;
                    }
                    break;

                default:
                    System.out.println(ConsoleColors.error("Comando no reconocido: " + comando));
                    System.out.println(ConsoleColors.info("Escriba un comando válido"));
                    esperarEnter();
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.error("Error: " + e.getMessage()));
            esperarEnter();
        }

        return true;
    }

    /**
     * Realiza un movimiento en el tablero.
     * @param filaStr fila como string
     * @param colStr columna como string
     */
    private void realizarMovimiento(String filaStr, String colStr) {
        try {
            int fila = Integer.parseInt(filaStr);
            int columna = Integer.parseInt(colStr);

            ResultadoOperacion resultado = controller.realizarMovimiento(fila, columna);

            switch (resultado) {
                case EXITO:
                    System.out.println(ConsoleColors.exito("¡Movimiento realizado!"));
                    break;
                case TURNO_PASADO:
                    System.out.println(ConsoleColors.exito("¡Movimiento realizado!"));
                    System.out.println(ConsoleColors.advertencia("El siguiente jugador no tiene movimientos válidos. Turno pasado."));
                    break;
                case JUEGO_FINALIZADO:
                    System.out.println(ConsoleColors.exito("¡Movimiento realizado!"));
                    System.out.println(ConsoleColors.info("¡El juego ha finalizado!"));
                    break;
                default:
                    System.out.println(ConsoleColors.error("Error: " + resultado));
            }

            esperarEnter();
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.error("Las coordenadas deben ser números"));
            esperarEnter();
        } catch (MovimientoInvalidoException e) {
            System.out.println(ConsoleColors.error("Movimiento inválido: " + e.getMessage()));
            esperarEnter();
        }
    }

    /**
     * Muestra el resultado final de la partida.
     */
    private void mostrarResultadoFinal() {
        Partida partida = controller.getPartidaActual();
        if (partida == null) return;

        int[] stats = partida.getEstadisticas();

        System.out.println("\n" + ConsoleColors.BRIGHT_CYAN + "╔════════════════════════════════════════╗");
        System.out.println("║          JUEGO FINALIZADO              ║");
        System.out.println("╚════════════════════════════════════════╝" + ConsoleColors.RESET);

        System.out.println("\n" + ConsoleColors.BOLD + "Resultado final:" + ConsoleColors.RESET);
        System.out.println(partida.getJugador1().getNombre() + ": " +
                ConsoleColors.BRIGHT_GREEN + stats[0] + ConsoleColors.RESET + " fichas");
        System.out.println(partida.getJugador2().getNombre() + ": " +
                ConsoleColors.BRIGHT_GREEN + stats[1] + ConsoleColors.RESET + " fichas");

        if (partida.getGanador() != null) {
            System.out.println("\n" + ConsoleColors.BRIGHT_YELLOW + "🏆 ¡GANADOR: " +
                    partida.getGanador().getNombre() + "! 🏆" + ConsoleColors.RESET);
        } else {
            System.out.println("\n" + ConsoleColors.BRIGHT_BLUE + "¡EMPATE!" + ConsoleColors.RESET);
        }
    }

    /**
     * Guarda la partida actual.
     * @param rutaArchivo ruta del archivo
     */
    private void guardarPartida(String rutaArchivo) {
        if (!rutaArchivo.endsWith(".dat")) {
            rutaArchivo += ".dat";
        }

        if (controller.existePartida(rutaArchivo)) {
            System.out.print(ConsoleColors.advertencia("El archivo ya existe. ¿Desea sobrescribirlo? (s/n): "));
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s") && !respuesta.equals("si")) {
                System.out.println(ConsoleColors.info("Guardado cancelado"));
                return;
            }
        }

        ResultadoOperacion resultado = controller.guardarPartida(rutaArchivo);

        if (resultado == ResultadoOperacion.EXITO) {
            System.out.println(ConsoleColors.exito("Partida guardada correctamente en: " + rutaArchivo));
        } else {
            System.out.println(ConsoleColors.error("No se pudo guardar la partida"));
        }
    }

    /**
     * Guarda la partida de forma interactiva.
     */
    private void guardarPartidaInteractivo() {
        System.out.print("Ingrese el nombre del archivo (sin extensión): ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println(ConsoleColors.error("Nombre de archivo inválido"));
            return;
        }

        guardarPartida("data/partidas/" + nombre);
    }

    /**
     * Carga una partida.
     * @param rutaArchivo ruta del archivo
     */
    private void cargarPartida(String rutaArchivo) {
        if (!rutaArchivo.endsWith(".dat")) {
            rutaArchivo += ".dat";
        }

        ResultadoOperacion resultado = controller.cargarPartida(rutaArchivo);

        if (resultado == ResultadoOperacion.EXITO) {
            System.out.println(ConsoleColors.exito("Partida cargada correctamente"));
            mostrarInfoInicio();
            esperarEnter();
            bucleJuego();
        } else {
            System.out.println(ConsoleColors.error("No se pudo cargar la partida"));
            esperarEnter();
        }
    }

    /**
     * Carga una partida de forma interactiva.
     */
    private void cargarPartidaInteractivo() {
        System.out.print("Ingrese la ruta del archivo: ");
        String ruta = scanner.nextLine().trim();

        if (ruta.isEmpty()) {
            System.out.println(ConsoleColors.error("Ruta inválida"));
            esperarEnter();
            return;
        }

        cargarPartida(ruta);
    }

    /**
     * Muestra los jugadores registrados.
     */
    private void mostrarJugadores() {
        try {
            limpiarConsola();
            System.out.println(ConsoleColors.BOLD + "\n=== JUGADORES REGISTRADOS ===" + ConsoleColors.RESET);

            List<Jugador> jugadores = controller.listarJugadores();

            if (jugadores.isEmpty()) {
                System.out.println(ConsoleColors.info("No hay jugadores registrados"));
            } else {
                System.out.println(String.format("\n%-20s %10s %10s %10s %10s",
                        "Nombre", "Ganadas", "Perdidas", "Empatadas", "Total"));
                System.out.println("─".repeat(62));

                for (Jugador jugador : jugadores) {
                    System.out.println(String.format("%-20s %10d %10d %10d %10d",
                            jugador.getNombre(),
                            jugador.getPartidasGanadas(),
                            jugador.getPartidasPerdidas(),
                            jugador.getPartidasEmpatadas(),
                            jugador.getTotalPartidas()));
                }
            }

            esperarEnter();
        } catch (RepositoryException e) {
            System.out.println(ConsoleColors.error("Error al listar jugadores: " + e.getMessage()));
            esperarEnter();
        }
    }

    /**
     * Muestra la ayuda.
     */
    private void mostrarAyuda() {
        limpiarConsola();
        System.out.println(ConsoleColors.BOLD + "\n=== AYUDA ===" + ConsoleColors.RESET);
        System.out.println("\nReverse Dots es un juego de estrategia para dos jugadores.");
        System.out.println("El objetivo es tener más fichas de tu color al final del juego.");
        System.out.println("\nReglas básicas:");
        System.out.println("- Se juega por turnos alternos");
        System.out.println("- Debes colocar una ficha que encierre fichas del oponente");
        System.out.println("- Las fichas encerradas se voltean a tu color");
        System.out.println("- El juego termina cuando el tablero está lleno o no hay movimientos");
        System.out.println("\nComandos del menú principal:");
        System.out.println("  new              - Iniciar nueva partida");
        System.out.println("  load <archivo>   - Cargar partida guardada");
        System.out.println("  players          - Ver jugadores registrados");
        System.out.println("  exit             - Salir del juego");
        System.out.println("\nComandos durante el juego:");
        System.out.println("  move <fila> <col> - Colocar ficha (ej: move 2 3)");
        System.out.println("  pass              - Pasar turno");
        System.out.println("  stats             - Ver estadísticas");
        System.out.println("  save <archivo>    - Guardar partida");
        System.out.println("  board             - Mostrar tablero");
        System.out.println("  exit              - Salir del juego");

        esperarEnter();
    }

    /**
     * Confirma la salida del juego.
     * @return true si confirma, false en caso contrario
     */
    private boolean confirmarSalida() {
        System.out.print(ConsoleColors.advertencia("¿Está seguro que desea salir? (s/n): "));
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si");
    }

    /**
     * Sale del juego.
     */
    private void salir() {
        if (confirmarSalida()) {
            System.out.println(ConsoleColors.exito("¡Gracias por jugar Reverse Dots!"));
            ejecutando = false;
        }
    }

    /**
     * Lee un comando del usuario.
     * @return comando ingresado
     */
    private String leerComando() {
        return scanner.nextLine().trim();
    }

    /**
     * Espera a que el usuario presione Enter.
     */
    private void esperarEnter() {
        System.out.print("\n" + ConsoleColors.BRIGHT_BLACK + "Presione Enter para continuar..." + ConsoleColors.RESET);
        scanner.nextLine();
    }

    /**
     * Limpia la consola (simulado).
     */
    private void limpiarConsola() {
        // En Windows y Unix
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Método main para ejecutar la aplicación CLI.
     */
    public static void main(String[] args) {
        ConsoleCLI cli = new ConsoleCLI();
        cli.iniciar();
    }
}