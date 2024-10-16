package co.edu.uniquindio.model;

import java.time.LocalDate;



public class Retiro extends Transaccion{

    private String idUsuarioAsociado;


    public Retiro(String idTransaccion, LocalDate fecha, double montoTransaccion,
                  String descripcion, Categoria categoria, String idUsuarioAsociado) {
        super(idTransaccion, fecha, montoTransaccion, descripcion, categoria);
        this.idUsuarioAsociado = idUsuarioAsociado;
    }
    public String getIdUsuarioAsociado() {
        return idUsuarioAsociado;
    }

    @Override
    public String toString() {
        return this.getIdTransaccion()+"@@"+this.getFecha()+"@@"+
                this.getMontoTransaccion()+"@@"+this.getDescripcion()+"@@"
                +this.getCategoria().textoCategoria()+"@@"+idUsuarioAsociado+"@@"
                +"RETIRO"+"\n";
    }
}
