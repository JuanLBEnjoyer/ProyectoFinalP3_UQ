package co.edu.uniquindio.exception;

public class UsuarioNoExisteException extends RuntimeException {
    public UsuarioNoExisteException(String message) {
        super(message);
    }
}
