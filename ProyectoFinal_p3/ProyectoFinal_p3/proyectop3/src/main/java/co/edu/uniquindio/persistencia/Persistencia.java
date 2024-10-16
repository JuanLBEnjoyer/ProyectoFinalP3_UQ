package co.edu.uniquindio.persistencia;

import co.edu.uniquindio.log.LogConfig;
import co.edu.uniquindio.model.Transaccion;
import co.edu.uniquindio.model.Usuario;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Persistencia {


    public static void generarLog(Logger logger){

        String rutaLog = "C://td//persistencia//log//billeteraVirtual_Log.txt";

        LogConfig.setupLogger(rutaLog,logger);

    }

    public static void guardarUsuario(Usuario usuario) throws IOException {

        String rutaUsuarios = "C:/td/persistencia/archivos/archivoUsuarios.txt";

        String contenido = usuario.getIdUsuario()+"@@"+usuario.getNombreCompleto()+
                "@@"+usuario.getCorreo()+"@@"+usuario.getTelefono()+
                "@@"+usuario.getDireccion()+"@@"+usuario.getSaldoTotal()+"\n";

        ArchivoUtil.guardarArchivo(rutaUsuarios,contenido,true);

    }

    public ArrayList<String> leerUsuarios() throws IOException {
        String rutaUsuarios = "C:/td/persistencia/archivos/archivoUsuarios.txt";
        return ArchivoUtil.leerArchivo(rutaUsuarios);
    }



    public static void guardarTransaccion(Transaccion transaccion) throws IOException {

        String rutaTransaccion = "C:/td/persistencia/archivos/transaccion.txt";

        String contenido = transaccion.toString();

        ArchivoUtil.guardarArchivo(rutaTransaccion,contenido,true);

    }

    public ArrayList<String> leerTransacciones() throws IOException {
        String rutaTransaccion = "C:/td/persistencia/archivos/transaccion.txt";

        return ArchivoUtil.leerArchivo(rutaTransaccion);
    }


    private static String generarNombreArchivo(String nombre) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return nombre + "_" + timestamp + ".txt"; // Puedes cambiar la extensión según el tipo de archivo
    }

    public  static void guardarCopiaArchivo(String nombre) throws IOException {
        String nombreArchivoCopia = generarNombreArchivo(nombre);
        File archivoOriginal = new File("resources/registroUsuarios.txt"); // Cambia esto por la ruta original
        File archivoCopia = new File("resources/" + nombreArchivoCopia); // Ruta de la copia

        try (InputStream in = new FileInputStream(archivoOriginal);
             OutputStream out = new FileOutputStream(archivoCopia)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw e;
        }
    }







}
