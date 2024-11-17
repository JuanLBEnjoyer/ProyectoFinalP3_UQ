package co.edu.uniquindio.utils;

import co.edu.uniquindio.log.LogConfig;
import co.edu.uniquindio.model.BilleteraVirtual;
import co.edu.uniquindio.model.Transaccion;
import co.edu.uniquindio.model.Usuario;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class Persistencia {

    private static final String DEFAULT_BACKUP_PATH = "C:/td/persistencia/respaldo/";

    public static void generarLog(Logger logger) {
        String ruta = obtenerRutaProperties("RutaLog");
        LogConfig.setupLogger(ruta, logger);
    }

    public static void guardarUsuario(Usuario usuario) throws IOException {
        String ruta = obtenerRutaProperties("RutaUsuarios");
        String contenido = String.format("%s@@%s@@%s@@%s@@%s@@%f\n",
                usuario.getIdUsuario(), usuario.getNombreCompleto(),
                usuario.getCorreo(), usuario.getTelefono(),
                usuario.getDireccion(), usuario.getSaldoTotal());
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }

    public ArrayList<String> leerUsuarios() throws IOException {
        String ruta = obtenerRutaProperties("RutaUsuarios");
        return ArchivoUtil.leerArchivo(ruta);
    }

    public static void guardarTransaccion(Transaccion transaccion) throws IOException {
        String ruta = obtenerRutaProperties("RutaTransacciones");
        String contenido = transaccion.toString();
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }

    public ArrayList<String> leerTransacciones() throws IOException {
        String ruta = obtenerRutaProperties("RutaTransacciones");
        return ArchivoUtil.leerArchivo(ruta);
    }

    private static String generarNombreArchivo(String nombre) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return nombre + "_" + timestamp + ".txt";
    }

    public static void guardarCopiaArchivo(String nombre, String archivo) throws IOException {
        String nombreArchivoCopia = generarNombreArchivo(nombre);
        String rutaOriginal = obtenerRutaProperties(archivo);
        File archivoOriginal = new File(rutaOriginal);
        File archivoCopia = new File(DEFAULT_BACKUP_PATH + nombreArchivoCopia);

        if (!archivoCopia.getParentFile().exists()) {
            archivoCopia.getParentFile().mkdirs();
        }

        try (InputStream in = new FileInputStream(archivoOriginal);
             OutputStream out = new FileOutputStream(archivoCopia)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    public static void guardarBilleteraEnBinario(BilleteraVirtual billetera) throws IOException {
        String ruta = obtenerRutaProperties("RutaBinario");
        ArchivoUtil.guardarEnBinario(billetera, ruta);
    }

    public static BilleteraVirtual leerBilleteraDesdeBinario() throws IOException, ClassNotFoundException {
        String ruta = obtenerRutaProperties("RutaBinario");
        return ArchivoUtil.leerDesdeBinario(ruta);
    }

    public static void guardarBilleteraEnXML(BilleteraVirtual billetera) throws JAXBException {
        String ruta = obtenerRutaProperties("RutaXML");
        ArchivoUtil.guardarEnXML(billetera, ruta);
    }

    public static BilleteraVirtual leerBilleteraDesdeXML() throws JAXBException {
        String ruta = obtenerRutaProperties("RutaXML");
        return ArchivoUtil.leerDesdeXML(BilleteraVirtual.class, ruta);
    }

    public static String obtenerRutaProperties(String fileName) {
        Properties properties = new Properties();
        try {
            properties.load(Persistencia.class.getResourceAsStream("/rutas.properties"));
            return properties.getProperty(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Archivo 'rutas.properties' no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo 'rutas.properties': " + e.getMessage());
        }
        return "";
    }
}

