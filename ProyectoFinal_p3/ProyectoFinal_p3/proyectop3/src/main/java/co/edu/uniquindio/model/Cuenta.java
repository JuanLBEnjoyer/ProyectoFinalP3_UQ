package co.edu.uniquindio.model;

import co.edu.uniquindio.exception.DatosInvalidosException;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String idCuenta;
    private String nombreBanco;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;

    public Cuenta(String idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta) {
        if (idCuenta == null || idCuenta.isEmpty()) {
            throw new DatosInvalidosException("El id de la cuenta no puede estar vacío");
        }
        if (nombreBanco == null || nombreBanco.isEmpty()) {
            throw new DatosInvalidosException("El nombre del banco no puede estar vacío");
        }
        if (numeroCuenta == null || numeroCuenta.isEmpty()) {
            throw new DatosInvalidosException("El número de cuenta no puede estar vacío");
        }
        if (tipoCuenta == null) {
            throw new DatosInvalidosException("El tipo de cuenta no puede ser nulo");
        }
        this.idCuenta = idCuenta;
        this.nombreBanco = nombreBanco;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "idCuenta='" + idCuenta + '\'' +
                ", nombreBanco='" + nombreBanco + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", tipoCuenta=" + tipoCuenta +
                '}';
    }

    public void actualizarCuenta(String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta) {
        if (nombreBanco != null && !nombreBanco.isEmpty()) {
            this.nombreBanco = nombreBanco;
        }
        if (numeroCuenta != null && !numeroCuenta.isEmpty()) {
            this.numeroCuenta = numeroCuenta;
        }
        if (tipoCuenta != null) {
            this.tipoCuenta = tipoCuenta;
        }
    }
}


