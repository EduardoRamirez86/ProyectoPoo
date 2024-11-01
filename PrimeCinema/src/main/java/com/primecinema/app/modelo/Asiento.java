package com.primecinema.app.modelo;

import com.primecinema.app.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Asiento {
    private int idAsiento;
    private int idSala;
    private int fila;
    private int columna;
    private String estado;

    // Constructor
    public Asiento(int idAsiento, int idSala, int fila, int columna, String estado) {
        this.idAsiento = idAsiento;
        this.idSala = idSala;
        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
