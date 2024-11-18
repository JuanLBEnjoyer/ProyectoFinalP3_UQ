package co.edu.uniquindio.model;

import co.edu.uniquindio.exception.DatosInvalidosException;

import java.io.Serializable;

public class Categoria implements Serializable {
    private String idCategoria;
    private String nombre;

    private static final long serialVersionUID = 1L;

    public Categoria(String idCategoria, String nombre) {
        if (idCategoria == null || idCategoria.isEmpty()) {
            throw new DatosInvalidosException("El id de la categoría no puede estar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new DatosInvalidosException("El nombre de la categoría no puede estar vacío");
        }
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        if (idCategoria == null || idCategoria.isEmpty()) {
            throw new DatosInvalidosException("El id de la categoría no puede estar vacío");
        }
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new DatosInvalidosException("El nombre de la categoría no puede estar vacío");
        }
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria='" + idCategoria + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public String textoCategoria() {
        return idCategoria + "@@" + nombre;
    }
}


