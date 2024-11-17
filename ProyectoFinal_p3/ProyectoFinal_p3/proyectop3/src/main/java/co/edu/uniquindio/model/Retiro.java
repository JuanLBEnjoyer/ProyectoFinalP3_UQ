package co.edu.uniquindio.model;

import java.time.LocalDate;



public class Retiro extends Transaccion  {

    private final String idUsuarioAsociado;

    public Retiro(String idTransaccion, LocalDate fecha, double montoTransaccion,
                  String descripcion, Categoria categoria, String idUsuarioAsociado) {
        super(idTransaccion, fecha, montoTransaccion, descripcion, categoria);
        if (idUsuarioAsociado == null || idUsuarioAsociado.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario asociado no puede estar vacío");
        }
        this.idUsuarioAsociado = idUsuarioAsociado;
    }

    public String getIdUsuarioAsociado() {
        return idUsuarioAsociado;
    }

    @Override
    public String toString() {
        return String.format("%s@@%s@@%.2f@@%s@@%s@@%s@@RETIRO\n",
                this.getIdTransaccion(), this.getFecha(), this.getMontoTransaccion(),
                this.getDescripcion(), this.getCategoria().textoCategoria(), idUsuarioAsociado);
    }
}

