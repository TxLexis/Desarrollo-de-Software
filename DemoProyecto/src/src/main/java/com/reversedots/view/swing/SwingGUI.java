package src.main.java.com.reversedots.view.swing;


import src.main.java.com.reversedots.controller.JuegoController;
import src.main.java.com.reversedots.repository.JugadorRepository;
import src.main.java.com.reversedots.repository.PartidaRepository;
import src.main.java.com.reversedots.repository.JugadorRepositoryImpl;
import src.main.java.com.reversedots.repository.PartidaRepositoryImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Clase principal de la interfaz gráfica Swing.
 */
public class  SwingGUI extends JFrame {

    private final JuegoController controller;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Paneles
    private MenuPrincipalPanel menuPrincipalPanel;
    private JuegoPanel juegoPanel;

    /**
     * Constructor de la interfaz Swing.
     */
    public SwingGUI() {
        JugadorRepository jugadorRepo = new JugadorRepositoryImpl();
        PartidaRepository partidaRepo = new PartidaRepositoryImpl();
        this.controller = new JuegoController(jugadorRepo, partidaRepo);

        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Inicializa los componentes de la interfaz.
     */
    private void inicializarComponentes() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear paneles
        menuPrincipalPanel = new MenuPrincipalPanel(this, controller);
        juegoPanel = new JuegoPanel(this, controller);

        // Agregar paneles al CardLayout
        mainPanel.add(menuPrincipalPanel, "MENU");
        mainPanel.add(juegoPanel, "JUEGO");

        add(mainPanel);
    }

    /**
     * Configura la ventana principal.
     */
    private void configurarVentana() {
        setTitle("Reverse Dots - Juego de Estrategia");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // Listener para cerrar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSalida();
            }
        });
    }

    /**
     * Muestra el menú principal.
     */
    public void mostrarMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    /**
     * Muestra el panel de juego.
     */
    public void mostrarJuego() {
        juegoPanel.actualizarVista();
        cardLayout.show(mainPanel, "JUEGO");
    }

    /**
     * Confirma la salida de la aplicación.
     */
    private void confirmarSalida() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Método main para ejecutar la aplicación Swing.
     */
    public static void main(String[] args) {
        try {
            // Establecer Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            SwingGUI gui = new SwingGUI();
            gui.setVisible(true);
        });
    }
}
