package edu.ucr.gestortareas.controller;

import edu.ucr.gestortareas.model.Tarea;
import edu.ucr.gestortareas.view.VistaTareas;

import javax.swing.*;
import java.util.ArrayList;

// Archivo: ControladorTareas.java
public class ControladorTareas {
    private ArrayList<Tarea> listaModel = new ArrayList<>();
    private VistaTareas vista;
    private int contadorId = 1;

    public ControladorTareas(VistaTareas vista) {
        this.vista = vista;
        this.vista.btnAgregar.addActionListener(e -> agregar());
        this.vista.btnEliminar.addActionListener(e -> eliminar());
        this.vista.getMenuLimpiar().addActionListener(e -> limpiarTodo());
    }

    private void agregar() {
        try {
            String desc = vista.txtDesc.getText();
            if(desc.isEmpty()) {
                throw new Exception("Descripción vacía"); //
            }

            Tarea t = new Tarea(contadorId++, desc, (String)vista.cbPrioridad.getSelectedItem());
            listaModel.add(t);

            // Actualizar Tabla
            vista.modeloTabla.addRow(new Object[]{t.getId(), t.getDescripcion(), t.getPrioridad()});

            vista.txtDesc.setText("");
            vista.cbPrioridad.setSelectedIndex(0);
            vista.cbPrioridad.requestFocus();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //
        }
    }

    private void eliminar() {
        int filaSeleccionada = vista.tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "No hay fila seleccionada.", "Error", JOptionPane.ERROR_MESSAGE); //
            return;
        }

        int idEliminar = (int) vista.modeloTabla.getValueAt(filaSeleccionada, 0);
        listaModel.removeIf(t -> t.getId() == idEliminar);
        vista.modeloTabla.removeRow(filaSeleccionada);

        JOptionPane.showMessageDialog(vista, "Eliminado", "Info", JOptionPane.INFORMATION_MESSAGE);

    }

    private void limpiarTodo() {
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Seguro que desea limpiar todo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            vista.modeloTabla.setRowCount(0);
            listaModel.clear();
            contadorId = 1;
            JOptionPane.showMessageDialog(null, "Todo ha sido limpiado", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}

