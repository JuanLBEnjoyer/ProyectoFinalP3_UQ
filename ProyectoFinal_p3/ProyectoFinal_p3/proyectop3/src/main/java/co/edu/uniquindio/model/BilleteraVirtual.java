package co.edu.uniquindio.model;

import co.edu.uniquindio.exception.CampoVacioException;
import co.edu.uniquindio.exception.MontoNegativoException;
import co.edu.uniquindio.exception.UsuarioNoExisteException;
import co.edu.uniquindio.exception.UsuarioYaExisteException;
import co.edu.uniquindio.persistencia.Persistencia;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BilleteraVirtual {

    private final Collection<Usuario> usuarios= new LinkedList<>();
    private final Collection<Transaccion> transacciones= new LinkedList<>();
    private static BilleteraVirtual instance;


    private static final Logger logger = Logger.getLogger(BilleteraVirtual.class.getName());

    public LinkedList<Usuario> getUsuarios() {
        return (LinkedList<Usuario>) usuarios;
    }
    private BilleteraVirtual() {
        Persistencia.generarLog(logger);
    }

    public static BilleteraVirtual getInstance() {
        if(instance == null) {
            instance = new BilleteraVirtual();
        }
        return instance;
    }

    private void buscarUsuario(String idUsuario) throws UsuarioNoExisteException {
       boolean existe = usuarios.stream().anyMatch(u -> u.getIdUsuario().equals(idUsuario));
       if(!existe) {
           throw new UsuarioNoExisteException("Usuario no existe");
       }
    }

    public Usuario obtenerUsuario(String idUsuario){
        try {
            buscarUsuario(idUsuario);
            logger.log(Level.INFO, "Usuario encontrado");
            return usuarios.stream().filter(u -> u.getIdUsuario().equals(idUsuario)).findFirst().orElse(null);
        }
        catch(UsuarioNoExisteException e){
            logger.log(Level.WARNING, "Usuario: "+idUsuario+" no encontrado",e);
            throw new RuntimeException(e);
        }
    }

    private void validarUsuario(String idUsuario) throws UsuarioYaExisteException {
        if(usuarios.stream().anyMatch(u->u.getIdUsuario().equals(idUsuario))) {
            throw new UsuarioYaExisteException("El usuario ya existe");
        }
    }

    private void validarCampos(String idUsuario, String nombreCompleto, String correo, String telefono, String direccion) throws CampoVacioException {
        if (idUsuario.isEmpty() || nombreCompleto.isEmpty() || correo.isEmpty() || telefono.isEmpty() || direccion.isEmpty()){
            throw new CampoVacioException("Datos incompletos");
        }
    }

    public void agregarUsuario(String idUsuario, String nombreCompleto, String correo, String telefono, String direccion) {
        try {
            validarUsuario(idUsuario);
            validarCampos(idUsuario,nombreCompleto,correo,telefono,direccion);
            Usuario nuevoUsusario = new Usuario(idUsuario, nombreCompleto, correo, telefono, direccion);
            usuarios.add(nuevoUsusario);
            Persistencia.guardarUsuario(nuevoUsusario);
            Persistencia.guardarCopiaArchivo("usuario");
            logger.log(Level.INFO,"Usuario guardado correctamente: {0}", nuevoUsusario.getIdUsuario());
        }
        catch(UsuarioYaExisteException e) {
            System.out.println(e.getMessage());
            logger.log(Level.WARNING,"Usuario ya existe",e);
        }
        catch (CampoVacioException e) {
            System.out.println(e.getMessage());
            logger.log(Level.WARNING,"Campo vacio",e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void actualizarUsuario(String idUsuario, String nombre, String correo, String telefono, String direccion) {
        Usuario usuarioActualizar = obtenerUsuario(idUsuario);

        usuarioActualizar.setNombreCompleto(nombre);

        usuarioActualizar.setCorreo(correo);

        usuarioActualizar.setTelefono(telefono);

        usuarioActualizar.setDireccion(direccion);

        logger.log(Level.INFO,"Usuario actualizado correctamente: {0}", usuarioActualizar.getIdUsuario());

    }

    private void validarSaldo(Usuario usuario)throws RuntimeException {
        boolean tieneSaldo = usuario.getSaldoTotal() > 0;
        if(tieneSaldo){
            throw new RuntimeException("El usuario aun tiene saldo disponible");
        }
    }

    public void eliminarUsuario(String idUsuario) {
        try {
            Usuario usuarioEliminar = obtenerUsuario(idUsuario);
            validarSaldo(usuarioEliminar);
            usuarios.remove(usuarioEliminar);
            logger.log(Level.INFO,"Usuario eliminado correctamente: ", usuarioEliminar.getIdUsuario());
        }

        catch(RuntimeException e) {
            logger.log(Level.WARNING,"Usuario no eliminado",e);
            System.out.println(e.getMessage());
        }
    }

    public Collection<Transaccion> getTransacciones() {
        return transacciones;
    }

    public  void  validarMonto(double monto){
        if (monto<0){
            throw new MontoNegativoException("El monto no puede ser negativo, ingrese un valor valido");
        }
    }

    public void generarDeposito(String idTransaccion, LocalDate fecha, double montoTransaccion,
                                String descripcion, Categoria categoria,
                                String idCuentaDestino) throws IOException {

        try {
            validarMonto(montoTransaccion);
            Deposito deposito = new Deposito(idTransaccion, fecha, montoTransaccion, descripcion, categoria, idCuentaDestino);
            BilleteraVirtual billeteraVirtual = BilleteraVirtual.getInstance();
            Usuario usuarioAsociado = billeteraVirtual.obtenerUsuario(idCuentaDestino);
            double saldoActual = usuarioAsociado.getSaldoTotal();
            usuarioAsociado.setSaldoTotal(saldoActual+montoTransaccion);
            transacciones.add(deposito);
            Persistencia.guardarTransaccion(deposito);
            Persistencia.guardarCopiaArchivo("transaccion");
            System.out.print("Deposito de "+montoTransaccion+" realizado correctamente");
        }
        catch (MontoNegativoException e){
            System.out.print(e.getMessage());
            logger.log(Level.WARNING,"Monto negativo",e);
        }


    }
    public void  generarRetiro(String idTransaccion, LocalDate fecha, double montoTransaccion,
                               String descripcion, Categoria categoria,
                               String idUsuarioAsociado){

        try {
            validarMonto(montoTransaccion);
            Retiro retiro = new Retiro(idTransaccion, fecha, montoTransaccion, descripcion, categoria, idUsuarioAsociado);
            BilleteraVirtual billeteraVirtual = BilleteraVirtual.getInstance();
            Usuario usuarioAsociado = billeteraVirtual.obtenerUsuario(idUsuarioAsociado);
            double saldoActual = usuarioAsociado.getSaldoTotal();
            if (saldoActual<montoTransaccion){
                logger.log(Level.WARNING,"Saldo solicitado es mayor al saldo disponible");
                System.out.println("Saldo insuficiente");
            }
            else {
                usuarioAsociado.setSaldoTotal(saldoActual-montoTransaccion);
                transacciones.add(retiro);
                Persistencia.guardarTransaccion(retiro);
                Persistencia.guardarCopiaArchivo("transaccion");
                System.out.print("Deposito de "+montoTransaccion+" realizado correctamente");
            }

        }
        catch (MontoNegativoException | IOException e){
            System.out.print(e.getMessage());
            logger.log(Level.WARNING,"Monto negativo",e);
        }



    }
    public void generarTransferencia(String idTransaccion, LocalDate fecha, double montoTransaccion,
                                     String descripcion, Categoria categoria, String iDUsuarioOrigen,
                                     String iDUsuarioDestino){

        try {
            validarMonto(montoTransaccion);
            Trasferencia trasferencia = new Trasferencia(idTransaccion,fecha, montoTransaccion, descripcion, categoria, iDUsuarioOrigen, iDUsuarioDestino);
            BilleteraVirtual billeteraVirtual = BilleteraVirtual.getInstance();
            Usuario usuarioOrigen = billeteraVirtual.obtenerUsuario(iDUsuarioOrigen);
            Usuario usuarioDestino = billeteraVirtual.obtenerUsuario(iDUsuarioDestino);

            double saldoActual = usuarioOrigen.getSaldoTotal();
            if (saldoActual<montoTransaccion){
                logger.log(Level.WARNING,"Saldo a transferir es mayor al saldo disponible");
                System.out.println("Saldo insuficiente");
            }
            else {
                usuarioOrigen.setSaldoTotal(saldoActual-montoTransaccion);
                usuarioDestino.setSaldoTotal(saldoActual+montoTransaccion);
                transacciones.add(trasferencia);
                Persistencia.guardarTransaccion(trasferencia);
                Persistencia.guardarCopiaArchivo("transaccion");
                System.out.print("Deposito de "+montoTransaccion+" realizado correctamente");
            }

        }
        catch (MontoNegativoException | IOException e){
            System.out.print(e.getMessage());
            logger.log(Level.WARNING,"Monto negativo",e);
        }


    }
}
