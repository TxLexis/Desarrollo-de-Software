package src.main.java.com.reversedots.view.swing;


import src.main.java.com.reversedots.controller.JuegoController;
import src.main.java.com.reversedots.controller.ResultadoOperacion;
import src.main.java.com.reversedots.exception.RepositoryException;
import src.main.java.com.reversedots.model.Jugador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel del menú principal.
 */
public class MenuPrincipalPanel extends JPanel {

    private final SwingGUI parent;
    private final JuegoController controller;

    /**
     * Constructor del panel del menú principal.
     */
    public MenuPrincipalPanel(SwingGUI parent, JuegoController controller) {
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

        // Panel de título
        JPanel tituloPanel = crearPanelTitulo();
        add(tituloPanel, BorderLayout.NORTH);

        // Panel de botones
        JPanel botonesPanel = crearPanelBotones();
        add(botonesPanel, BorderLayout.CENTER);

        // Panel de información
        JPanel infoPanel = crearPanelInfo();
        add(infoPanel, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel del título.
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JLabel titulo = new JLabel("REVERSE DOTS");
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Juego de Estrategia para Dos Jugadores");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitulo.setForeground(new Color(236, 240, 241));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitulo);

        return panel;
    }

    /**
     * Crea el panel de botones.
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension buttonSize = new Dimension(300, 50);

        // Botón Nueva Partida
        JButton btnNuevaPartida = crearBoton("Nueva Partida", new Color(46, 204, 113), buttonSize);
        btnNuevaPartida.addActionListener(e -> nuevaPartida());
        panel.add(btnNuevaPartida, gbc);

        // Botón Cargar Partida
        gbc.gridy++;
        JButton btnCargarPartida = crearBoton("Cargar Partida", new Color(52, 152, 219), buttonSize);
        btnCargarPartida.addActionListener(e -> cargarPartida());
        panel.add(btnCargarPartida, gbc);

        // Botón Ver Jugadores
        gbc.gridy++;
        JButton btnVerJugadores = crearBoton("Ver Jugadores", new Color(155, 89, 182), buttonSize);
        btnVerJugadores.addActionListener(e -> verJugadores());
        panel.add(btnVerJugadores, gbc);

        // Botón Salir
        gbc.gridy++;
        JButton btnSalir = crearBoton("Salir", new Color(231, 76, 60), buttonSize);
        btnSalir.addActionListener(e -> salir());
        panel.add(btnSalir, gbc);

        return panel;
    }

    /**
     * Crea el panel de información.
     */
    private JPanel crearPanelInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel info = new JLabel("Versión 1.0 - Proyecto POO");
        info.setFont(new Font("Arial", Font.PLAIN, 12));
        info.setForeground(new Color(189, 195, 199));

        panel.add(info);

        return panel;
    }

    /**
     * Crea un botón con estilo personalizado.
     */
    private JButton crearBoton(String texto, Color color, Dimension size) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(size);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });

        return boton;
    }

    /**
     * Inicia una nueva partida.
     */
    private void nuevaPartida() {
        NuevaPartidaDialog dialog = new NuevaPartidaDialog(parent, controller);
        dialog.setVisible(true);

        if (dialog.isPartidaIniciada()) {
            parent.mostrarJuego();
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
                parent.mostrarJuego();
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
     * Muestra los jugadores registrados.
     */
    private void verJugadores() {
        try {
            List<Jugador> jugadores = controller.listarJugadores();

            if (jugadores.isEmpty()) {
                JOptionPane.showMessageDialog(
                        parent,
                        "No hay jugadores registrados",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Crear tabla
            String[] columnas = {"Nombre", "Ganadas", "Perdidas", "Empatadas", "Total"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Jugador jugador : jugadores) {
                model.addRow(new Object[]{
                        jugador.getNombre(),
                        jugador.getPartidasGanadas(),
                        jugador.getPartidasPerdidas(),
                        jugador.getPartidasEmpatadas(),
                        jugador.getTotalPartidas()
                });
            }

            JTable table = new JTable(model);
            table.setFont(new Font("Arial", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
            table.setRowHeight(25);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(600, 300));

            JOptionPane.showMessageDialog(
                    parent,
                    scrollPane,
                    "Jugadores Registrados",
                    JOptionPane.PLAIN_MESSAGE
            );

        } catch (RepositoryException e) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Error al cargar los jugadores: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Sale de la aplicación.
     */
    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(
                parent,
                "¿Está seguro que desea salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}