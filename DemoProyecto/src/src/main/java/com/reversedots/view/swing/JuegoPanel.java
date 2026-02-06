package src.main.java.com.reversedots.view.swing;

import src.main.java.com.reversedots.controller.JuegoController;
import src.main.java.com.reversedots.controller.JuegoController.Posicion;
import src.main.java.com.reversedots.controller.ResultadoOperacion;
import src.main.java.com.reversedots.exception.MovimientoInvalidoException;
import src.main.java.com.reversedots.model.Ficha;
import src.main.java.com.reversedots.model.Partida;
import src.main.java.com.reversedots.model.Tablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Panel principal del juego.
 */
public class JuegoPanel extends JPanel {

    private final SwingGUI parent;
    private final JuegoController controller;

    // Componentes
    private TableroPanel tableroPanel;
    private JPanel infoPanel;
    private JLabel lblJugador1;
    private JLabel lblJugador2;
    private JLabel lblFichasJ1;
    private JLabel lblFichasJ2;
    private JLabel lblTurno;
    private JLabel lblMensaje;

    /**
     * Constructor del panel de juego.
     */
    public JuegoPanel(SwingGUI parent, JuegoController controller) {
        this.parent = parent;
        this.controller = controller;

        inicializarComponentes();
    }

    /**
     * Inicializa los componentes del panel.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Panel superior (menú)
        JPanel menuPanel = crearMenuPanel();
        add(menuPanel, BorderLayout.NORTH);

        // Panel central (tablero e información)
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(new Color(240, 240, 240));

        // Tablero
        tableroPanel = new TableroPanel();
        centralPanel.add(tableroPanel, BorderLayout.CENTER);

        // Panel de información
        infoPanel = crearInfoPanel();
        centralPanel.add(infoPanel, BorderLayout.EAST);

        add(centralPanel, BorderLayout.CENTER);

        // Panel inferior (mensajes)
        JPanel mensajePanel = crearMensajePanel();
        add(mensajePanel, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel del menú.
     */
    private JPanel crearMenuPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton btnNuevaPartida = crearBotonMenu("Nueva Partida", new Color(26, 35, 172));
        btnNuevaPartida.addActionListener(e -> nuevaPartida());

        JButton btnGuardar = crearBotonMenu("Guardar", new Color(52, 152, 219));
        btnGuardar.addActionListener(e -> guardarPartida());

        JButton btnCargar = crearBotonMenu("Cargar", new Color(155, 89, 182));
        btnCargar.addActionListener(e -> cargarPartida());

        JButton btnPasarTurno = crearBotonMenu("Pasar Turno", new Color(241, 196, 15));
        btnPasarTurno.addActionListener(e -> pasarTurno());

        JButton btnSalir = crearBotonMenu("Menú Principal", new Color(231, 76, 60));
        btnSalir.addActionListener(e -> volverAlMenu());

        panel.add(btnNuevaPartida);
        panel.add(btnGuardar);
        panel.add(btnCargar);
        panel.add(btnPasarTurno);
        panel.add(btnSalir);

