package co.edu.uniquindio.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;

public class ArchivoUtil {

    public static void guardarArchivo(String ruta,String contenido, Boolean flagAnexarContenido) throws IOException {

        FileWriter fw = new FileWriter(ruta,flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }

    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String>  contenido = new ArrayList<>();
        //abrir conexion
        FileReader fr=new FileReader(ruta);
        BufferedReader bfr=new BufferedReader(fr);
        // leer
        String linea;
        while((linea = bfr.readLine())!=null)
        {
            contenido.add(linea);
        }
        //cerrar
        bfr.close();
        fr.close();
        return contenido;
    }

    // Método genérico para guardar un objeto en un archivo binario (.dat)
    public static <T> void guardarEnBinario(T objeto, String archivoBinario) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivoBinario))) {
            salida.writeObject(objeto);
        }
    }

    // Método genérico para leer un objeto desde un archivo binario (.dat)
    public static <T> T leerDesdeBinario(String archivoBinario) throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivoBinario))) {
            return (T) entrada.readObject();
        }
    }

    // Método genérico para guardar un objeto en un archivo XML
    public static <T> void guardarEnXML(T objeto, String archivoXML) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objeto.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(objeto, new File(archivoXML));
    }

    // Método genérico para leer un objeto desde un archivo XML
    public static <T> T leerDesdeXML(Class<T> clase, String archivoXML) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clase);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new File(archivoXML));
    }

}
