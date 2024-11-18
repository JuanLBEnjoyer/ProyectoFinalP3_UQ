package co.edu.uniquindio.model;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class InformeFinanciero extends Thread {

    private String idUsuario;
    private BilleteraVirtual billetera;

    public InformeFinanciero(String idUsuario) {
        this.idUsuario = idUsuario;
        this.billetera = BilleteraVirtual.getInstance();
    }

    @Override
    public void run() {
        try {
            // Obtener usuario
            Usuario usuario = billetera.obtenerUsuario(idUsuario);

            // Generar informe (este es un ejemplo, puedes personalizarlo como desees)
            StringBuilder informe = new StringBuilder();
            informe.append("Informe Financiero para Usuario: ").append(usuario.getNombreCompleto()).append("\n");
            informe.append("Correo: ").append(usuario.getCorreo()).append("\n");
            informe.append("Saldo Total: ").append(usuario.getSaldoTotal()).append("\n");
            informe.append("Transacciones:\n");

            for (Transaccion transaccion : billetera.obtenerTransaccionesUsuario(idUsuario)) {
                informe.append(transaccion.toString()).append("\n");
            }

            // Simular una operación de larga duración
            Thread.sleep(5000); // 5 segundos

            // Guardar el informe en un archivo o base de datos
            String rutaInforme = "C:/Users/Juanp/OneDrive/Escritorio/ProyectoFinalP3_UQ/ProyectoFinal_p3/ProyectoFinal_p3/proyectop3/src/main/resources/persistencia/informefinanciero.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaInforme))) {
                writer.write(informe.toString());
            }

            System.out.println("Informe financiero generado para el usuario: " + idUsuario);
        } catch (Exception e) {
            System.out.println("Error al generar el informe financiero: " + e.getMessage());
        }
    }
}