        return panel;
    }

    /**
     * Crea un botón del menú.
     */
    private JButton crearBotonMenu(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                boton.setBackground(color);
            }
        });

        return boton;
    }

    /**
     * Crea el panel de información.
     */
    private JPanel crearInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2)
        ));
        panel.setPreferredSize(new Dimension(250, 0));

        // Título
        JLabel titulo = new JLabel("INFORMACIÓN");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);

        panel.add(Box.createVerticalStrut(20));

        // Jugador 1
        JPanel j1Panel = new JPanel();
        j1Panel.setLayout(new BoxLayout(j1Panel, BoxLayout.Y_AXIS));
        j1Panel.setBackground(Color.WHITE);
        j1Panel.setBorder(BorderFactory.createTitledBorder("Jugador 1"));

        lblJugador1 = new JLabel("Jugador 1");
        lblJugador1.setFont(new Font("Arial", Font.BOLD, 14));
        lblFichasJ1 = new JLabel("Fichas: 0");
        lblFichasJ1.setFont(new Font("Arial", Font.PLAIN, 12));

        j1Panel.add(lblJugador1);
        j1Panel.add(lblFichasJ1);
        panel.add(j1Panel);

        panel.add(Box.createVerticalStrut(10));

        // Jugador 2
        JPanel j2Panel = new JPanel();
        j2Panel.setLayout(new BoxLayout(j2Panel, BoxLayout.Y_AXIS));
        j2Panel.setBackground(Color.WHITE);
        j2Panel.setBorder(BorderFactory.createTitledBorder("Jugador 2"));

        lblJugador2 = new JLabel("Jugador 2");
        lblJugador2.setFont(new Font("Arial", Font.BOLD, 14));
        lblFichasJ2 = new JLabel("Fichas: 0");
        lblFichasJ2.setFont(new Font("Arial", Font.PLAIN, 12));

        j2Panel.add(lblJugador2);
        j2Panel.add(lblFichasJ2);
        panel.add(j2Panel);

        panel.add(Box.createVerticalStrut(20));

        // Turno actual
        JPanel turnoPanel = new JPanel();
        turnoPanel.setLayout(new BoxLayout(turnoPanel, BoxLayout.Y_AXIS));
        turnoPanel.setBackground(new Color(255, 243, 224));
        turnoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(241, 196, 15), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblTurnoTitulo = new JLabel("TURNO ACTUAL");
        lblTurnoTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTurnoTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTurno = new JLabel("Jugador");
        lblTurno.setFont(new Font("Arial", Font.BOLD, 16));
        lblTurno.setAlignmentX(Component.CENTER_ALIGNMENT);

        turnoPanel.add(lblTurnoTitulo);
        turnoPanel.add(Box.createVerticalStrut(5));
        turnoPanel.add(lblTurno);
        panel.add(turnoPanel);

        panel.add(Box.createVerticalStrut(20));

        // Botón de ayuda
        JButton btnAyuda = new JButton("Ver Movimientos Válidos");
        btnAyuda.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAyuda.addActionListener(e -> mostrarMovimientosValidos());
        panel.add(btnAyuda);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Crea el panel de mensajes.
     */
    private JPanel crearMensajePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblMensaje = new JLabel("Seleccione una posición en el tablero");
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblMensaje);

        return panel;
    }

    /**
     * Actualiza la vista con el estado actual del juego.
     */
    public void actualizarVista() {
        Partida partida = controller.getPartidaActual();

        if (partida == null) {
            mostrarMensaje("No hay partida activa", Color.RED);
            return;
        }

        // Actualizar información de jugadores
        lblJugador1.setText(partida.getJugador1().getNombre());
        lblJugador2.setText(partida.getJugador2().getNombre());

        // Actualizar colores
        String colorJ1 = partida.getColorJugador1() == src.main.java.com.reversedots.model.Color.NEGRO ? " (Negro ●)" : " (Blanco ○)";
        String colorJ2 = partida.getColorJugador2() == src.main.java.com.reversedots.model.Color.NEGRO ? " (Negro ●)" : " (Blanco ○)";

        lblJugador1.setText(partida.getJugador1().getNombre() + colorJ1);
        lblJugador2.setText(partida.getJugador2().getNombre() + colorJ2);

        // Actualizar fichas
        int[] stats = partida.getEstadisticas();
        lblFichasJ1.setText("Fichas: " + stats[0]);
        lblFichasJ2.setText("Fichas: " + stats[1]);

        // Actualizar turno
        lblTurno.setText(partida.getJugadorActual().getNombre());

        // Actualizar tablero
        tableroPanel.actualizarTablero();

        // Verificar si el juego terminó
        if (partida.isFinalizada()) {
            mostrarResultadoFinal();
        } else if (!controller.tieneMovimientosValidos(partida.getTurnoActual())) {
            mostrarMensaje("¡" + partida.getJugadorActual().getNombre() +
                    " no tiene movimientos válidos!", new Color(255, 165, 0));
        } else {
            mostrarMensaje("Turno de " + partida.getJugadorActual().getNombre(),
                    new Color(52, 152, 219));
        }
    }

    /**
     * Muestra un mensaje en el panel de mensajes.
     */
    private void mostrarMensaje(String mensaje, Color color) {
        lblMensaje.setText(mensaje);
        lblMensaje.setForeground(color);
    }

    /**
     * Realiza un movimiento en el tablero.
     */
    private void realizarMovimiento(int fila, int columna) {
        try {
            ResultadoOperacion resultado = controller.realizarMovimiento(fila, columna);

            switch (resultado) {
                case EXITO:
                    mostrarMensaje("¡Movimiento realizado!", new Color(46, 204, 113));
                    break;
                case TURNO_PASADO:
                    mostrarMensaje("¡Movimiento realizado! El siguiente jugador no tiene movimientos.",
                            new Color(255, 165, 0));
                    break;
                case JUEGO_FINALIZADO:
                    mostrarMensaje("¡El juego ha finalizado!", new Color(155, 89, 182));
                    break;
                case PARTIDA_NO_INICIADA:
                    mostrarMensaje("No hay partida activa", Color.RED);
                    return;
                default:
                    mostrarMensaje("Error: " + resultado, Color.RED);
                    return;
            }

            actualizarVista();

        } catch (MovimientoInvalidoException e) {
            mostrarMensaje("Movimiento inválido: " + e.getMessage(), Color.RED);
            JOptionPane.showMessageDialog(
                    parent,
                    e.getMessage(),
                    "Movimiento Inválido",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Pasa el turno al siguiente jugador.
     */
    private void pasarTurno() {
        Partida partida = controller.getPartidaActual();
        if (partida == null) {
            JOptionPane.showMessageDialog(
                    parent,
                    "No hay partida activa",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                parent,
                "¿Está seguro que desea pasar el turno?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            ResultadoOperacion resultado = controller.pasarTurno();

            if (resultado == ResultadoOperacion.JUEGO_FINALIZADO) {
                actualizarVista();
                mostrarResultadoFinal();
            } else {
                actualizarVista();
                mostrarMensaje("Turno pasado", new Color(255, 165, 0));
            }
        }
    }

    /**
     * Muestra los movimientos válidos.
     */
    private void mostrarMovimientosValidos() {
        Partida partida = controller.getPartidaActual();
        if (partida == null) return;

        List<Posicion> movimientos = controller.obtenerMovimientosValidos(partida.getTurnoActual());

        if (movimientos.isEmpty()) {
            JOptionPane.showMessageDialog(
                    parent,
                    "No hay movimientos válidos disponibles",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            tableroPanel.resaltarMovimientos(movimientos);
        }
    }

    /**
     * Guarda la partida actual.
     */
    private void guardarPartida() {
        if (controller.getPartidaActual() == null) {
            JOptionPane.showMessageDialog(
                    parent,
                    "No hay partida activa para guardar",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JFileChooser fileChooser = new JFileChooser("data/partidas");
        fileChooser.setDialogTitle("Guardar Partida");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de partida (*.dat)", "dat"));

        int resultado = fileChooser.showSaveDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

            if (!rutaArchivo.endsWith(".dat")) {
                rutaArchivo += ".dat";
            }

            // Verificar si existe
            if (controller.existePartida(rutaArchivo)) {
                int opcion = JOptionPane.showConfirmDialog(
                        parent,
                        "El archivo ya existe. ¿Desea sobrescribirlo?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            ResultadoOperacion res = controller.guardarPartida(rutaArchivo);

            if (res == ResultadoOperacion.EXITO) {
                JOptionPane.showMessageDialog(
                        parent,
                        "Partida guardada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                mostrarMensaje("Partida guardada", new Color(46, 204, 113));
            } else {
                JOptionPane.showMessageDialog(
                        parent,
                        "No se pudo guardar la partida",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Carga una partida guardada.
     */
    private void cargarPartida() {
        JFileChooser fileChooser = new JFileChooser("data/partidas");
        fileChooser.setDialogTitle("Cargar Partida");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de partida (*.dat)", "dat"));

        int resultado = fileChooser.showOpenDialog(parent);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            ResultadoOperacion res = controller.cargarPartida(rutaArchivo);

            if (res == ResultadoOperacion.EXITO) {
                JOptionPane.showMessageDialog(
                        parent,
                        "Partida cargada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                actualizarVista();
            } else {
                JOptionPane.showMessageDialog(
                        parent,
                        "No se pudo cargar la partida",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Inicia una nueva partida.
     */
    private void nuevaPartida() {
        int opcion = JOptionPane.showConfirmDialog(
                parent,
                "¿Desea iniciar una nueva partida? La partida actual se perderá.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            NuevaPartidaDialog dialog = new NuevaPartidaDialog(parent, controller);
            dialog.setVisible(true);

            if (dialog.isPartidaIniciada()) {
                actualizarVista();
            }
        }
    }

    /**
     * Vuelve al menú principal.
     */
    private void volverAlMenu() {
        int opcion = JOptionPane.showConfirmDialog(
                parent,
                "¿Desea volver al menú principal? La partida actual se perderá si no la guarda.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            parent.mostrarMenu();
        }
    }

    /**
     * Muestra el resultado final del juego.
     */
    private void mostrarResultadoFinal() {
        Partida partida = controller.getPartidaActual();
        if (partida == null || !partida.isFinalizada()) return;

        int[] stats = partida.getEstadisticas();

        String mensaje = String.format(
                "¡JUEGO FINALIZADO!\n\n" +
                        "%s: %d fichas\n" +
                        "%s: %d fichas\n\n",
                partida.getJugador1().getNombre(), stats[0],
                partida.getJugador2().getNombre(), stats[1]
        );

        if (partida.getGanador() != null) {
            mensaje += "🏆 GANADOR: " + partida.getGanador().getNombre() + " 🏆";
        } else {
            mensaje += "¡EMPATE!";
        }

        JOptionPane.showMessageDialog(
                parent,
                mensaje,
                "Resultado Final",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Panel interno para el tablero.
     */
    private class TableroPanel extends JPanel {

        private CeldaPanel[][] celdas;
        private List<Posicion> movimientosResaltados;

        public TableroPanel() {
            setBackground(new Color(44, 62, 80));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        public void actualizarTablero() {
            removeAll();

            Partida partida = controller.getPartidaActual();
            if (partida == null) {
                revalidate();
                repaint();
                return;
            }

            Tablero tablero = partida.getTablero();
            int tamano = tablero.getTamano();

            setLayout(new GridLayout(tamano, tamano, 2, 2));

            celdas = new CeldaPanel[tamano][tamano];

            for (int i = 0; i < tamano; i++) {
                for (int j = 0; j < tamano; j++) {
                    final int fila = i;
                    final int columna = j;

                    CeldaPanel celda = new CeldaPanel(fila, columna, tablero.getFicha(i, j));
                    celda.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            realizarMovimiento(fila, columna);
                        }
                    });

                    celdas[i][j] = celda;
                    add(celda);
                }
            }

            revalidate();
            repaint();
        }

        public void resaltarMovimientos(List<Posicion> movimientos) {
            this.movimientosResaltados = movimientos;

            if (celdas != null) {
                for (int i = 0; i < celdas.length; i++) {
                    for (int j = 0; j < celdas[i].length; j++) {
                        celdas[i][j].setResaltado(false);
                    }
                }

                for (Posicion pos : movimientos) {
                    if (pos.fila < celdas.length && pos.columna < celdas[0].length) {
                        celdas[pos.fila][pos.columna].setResaltado(true);
                    }
                }
            }
        }
    }

    /**
     * Panel para cada celda del tablero.
     */
    private class CeldaPanel extends JPanel {

        private final int fila;
        private final int columna;
        private Ficha ficha;
        private boolean resaltado;

        public CeldaPanel(int fila, int columna, Ficha ficha) {
            this.fila = fila;
            this.columna = columna;
            this.ficha = ficha;
            this.resaltado = false;

            setPreferredSize(new Dimension(60, 60));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (ficha == null) {
                        setBackground(new Color(100, 120, 140));
                        repaint();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    repaint();
                }
            });
        }

        public void setResaltado(boolean resaltado) {
            this.resaltado = resaltado;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Fondo de la celda
            if (resaltado) {
                g2d.setColor(new Color(241, 196, 15, 100));
                g2d.fillRect(0, 0, width, height);
            } else {
                g2d.setColor(new Color(39, 174, 96));
                g2d.fillRect(0, 0, width, height);
            }

            // Borde
            g2d.setColor(new Color(44, 62, 80));
            g2d.drawRect(0, 0, width - 1, height - 1);

            // Dibujar ficha
            if (ficha != null) {
                int margin = 5;
                int diameter = Math.min(width, height) - 2 * margin;
                int x = (width - diameter) / 2;
                int y = (height - diameter) / 2;

                if (ficha.getColor() == src.main.java.com.reversedots.model.Color.NEGRO) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillOval(x, y, diameter, diameter);
                    g2d.setColor(new Color(50, 50, 50));
                    g2d.drawOval(x, y, diameter, diameter);
                } else {
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(x, y, diameter, diameter);
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.drawOval(x, y, diameter, diameter);
                }
            }

            // Indicador de movimiento válido
            if (resaltado && ficha == null) {
                g2d.setColor(new Color(241, 196, 15));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(2, 2, width - 5, height - 5);
            }
        }
    }
}