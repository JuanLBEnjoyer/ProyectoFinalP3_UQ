package co.edu.uniquindio;

import co.edu.uniquindio.model.*;

import java.io.IOException;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class Prueba{

    public static void main(String[] args) throws IOException {
        BilleteraVirtual billetera = BilleteraVirtual.getInstance();

        // Agregar usuarios
        billetera.agregarUsuario("user1", "John Doe", "john.doe@example.com", "1234567890", "123 Main St");
        billetera.agregarUsuario("user2", "Jane Doe", "jane.doe@example.com", "0987654321", "456 Main St");

        // Actualizar usuario
        billetera.actualizarUsuario("user1", "John Smith", "john.smith@example.com", "1234567890", "123 Main St");

        // Consultar saldo del usuario
        System.out.println("Saldo de user1: " + billetera.consultarSaldoUsuario("user1"));

        // Agregar y consultar transacciones
        Categoria categoria = new Categoria("cat1", "Food");
        billetera.crearCategoria("cat1", "Food");
        billetera.generarDeposito("trans1", LocalDate.now(), 100.0, "Salary", categoria, "user1");
        billetera.generarRetiro("trans2", LocalDate.now(), 50.0, "Groceries", categoria, "user1");
        billetera.generarTransferencia("trans3", LocalDate.now(), 25.0, "Loan", categoria, "user1", "user2");

        // Consultar transacciones de un usuario
        System.out.println("Transacciones de user1: " + billetera.obtenerTransaccionesUsuario("user1"));

        // Consultar detalles de una transacción
        System.out.println("Detalles de transacción trans1: " + billetera.obtenerDetallesTransaccion("trans1"));

        // Crear y consultar presupuestos
        Presupuesto presupuesto = new Presupuesto("pres1", "Monthly Budget", 500.0, categoria);
        billetera.crearPresupuesto("user1", presupuesto);
        System.out.println("Presupuestos de user1: " + billetera.obtenerPresupuestosUsuario("user1"));

        // Consultar monto gastado en un presupuesto
        System.out.println("Monto gastado en pres1: " + billetera.consultarMontoGastado("user1", "pres1"));

        // Consultar saldo de un presupuesto
        System.out.println("Saldo en pres1: " + billetera.consultarSaldoPresupuesto("user1", "pres1"));

        // Consultar gasto por categoría recursivamente
        System.out.println("Gasto por categoría Food: " + billetera.consultarGastoPorCategoriaRecursivo("user1", categoria));

        // Listar categorías
        System.out.println("Categorías: " + billetera.listarCategorias());

        // Asignar categoría a una transacción
        billetera.asignarCategoriaATransaccion("trans1", categoria);

        // Actualizar y eliminar categorías
        billetera.actualizarCategoria("cat1", "Groceries");
        billetera.eliminarCategoria("cat1");

        // Agregar cuentas de usuario
        Cuenta cuenta = new Cuenta("account1", "Bank1", "000123456", TipoCuenta.AHORRO);
        billetera.agregarCuentaUsuario("user1", cuenta);

        // Consultar detalles de cuenta
        System.out.println("Detalles de cuenta account1 de user1: " + billetera.consultarDetallesCuentaUsuario("user1", "account1"));

        // Listar transacciones
        System.out.println("Todas las transacciones: " + billetera.listarTransacciones());

        // Filtrar transacciones por fecha
        System.out.println("Transacciones entre fechas: " + billetera.filtrarTransaccionesPorFecha(LocalDate.now().minusDays(1), LocalDate.now()));

        // Consultar los gastos más comunes
        Map<Categoria, Double> gastosComunes = billetera.gastosMasComunes();
        System.out.println("Gastos más comunes: " + gastosComunes);

        // Consultar los usuarios con más transacciones
        List<Usuario> usuariosConMasTransacciones = billetera.usuariosConMasTransacciones();
        System.out.println("Usuarios con más transacciones: " + usuariosConMasTransacciones);

        // Consultar el saldo promedio de los usuarios
        double saldoPromedio = billetera.saldoPromedioUsuarios();
        System.out.println("Saldo promedio de los usuarios: " + saldoPromedio);

        // Generar informes y reportes
        try {
            billetera.generarReporteCSV("user1");
            billetera.generarReportePDF("user1");
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }

        // Iniciar la generación de informes financieros
        billetera.iniciarGeneracionInformeFinanciero("user1");

        // Actualizar y eliminar cuentas de usuario
        billetera.actualizarCuentaUsuario("user1", "account1", "Bank2", "000654321", TipoCuenta.CORRIENTE);
        billetera.eliminarCuentaUsuario("user1", "account1");

        // Eliminar usuario
        billetera.eliminarUsuario("user2");
    }
}

