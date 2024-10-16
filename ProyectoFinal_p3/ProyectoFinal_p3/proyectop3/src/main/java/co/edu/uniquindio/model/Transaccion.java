package co.edu.uniquindio.model;

import java.time.LocalDate;

public abstract class Transaccion {

    private String idTransaccion;
    private LocalDate fecha;
    private double montoTransaccion;
    private String descripcion;
    private Categoria categoria;

    public Transaccion(String idTransaccion,LocalDate fecha, double montoTransaccion, String descripcion, Categoria categoria) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.montoTransaccion = montoTransaccion;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getMontoTransaccion() {
        return montoTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoria getCategoria() {return categoria;}

    public abstract String toString();

}
