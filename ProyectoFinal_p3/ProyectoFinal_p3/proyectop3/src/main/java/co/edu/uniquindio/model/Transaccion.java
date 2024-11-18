package co.edu.uniquindio.model;

import co.edu.uniquindio.exception.DatosInvalidosException;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String idTransaccion;
    private final LocalDate fecha;
    private final double montoTransaccion;
    private final String descripcion;
    private Categoria categoria;

    public Transaccion(String idTransaccion, LocalDate fecha, double montoTransaccion, String descripcion, Categoria categoria) {
        if (idTransaccion == null || idTransaccion.isEmpty()) {
            throw new DatosInvalidosException("El id de la transacción no puede estar vacío");
        }
        if (fecha == null) {
            throw new DatosInvalidosException("La fecha no puede ser nula");
        }
        if (montoTransaccion < 0) {
            throw new DatosInvalidosException("El monto de la transacción no puede ser negativo");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new DatosInvalidosException("La descripción no puede estar vacía");
        }
        if (categoria == null) {
            throw new DatosInvalidosException("La categoría no puede ser nula");
        }
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new DatosInvalidosException("La categoría no puede ser nula");
        }
        this.categoria = categoria;
    }

    @Override
    public abstract String toString();
}


