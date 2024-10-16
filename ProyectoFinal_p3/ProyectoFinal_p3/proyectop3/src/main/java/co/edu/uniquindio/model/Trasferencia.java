package co.edu.uniquindio.model;

import java.time.LocalDate;

public class Trasferencia extends Transaccion{

    private String iDUsuarioOrigen;
    private String iDUsuarioDestino;

    public Trasferencia(String idTransaccion, LocalDate fecha, double montoTransaccion,
                        String descripcion, Categoria categoria, String IdUsuarioOrigen,
                        String IdUsuarioDestino) {
        super(idTransaccion, fecha, montoTransaccion, descripcion, categoria);
        this.iDUsuarioOrigen = IdUsuarioOrigen;
        this.iDUsuarioDestino = IdUsuarioDestino;

    }
    public String getIdUsuarioOrigen() {
        return iDUsuarioOrigen;
    }
    public String getIdUsuarioDestino() {
        return iDUsuarioDestino;
    }

    @Override
    public String toString() {
        return this.getIdTransaccion()+"@@"+this.getFecha()+"@@"+
                this.getMontoTransaccion()+"@@"+this.getDescripcion()+"@@"
                +this.getCategoria().textoCategoria()+"@@"+iDUsuarioOrigen+"@@"
                +iDUsuarioDestino+"@@"+"TRANSFERENCIA"+"\n";
    }
}
