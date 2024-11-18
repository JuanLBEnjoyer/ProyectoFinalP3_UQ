package co.edu.uniquindio.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogConfig {



    public static void setupLogger(String rutaLog, Logger logger) {
        try {

            FileHandler fileHandler = new FileHandler(rutaLog, true);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al configurar el logger: {0}", e.getMessage());
        }
    }
}
