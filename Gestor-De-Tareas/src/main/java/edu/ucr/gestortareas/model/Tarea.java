package edu.ucr.gestortareas.model;

public class Tarea {
    private int id;
    private String descripcion;
    private String prioridad; // Alta, Media, Baja

    public Tarea(int id, String descripcion, String prioridad) {
        this.id = id;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    // Getters necesarios para el TableModel
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }
}