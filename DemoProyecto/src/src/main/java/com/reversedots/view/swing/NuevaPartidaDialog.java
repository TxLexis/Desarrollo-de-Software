package src.main.java.com.reversedots.view.swing;

import src.main.java.com.reversedots.controller.JuegoController;
import src.main.java.com.reversedots.controller.ResultadoOperacion;
import src.main.java.com.reversedots.exception.RepositoryException;
import src.main.java.com.reversedots.model.Jugador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Diálogo para crear una nueva partida.
 */
public class NuevaPartidaDialog extends JDialog {

    private final JuegoController controller;
    private boolean partidaIniciada = false;

    private JComboBox<String> comboJugador1;
    private JComboBox<String> comboJugador2;
    private JSpinner spinnerTamano;

    /**
     * Constructor del diálogo.
     */
    public NuevaPartidaDialog(JFrame parent, JuegoController controller) {
        super(parent, "Nueva Partida", true);
        this.controller = controller;

        inicializarComponentes();
        cargarJugadores();

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    /**
     * Inicializa los componentes del diálogo.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Configurar Nueva Partida");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titulo, gbc);

        // Jugador 1
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel lblJugador1 = new JLabel("Jugador 1:");
        lblJugador1.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(lblJugador1, gbc);

        gbc.gridx = 1;
        comboJugador1 = new JComboBox<>();
        comboJugador1.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(comboJugador1, gbc);

        // Botón nuevo jugador 1
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnNuevoJ1 = new JButton("Registrar Nuevo Jugador 1");
        btnNuevoJ1.addActionListener(e -> registrarNuevoJugador(comboJugador1));
        mainPanel.add(btnNuevoJ1, gbc);

        // Jugador 2
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel lblJugador2 = new JLabel("Jugador 2:");
        lblJugador2.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(lblJugador2, gbc);

        gbc.gridx = 1;
        comboJugador2 = new JComboBox<>();
        comboJugador2.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(comboJugador2, gbc);

        // Botón nuevo jugador 2
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnNuevoJ2 = new JButton("Registrar Nuevo Jugador 2");
        btnNuevoJ2.addActionListener(e -> registrarNuevoJugador(comboJugador2));
        mainPanel.add(btnNuevoJ2, gbc);

        // Tamaño del tablero
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel lblTamano = new JLabel("Tamaño del tablero:");
        lblTamano.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(lblTamano, gbc);

        gbc.gridx = 1;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(8, 4, 20, 2);
        spinnerTamano = new JSpinner(spinnerModel);
        spinnerTamano.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(spinnerTamano, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setBackground(new Color(240, 240, 240));
        botonesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnIniciar = new JButton("Iniciar Partida");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciar.setBackground(new Color(46, 204, 113));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.addActionListener(e -> iniciarPartida());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());

        botonesPanel.add(btnIniciar);
        botonesPanel.add(btnCancelar);

        add(botonesPanel, BorderLayout.SOUTH);
    }

    /**
     * Carga los jugadores en los combobox.
     */
    private void cargarJugadores() {
        try {
            List<Jugador> jugadores = controller.listarJugadores();

            comboJugador1.removeAllItems();
            comboJugador2.removeAllItems();

            for (Jugador jugador : jugadores) {
                comboJugador1.addItem(jugador.getNombre());
                comboJugador2.addItem(jugador.getNombre());
            }

            if (jugadores.size() >= 2) {
                comboJugador2.setSelectedIndex(1);
            }

        } catch (RepositoryException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar jugadores: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Registra un nuevo jugador.
     */
    private void registrarNuevoJugador(JComboBox<String> combo) {
        String nombre = JOptionPane.showInputDialog(
                this,
                "Ingrese el nombre del nuevo jugador:",
                "Registrar Jugador",
                JOptionPane.PLAIN_MESSAGE
        );

        if (nombre != null && !nombre.trim().isEmpty()) {
            ResultadoOperacion resultado = controller.registrarJugador(nombre.trim());

            if (resultado == ResultadoOperacion.EXITO) {
                JOptionPane.showMessageDialog(
                        this,
                        "Jugador registrado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarJugadores();
                combo.setSelectedItem(nombre.trim());
            } else if (resultado == ResultadoOperacion.JUGADOR_YA_EXISTE) {
                JOptionPane.showMessageDialog(
                        this,
                        "El jugador ya existe",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
                combo.setSelectedItem(nombre.trim());
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo registrar el jugador",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Inicia la partida.
     */
    private void iniciarPartida() {
        String jugador1 = (String) comboJugador1.getSelectedItem();
        String jugador2 = (String) comboJugador2.getSelectedItem();
        int tamano = (Integer) spinnerTamano.getValue();

        if (jugador1 == null || jugador2 == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar ambos jugadores",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (jugador1.equals(jugador2)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Los jugadores deben ser diferentes",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        ResultadoOperacion resultado = controller.iniciarPartida(jugador1, jugador2, tamano);

        if (resultado == ResultadoOperacion.EXITO) {
            partidaIniciada = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo iniciar la partida: " + resultado,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Verifica si la partida fue iniciada.
     */
    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }
}
