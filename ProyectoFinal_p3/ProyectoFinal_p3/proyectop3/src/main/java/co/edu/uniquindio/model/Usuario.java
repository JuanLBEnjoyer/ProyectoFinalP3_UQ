package co.edu.uniquindio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Usuario implements Serializable {
    private final String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String direccion;
    private double saldoTotal;
    private final Collection<Cuenta> cuentas;
    private final Collection<Transaccion> transacciones;
    private final Collection<Presupuesto> presupuestos;

    private static final long serialVersionUID = 1L;

    public Usuario(String idUsuario, String nombreCompleto, String correo, String telefono, String direccion) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El id del usuario no puede estar vacío");
        }
        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }
        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
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

    public void setSaldoTotal(double saldoTotal) {
        if (saldoTotal < 0) {
            throw new IllegalArgumentException("El saldo total no puede ser negativo");
        }
        this.saldoTotal = saldoTotal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }
        this.nombreCompleto = nombreCompleto;
    }

    public Collection<Cuenta> getCuentas() {
        return cuentas;
    }

    public Collection<Transaccion> getTransacciones() {
        return transacciones;
    }

    public Collection<Presupuesto> getPresupuestos() {
        return presupuestos;
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

    public void actualizarPerfil(String nombreCompleto, String correo, String telefono, String direccion) {
        if (nombreCompleto != null && !nombreCompleto.isEmpty()) {
            this.nombreCompleto = nombreCompleto;
        }
        if (correo != null && !correo.isEmpty()) {
            this.correo = correo;
        }
        if (telefono != null && !telefono.isEmpty()) {
            this.telefono = telefono;
        }
        if (direccion != null && !direccion.isEmpty()) {
            this.direccion = direccion;
        }
    }

    public Collection<Transaccion> obtenerTransacciones() {
        return Collections.unmodifiableCollection(this.transacciones);
    }

    public Collection<Presupuesto> obtenerPresupuestos() {
        return Collections.unmodifiableCollection(this.presupuestos);
    }

    public void agregarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            this.cuentas.add(cuenta);
        } else {
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        }
    }


    public void eliminarCuenta(String idCuenta) {
        this.cuentas.removeIf(cuenta -> cuenta.getIdCuenta().equals(idCuenta));
    }


    public Collection<Cuenta> obtenerCuentas() {
        return Collections.unmodifiableCollection(this.cuentas);
    }

    public Cuenta obtenerCuenta(String idCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getIdCuenta().equals(idCuenta)) {
                return cuenta;
            }
        }
        throw new IllegalArgumentException("Cuenta no encontrada con el ID: " + idCuenta);
    }

    public void agregarPresupuesto(Presupuesto presupuesto) {
        if (presupuesto != null) {
            this.presupuestos.add(presupuesto);
        } else {
            throw new IllegalArgumentException("El presupuesto no puede ser nulo");
        }
    }

    public void actualizarPresupuesto(String idPresupuesto, String nombre, double montoTotalAsignado, Categoria categoria) {
        for (Presupuesto presupuesto : presupuestos) {
            if (presupuesto.getIdPresupuesto().equals(idPresupuesto)) {
                if (nombre != null && !nombre.isEmpty()) {
                    presupuesto.setNombre(nombre);
                }
                if (montoTotalAsignado >= 0) {
                    presupuesto.setMontoTotalAsignado(montoTotalAsignado);
                }
                if (categoria != null) {
                    presupuesto.setCategoria(categoria);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Presupuesto no encontrado con el ID: " + idPresupuesto);
    }

    public void eliminarPresupuesto(String idPresupuesto) {
        this.presupuestos.removeIf(presupuesto -> presupuesto.getIdPresupuesto().equals(idPresupuesto));
    }

    public double consultarMontoGastado(String idPresupuesto) {
        for (Presupuesto presupuesto : presupuestos) {
            if (presupuesto.getIdPresupuesto().equals(idPresupuesto)) {
                return presupuesto.getMontoGastado();
            }
        }
        throw new IllegalArgumentException("Presupuesto no encontrado con el ID: " + idPresupuesto);
    }

    public double consultarSaldoPresupuesto(String idPresupuesto) {
        for (Presupuesto presupuesto : presupuestos) {
            if (presupuesto.getIdPresupuesto().equals(idPresupuesto)) {
                return presupuesto.calcularSaldoRestante();
            }
        }
        throw new IllegalArgumentException("Presupuesto no encontrado con el ID: " + idPresupuesto);
    }

    public double consultarGastoPorCategoria(Categoria categoria) {
        double totalGastado = 0;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getCategoria().equals(categoria)) {
                totalGastado += transaccion.getMontoTransaccion();
            }
        }
        return totalGastado;
    }

    public Collection<Transaccion> consultarTransacciones() {
        return Collections.unmodifiableCollection(this.transacciones);
    }

    public void categorizarTransaccion(String idTransaccion, Categoria categoria) {
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getIdTransaccion().equals(idTransaccion)) {
                transaccion.setCategoria(categoria);
                return;
            }
        }
        throw new IllegalArgumentException("Transacción no encontrada con el ID: " + idTransaccion);
    }

    public Transaccion obtenerDetallesTransaccion(String idTransaccion) {
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getIdTransaccion().equals(idTransaccion)) {
                return transaccion;
            }
        }
        throw new IllegalArgumentException("Transacción no encontrada con el ID: " + idTransaccion);
    }


}

