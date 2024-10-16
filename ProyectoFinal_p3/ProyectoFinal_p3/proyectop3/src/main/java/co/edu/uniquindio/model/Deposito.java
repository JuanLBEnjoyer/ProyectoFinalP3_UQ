package co.edu.uniquindio.model;

import java.time.LocalDate;

public class Deposito extends Transaccion{

    private String idCuentaDestino;

    public Deposito(String idTransaccion, LocalDate fecha, double montoTransaccion,
                    String descripcion,Categoria categoria,String idCuentaDestino) {
        super(idTransaccion, fecha, montoTransaccion, descripcion, categoria);
        this.idCuentaDestino = idCuentaDestino;
    }


    @Override
    public String toString() {
        return this.getIdTransaccion()+"@@"+this.getFecha()+"@@"+
                this.getMontoTransaccion()+"@@"+this.getDescripcion()+"@@"
                +this.getCategoria().textoCategoria()+"@@"+idCuentaDestino+"@@"
                +"DEPOSITO"+"\n";
    }
}
