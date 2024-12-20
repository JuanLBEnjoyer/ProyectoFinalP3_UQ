package co.edu.uniquindio.controller;

import co.edu.uniquindio.model.BilleteraVirtual;
import co.edu.uniquindio.model.Usuario;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AdminUsuarioController {
    private static AdminUsuarioController instance;
    private BilleteraVirtual billeteraVirtual;

    private AdminUsuarioController() {
        billeteraVirtual = BilleteraVirtual.getInstance();  // Singleton para la lógica de negocio
    }

    public static AdminUsuarioController getInstance() {
        if (instance == null) {
            instance = new AdminUsuarioController();
        }
        return instance;
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return billeteraVirtual.getUsuarios();  // Devuelve la lista de usuarios desde el modelo
    }

    public void crearUsuario(String id, String nombreCompleto, String correo, String telefono, String direccion) {
        billeteraVirtual.agregarUsuario(id, nombreCompleto, correo, telefono, direccion);  // Lógica para agregar usuario
    }

    public void actualizarUsuario(String id, String nombreCompleto, String correo, String telefono, String direccion) throws JAXBException, IOException {
        billeteraVirtual.actualizarUsuario(id, nombreCompleto, correo, telefono, direccion);  // Lógica para actualizar usuario
    }

    public void eliminarUsuario(String id) {
        billeteraVirtual.eliminarUsuario(id);  // Lógica para eliminar usuario
    }
}
