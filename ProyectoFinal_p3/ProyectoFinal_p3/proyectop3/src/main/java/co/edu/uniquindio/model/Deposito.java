package co.edu.uniquindio.model;

import java.time.LocalDate;

public class Deposito extends Transaccion {

    private final String idCuentaDestino;

    public Deposito(String idTransaccion, LocalDate fecha, double montoTransaccion,
                    String descripcion, Categoria categoria, String idCuentaDestino) {
        super(idTransaccion, fecha, montoTransaccion, descripcion, categoria);
        if (idCuentaDestino == null || idCuentaDestino.isEmpty()) {
            throw new IllegalArgumentException("El ID de la cuenta destino no puede estar vacío");
        }
        this.idCuentaDestino = idCuentaDestino;
    }

    public String getIdCuentaDestino() {
        return idCuentaDestino;
    }

    @Override
    public String toString() {
        return String.format("%s@@%s@@%.2f@@%s@@%s@@%s@@DEPOSITO\n",
                this.getIdTransaccion(), this.getFecha(), this.getMontoTransaccion(),
                this.getDescripcion(), this.getCategoria().textoCategoria(), idCuentaDestino);
    }
}

