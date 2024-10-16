package co.edu.uniquindio.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String direccion;
    private double saldoTotal;
    private Collection<Cuenta> cuentas;
    private Collection<Transaccion> transacciones;
    private Collection<Presupuesto> presupuestos;

    public Usuario(String idUsuario, String nombreCompleto, String correo, String telefono, String direccion) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.saldoTotal = 0;
        this.cuentas = new LinkedList<>();
        this.transacciones = new LinkedList<>();
        this.presupuestos = new LinkedList<>();

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {this.direccion = direccion;}

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Collection<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", saldoTotal=" + saldoTotal +
                ", cuentas=" + cuentas +
                ", transacciones=" + transacciones +
                ", presupuestos=" + presupuestos +
                '}';
    }
}
