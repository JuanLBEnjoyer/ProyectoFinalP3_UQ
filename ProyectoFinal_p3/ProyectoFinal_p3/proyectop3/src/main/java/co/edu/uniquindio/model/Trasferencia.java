package co.edu.uniquindio.model;

import java.time.LocalDate;

public class Trasferencia extends Transaccion {

    private final String iDUsuarioOrigen;
    private final String iDUsuarioDestino;

    public Trasferencia(String idTransaccion, LocalDate fecha, double montoTransaccion,
                        String descripcion, Categoria categoria, String iDUsuarioOrigen,
                        String iDUsuarioDestino) {
        super(idTransaccion, fecha, montoTransaccion, descripcion, categoria);
        if (iDUsuarioOrigen == null || iDUsuarioOrigen.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario origen no puede estar vacío");
        }
        if (iDUsuarioDestino == null || iDUsuarioDestino.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario destino no puede estar vacío");
        }
        this.iDUsuarioOrigen = iDUsuarioOrigen;
        this.iDUsuarioDestino = iDUsuarioDestino;
    }

    public String getIdUsuarioOrigen() {
        return iDUsuarioOrigen;
    }

    public String getIdUsuarioDestino() {
        return iDUsuarioDestino;
    }

    @Override
    public String toString() {
        return String.format("%s@@%s@@%.2f@@%s@@%s@@%s@@TRANSFERENCIA\n",
                this.getIdTransaccion(), this.getFecha(), this.getMontoTransaccion(),
                this.getDescripcion(), this.getCategoria().textoCategoria(), iDUsuarioOrigen, iDUsuarioDestino);
    }
}

