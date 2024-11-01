package com.primecinema.app.modelo;

import java.io.Serializable;

public class Sucursal implements Serializable {
    private int id;
    private String nombre_sucursal;
    private String gerente;
    private String direccion;
    private String telefono;

    public Sucursal() {
        // Constructor vacío
    }

    // Getters y setters para todos los campos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreSucursal() {
        return nombre_sucursal;
    }

    public void setNombreSucursal(String nombre_sucursal) {
        this.nombre_sucursal = nombre_sucursal;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
