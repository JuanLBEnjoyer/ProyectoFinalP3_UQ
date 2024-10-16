package co.edu.uniquindio.utils;

import co.edu.uniquindio.model.BilleteraVirtual;
import co.edu.uniquindio.model.Usuario;

public class BilleteraVirtualUtils {
    public static BilleteraVirtual inicializarDatos(){
        BilleteraVirtual billeteraVirtual= BilleteraVirtual.getInstance();

        Usuario usuario1= new Usuario("1","Daniel Garzón", "dagamen3@gmail.com", "3105578932","Calle 9N 43-21");
        billeteraVirtual.getUsuarios().add(usuario1);

        return billeteraVirtual;
    }
}
