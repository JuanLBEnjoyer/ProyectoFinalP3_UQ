package co.edu.uniquindio.model;

import co.edu.uniquindio.exception.DatosInvalidosException;

import java.io.Serializable;

public class Presupuesto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPresupuesto;
    private String nombre;
    private double montoTotalAsignado;
    private double montoGastado;
    private Categoria categoria;

    public Presupuesto(String idPresupuesto, String nombre, double montoTotalAsignado, Categoria categoria) {
        if (idPresupuesto == null || idPresupuesto.isEmpty()) {
            throw new DatosInvalidosException("El id del presupuesto no puede estar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new DatosInvalidosException("El nombre del presupuesto no puede estar vacío");
        }
        if (montoTotalAsignado < 0) {
            throw new DatosInvalidosException("El monto total asignado no puede ser negativo");
        }
        if (categoria == null) {
            throw new DatosInvalidosException("La categoría no puede ser nula");
        }
        this.idPresupuesto = idPresupuesto;
        this.nombre = nombre;
        this.montoTotalAsignado = montoTotalAsignado;
        this.montoGastado = 0;
        this.categoria = categoria;
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public double getMontoTotalAsignado() {
        return montoTotalAsignado;
    }

    public void setMontoTotalAsignado(double montoTotalAsignado) {
        if (montoTotalAsignado < 0) {
            throw new IllegalArgumentException("El monto total asignado no puede ser negativo");
        }
        this.montoTotalAsignado = montoTotalAsignado;
    }

    public double getMontoGastado() {
        return montoGastado;
    }

    public void setMontoGastado(double montoGastado) {
        if (montoGastado < 0) {
            throw new IllegalArgumentException("El monto gastado no puede ser negativo");
        }
        this.montoGastado = montoGastado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }
        this.categoria = categoria;
    }

    public void agregarGasto(double monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("El monto gastado no puede ser negativo");
        }
        this.montoGastado += monto;
    }

    public double calcularSaldoRestante() {
        return montoTotalAsignado - montoGastado;
    }

    @Override
    public String toString() {
        return "Presupuesto{" +
                "idPresupuesto='" + idPresupuesto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", montoTotalAsignado=" + montoTotalAsignado +
                ", montoGastado=" + montoGastado +
                ", categoria=" + categoria +
                '}';
    }
}



