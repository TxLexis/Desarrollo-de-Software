package edu.ucr.gestortareas;

import edu.ucr.gestortareas.controller.ControladorTareas;
import edu.ucr.gestortareas.view.VistaTareas;

import javax.swing.*;

public class App{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            VistaTareas vista = new VistaTareas();
            ControladorTareas controlador = new ControladorTareas(vista);
            vista.setVisible(true);
        });
    }
}