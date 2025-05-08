package com.pokegame.app.modelo;

//TODA LA METODOLOGIA DE AdmEquipo ES PARA TENER UN LUGAR EN DONDE HACER EL CRUD DE LOS EQUIPOS (administrar el equipo) SI NO TE PARECE UNA BUENA IDEA, AVISAME Y LO HAGO TOD0 DENUEVO

public class AdmEquipo {
    private int id;
    private String nombre;
    private int clienteId;

    public AdmEquipo(String nombre, int idCliente) {
        this.nombre = nombre;
        this.clienteId = idCliente;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
