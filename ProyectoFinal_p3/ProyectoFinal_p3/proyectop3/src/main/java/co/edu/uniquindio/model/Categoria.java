package co.edu.uniquindio.model;

public class Categoria {
    private String idCategoria;
    private String nombre;

    public Categoria(String idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    };

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String textoCategoria() {
        return idCategoria+"@@"+nombre;
    }
}
