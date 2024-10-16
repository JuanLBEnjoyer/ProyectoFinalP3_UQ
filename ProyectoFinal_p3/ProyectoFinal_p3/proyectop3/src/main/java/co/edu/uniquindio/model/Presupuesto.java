package co.edu.uniquindio.model;

public class Presupuesto {

    private String idPresupuesto;
    private String nombre;
    private double montoTotalAsignado;
    private double montoGastado;
    private Categoria categoria;

    public Presupuesto(String idPresupuesto, String nombre, double montoTotalAsignado,Categoria categoria) {
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

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public double getMontoTotalAsignado() {
        return montoTotalAsignado;
    }

    public double getMontoGastado() {
        return montoGastado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

}
