package com.primecinema.app.modelo;

public class DetalleCompra {
    private int idDetalleCompra;
    private int idCompra;
    private int idAsiento;

    // Constructor
    public DetalleCompra(int idDetalleCompra, int idCompra, int idAsiento) {
        this.idDetalleCompra = idDetalleCompra;
        this.idCompra = idCompra;
        this.idAsiento = idAsiento;
    }

    // Getters y setters
    public int getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(int idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    // Otros métodos para interactuar con la entidad DetalleCompra, si es necesario
}
