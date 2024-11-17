package co.edu.uniquindio;

import co.edu.uniquindio.model.BilleteraVirtual;
import co.edu.uniquindio.model.Categoria;

import java.io.IOException;
import java.time.LocalDate;

public class Prueba {

    public static void main(String[] args) throws IOException {

        BilleteraVirtual billeteraVirtual = BilleteraVirtual.getInstance();

        billeteraVirtual.agregarUsuario("1234","Juan","1245@gmail","32244515","Calle 10");

        Categoria categoria = new Categoria("4556","Deudas");

        billeteraVirtual.generarDeposito("1234", LocalDate.now(),3000,"Dinero que debo",categoria,"1234");

    }


}
