package edu.ucr.gestortareas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Archivo: VistaTareas.java
public class VistaTareas extends JFrame {
    public JTable tabla;
    public DefaultTableModel modeloTabla;
    public JTextField txtDesc;
    public JComboBox<String> cbPrioridad;
    public JButton btnAgregar, btnEliminar;

    public VistaTareas() {
        super("Mini-Task Manager");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Descripción", "Prioridad"}, 0);


        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER); //

        // Inicializar Formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtDesc = new JTextField();
        cbPrioridad = new JComboBox<>(new String[]{"Alta", "Media", "Baja"}); //
        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");

        panelForm.add(new JLabel("Descripción:"));
        panelForm.add(txtDesc);
        panelForm.add(new JLabel("Prioridad:"));
        panelForm.add(cbPrioridad);
        panelForm.add(btnAgregar);
        panelForm.add(btnEliminar);

        add(panelForm, BorderLayout.EAST);

        crearMenu();

        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOpciones = new JMenu("Opciones");
        JMenuItem menuLimpiar = new JMenuItem("Limpiar Todo");

        menuOpciones.add(menuLimpiar);
        menuBar.add(menuOpciones);
        setJMenuBar(menuBar);

        menuLimpiar.setActionCommand("Limpiar");
    }

    public JMenuItem getMenuLimpiar() {
        return ((JMenu)getJMenuBar().getMenu(0)).getItem(0);
    }
}
