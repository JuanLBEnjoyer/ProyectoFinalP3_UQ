package co.edu.uniquindio.model;

import co.edu.uniquindio.exception.*;
import co.edu.uniquindio.utils.Persistencia;
import co.edu.uniquindio.utils.ReporteUtil;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@XmlRootElement
public class BilleteraVirtual implements Serializable {

    private static final long serialVersionUID = 1L;



    @XmlElement
    private final List<Usuario> usuarios= new ArrayList<>();
    @XmlElement
    private final List<Transaccion> transacciones= new ArrayList<>();

    private final List<Categoria> categorias = new ArrayList<>();

    private static BilleteraVirtual instance;


    private static final Logger logger = Logger.getLogger(BilleteraVirtual.class.getName());

    public ArrayList<Usuario> getUsuarios() {
        return (ArrayList<Usuario>) usuarios;
    }
    private BilleteraVirtual() {
        Persistencia.generarLog(logger);
    }

    public static synchronized BilleteraVirtual getInstance() {
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

    public synchronized void agregarUsuario(String idUsuario, String nombreCompleto, String correo, String telefono, String direccion) {
        try {
            validarUsuario(idUsuario);
            validarCampos(idUsuario, nombreCompleto, correo, telefono, direccion);
            Usuario nuevoUsuario = new Usuario(idUsuario, nombreCompleto, correo, telefono, direccion);
            usuarios.add(nuevoUsuario);
            Persistencia.guardarUsuario(nuevoUsuario);
            Persistencia.guardarCopiaArchivo("usuario","RutaUsuarios");
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
            logger.log(Level.INFO, "Usuario guardado correctamente: {0}", nuevoUsuario.getIdUsuario());
        } catch (UsuarioYaExisteException | CampoVacioException e) {
            System.out.println(e.getMessage());
            logger.log(Level.WARNING, e.getMessage(), e);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }



    public synchronized void actualizarUsuario(String idUsuario, String nombre, String correo, String telefono, String direccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        usuario.actualizarPerfil(nombre, correo, telefono, direccion);
        try {
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
        logger.log(Level.INFO,"Usuario actualizado correctamente: {0}", usuario.getIdUsuario());

    }

    private void validarSaldo(Usuario usuario) throws SaldoDisponibleException {
        boolean tieneSaldo = usuario.getSaldoTotal() > 0;
        if (tieneSaldo) {
            throw new SaldoDisponibleException("El usuario aún tiene saldo disponible");
        }
    }


    public void eliminarUsuario(String idUsuario) {
        try {
            Usuario usuarioEliminar = obtenerUsuario(idUsuario);
            validarSaldo(usuarioEliminar);
            usuarios.remove(usuarioEliminar);
            logger.log(Level.INFO,"Usuario eliminado correctamente: ", usuarioEliminar.getIdUsuario());
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
        }

        catch(RuntimeException e) {
            logger.log(Level.WARNING,"Usuario no eliminado",e);
            System.out.println(e.getMessage());
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
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
            Persistencia.guardarCopiaArchivo("transaccion","RutaTransacciones");
            logger.log(Level.INFO,"Deposito de "+montoTransaccion+" realizado correctamente");
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
        }
        catch (MontoNegativoException e){
            System.out.print(e.getMessage());
            logger.log(Level.WARNING,"Monto negativo",e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
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
                Persistencia.guardarCopiaArchivo("transaccion","RutaTransacciones");
                logger.log(Level.INFO,"Retiro de "+montoTransaccion+" realizado correctamente");
                Persistencia.guardarBilleteraEnBinario(this);
                Persistencia.guardarBilleteraEnXML(this);
            }

        }
        catch (MontoNegativoException | IOException e){
            System.out.print(e.getMessage());
            logger.log(Level.WARNING,"Monto negativo",e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
    public void generarTransferencia(String idTransaccion, LocalDate fecha, double montoTransaccion,
                                     String descripcion, Categoria categoria, String iDUsuarioOrigen,
                                     String iDUsuarioDestino){

        try {
            validarMonto(montoTransaccion);
            Transferencia trasferencia = new Transferencia(idTransaccion,fecha, montoTransaccion, descripcion, categoria, iDUsuarioOrigen, iDUsuarioDestino);
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
                Persistencia.guardarCopiaArchivo("transaccion","RutaTransacciones");
               logger.log(Level.INFO,"Transaccion de"+montoTransaccion+" realizada correctamente");
                Persistencia.guardarBilleteraEnBinario(this);
                Persistencia.guardarBilleteraEnXML(this);
            }

        }
        catch (MontoNegativoException | IOException e){
            System.out.print(e.getMessage());
            logger.log(Level.WARNING,"Monto negativo",e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }


    }

    public double consultarSaldoUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        logger.log(Level.INFO, "Saldo consultado para usuario: {0}, Saldo: {1}", new Object[]{idUsuario,usuario.getSaldoTotal()});
        return usuario.getSaldoTotal();
    }

    public Collection<Transaccion> obtenerTransaccionesUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        logger.log(Level.INFO, "Transacciones consultadas para usuario: {0}",idUsuario);
        return usuario.obtenerTransacciones();
    }

    public Collection<Presupuesto> obtenerPresupuestosUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        logger.log(Level.INFO, "Presupuestos consultados para usuario: {0}",idUsuario);
        return usuario.obtenerPresupuestos();
    }

    public void agregarCuentaUsuario(String idUsuario, Cuenta cuenta) {
        Usuario usuario = obtenerUsuario(idUsuario);
        usuario.agregarCuenta(cuenta);
        try {
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
            logger.log(Level.INFO, "Cuenta agregada para usuario: {0}", idUsuario);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarCuentaUsuario(String idUsuario, String idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta) {
        Usuario usuario = obtenerUsuario(idUsuario);
        Cuenta cuenta = usuario.obtenerCuenta(idCuenta);
        cuenta.actualizarCuenta(nombreBanco, numeroCuenta, tipoCuenta);
        try {
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
            logger.log(Level.INFO, "Cuenta actualizada para usuario: {0}, Cuenta ID: {1}", new Object[]{idUsuario, idCuenta});
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarCuentaUsuario(String idUsuario, String idCuenta) {
        Usuario usuario = obtenerUsuario(idUsuario);
        usuario.eliminarCuenta(idCuenta);
        try {
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
            logger.log(Level.INFO, "Cuenta eliminada para usuario: {0}, Cuenta ID: {1}", new Object[]{idUsuario, idCuenta});
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Cuenta> obtenerCuentasUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        logger.log(Level.INFO, "Cuentas consultadas para usuario: {0}", idUsuario);
        return usuario.obtenerCuentas();
    }

    public Cuenta consultarDetallesCuentaUsuario(String idUsuario, String idCuenta) {
        Usuario usuario = obtenerUsuario(idUsuario);
        logger.log(Level.INFO, "Detalles de cuenta consultados para usuario: {0}, Cuenta ID: {1}", new Object[]{idUsuario, idCuenta});
        return usuario.obtenerCuenta(idCuenta);
    }

    public Collection<Transaccion> listarTransacciones() {
        logger.log(Level.INFO, "Lista de todas las transacciones consultada");
        return Collections.unmodifiableCollection(transacciones);
    }

    public Collection<Transaccion> filtrarTransaccionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        logger.log(Level.INFO, "Transacciones filtradas por fecha: desde {0} hasta {1}", new Object[]{fechaInicio, fechaFin});
        return transacciones.stream()
                .filter(t -> !t.getFecha().isBefore(fechaInicio) && !t.getFecha().isAfter(fechaFin))
                .collect(Collectors.toList());
    }

    public Transaccion obtenerDetallesTransaccion(String idTransaccion) {
        logger.log(Level.INFO, "Detalles de transacción consultados para transacción ID: {0}", idTransaccion);
        return transacciones.stream()
                .filter(t -> t.getIdTransaccion().equals(idTransaccion))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada con el ID: " + idTransaccion));
    }

    public void crearPresupuesto(String idUsuario, Presupuesto presupuesto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        usuario.agregarPresupuesto(presupuesto);
        try {
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
            logger.log(Level.INFO, "Presupuesto creado para usuario: {0}, Presupuesto: {1}", new Object[]{idUsuario, presupuesto.getIdPresupuesto()});
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarPresupuesto(String idUsuario, String idPresupuesto, String nombre, double montoTotalAsignado, Categoria categoria) {
        Usuario usuario = obtenerUsuario(idUsuario);
        usuario.actualizarPresupuesto(idPresupuesto, nombre, montoTotalAsignado, categoria);
        try {
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
            logger.log(Level.INFO, "Presupuesto actualizado para usuario: {0}, Presupuesto ID: {1}", new Object[]{idUsuario, idPresupuesto});
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarPresupuesto(String idUsuario, String idPresupuesto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        usuario.eliminarPresupuesto(idPresupuesto);
        try {
            Persistencia.guardarBilleteraEnBinario(this);
            Persistencia.guardarBilleteraEnXML(this);
            logger.log(Level.INFO, "Presupuesto eliminado para usuario: {0}, Presupuesto ID: {1}", new Object[]{idUsuario,idPresupuesto});
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public double consultarMontoGastado(String idUsuario, String idPresupuesto) {
            Usuario usuario = obtenerUsuario(idUsuario);
            double montoGastado = usuario.consultarMontoGastado(idPresupuesto);
            logger.log(Level.INFO, "Monto gastado consultado para el usuario: {0}, presupuesto: {1}", new Object[]{idUsuario, idPresupuesto});
            return montoGastado;
        }
        public double consultarSaldoPresupuesto(String idUsuario, String idPresupuesto) {
            Usuario usuario = obtenerUsuario(idUsuario);
            double saldoPresupuesto = usuario.consultarSaldoPresupuesto(idPresupuesto);
            logger.log(Level.INFO, "Saldo del presupuesto consultado para el usuario: {0}, presupuesto: {1}", new Object[]{idUsuario, idPresupuesto});
            return saldoPresupuesto;
        }

        private double calcularGastoPorCategoriaRecursivo(List<Transaccion> transacciones, Categoria categoria, int indice) {
            if (indice == transacciones.size()) {
                return 0;
            }
            Transaccion transaccion = transacciones.get(indice);
            double monto = transaccion.getCategoria().equals(categoria) ? transaccion.getMontoTransaccion() : 0;
            return monto + calcularGastoPorCategoriaRecursivo(transacciones, categoria, indice + 1);
        }

        public double consultarGastoPorCategoriaRecursivo(String idUsuario, Categoria categoria) {
            Usuario usuario = obtenerUsuario(idUsuario);
            List<Transaccion> transaccionesUsuario = new ArrayList<>(usuario.obtenerTransacciones());
            double gastoTotal = calcularGastoPorCategoriaRecursivo(transaccionesUsuario, categoria, 0);
            logger.log(Level.INFO, "Gasto total por categoría consultado recursivamente para el usuario: {0}, categoría: {1}", new Object[]{idUsuario, categoria.getNombre()});
            return gastoTotal;
        }

    public void crearCategoria(String idCategoria, String nombre) {
            Categoria nuevaCategoria = new Categoria(idCategoria, nombre);
            categorias.add(nuevaCategoria);
            logger.log(Level.INFO, "Categoría creada: {0}", nuevaCategoria);
        }

        public void asignarCategoriaATransaccion(String idTransaccion, Categoria categoria) {
            for (Transaccion transaccion : transacciones) {
                if (transaccion.getIdTransaccion().equals(idTransaccion)) {
                    transaccion.setCategoria(categoria);
                    logger.log(Level.INFO, "Categoría asignada a la transacción: {0}, categoría: {1}", new Object[]{idTransaccion, categoria.getNombre()});
                    return;
                }
            }
            throw new IllegalArgumentException("Transacción no encontrada con el ID: " + idTransaccion);
        }

        public void actualizarCategoria(String idCategoria, String nuevoNombre) {
            for (Categoria categoria : categorias) {
                if (categoria.getIdCategoria().equals(idCategoria)) {
                    categoria.setNombre(nuevoNombre);
                    logger.log(Level.INFO, "Categoría actualizada: {0}, nuevo nombre: {1}", new Object[]{idCategoria, nuevoNombre});
                    return;
                }
            }
            throw new IllegalArgumentException("Categoría no encontrada con el ID: " + idCategoria);
        }

        public void eliminarCategoria(String idCategoria) {
            boolean eliminada = categorias.removeIf(categoria -> categoria.getIdCategoria().equals(idCategoria));
            if (eliminada) {
                logger.log(Level.INFO, "Categoría eliminada: {0}", idCategoria);
            } else {
                throw new IllegalArgumentException("Categoría no encontrada con el ID: " + idCategoria);
            }
        }

        public List<Categoria> listarCategorias() {
            logger.log(Level.INFO, "Categorías listadas");
            return Collections.unmodifiableList(categorias);
        }

        public void agregarTransaccion(Transaccion transaccion) {
            transacciones.add(transaccion);
            logger.log(Level.INFO, "Transacción agregada: {0}", transaccion.getIdTransaccion());
        }

        public Map<Categoria, Double> gastosMasComunes() {
            Map<Categoria, Double> gastos = transacciones.stream()
                    .filter(transaccion -> transaccion instanceof Retiro || transaccion instanceof Transferencia)
                    .collect(Collectors.groupingBy(Transaccion::getCategoria, Collectors.summingDouble(Transaccion::getMontoTransaccion)))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<Categoria, Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            logger.log(Level.INFO, "Gastos más comunes consultados");
            return gastos;
        }

        public List<Usuario> usuariosConMasTransacciones() {
            List<Usuario> usuariosOrdenados = usuarios.stream()
                    .sorted((u1, u2) -> Integer.compare(u2.getTransacciones().size(), u1.getTransacciones().size()))
                    .collect(Collectors.toList());
            logger.log(Level.INFO, "Usuarios con más transacciones consultados");
            return usuariosOrdenados;
        }

        public double saldoPromedioUsuarios() {
            double saldoPromedio = usuarios.stream()
                    .mapToDouble(Usuario::getSaldoTotal)
                    .average()
                    .orElse(0.0);
            logger.log(Level.INFO, "Saldo promedio de usuarios consultado");
            return saldoPromedio;
        }
        public void generarReporteCSV(String idUsuario) throws IOException {
            Usuario usuario = obtenerUsuario(idUsuario);
            ReporteUtil.generarReporteCSV("RutaCSV", new ArrayList<>(usuario.consultarTransacciones()));
            logger.log(Level.INFO, "Reporte CSV generado correctamente para el usuario: {0}", idUsuario);
        }
        public void generarReportePDF(String idUsuario) throws IOException {
            Usuario usuario = obtenerUsuario(idUsuario);
            ReporteUtil.generarReportePDF("RutaPDF", new ArrayList<>(usuario.consultarTransacciones()));
            logger.log(Level.INFO, "Reporte PDF generado correctamente para el usuario: {0}", idUsuario);
    }

    public void iniciarGeneracionInformeFinanciero(String idUsuario) {
            InformeFinanciero informeFinanciero = new InformeFinanciero(idUsuario);
            informeFinanciero.start();
    }

}

