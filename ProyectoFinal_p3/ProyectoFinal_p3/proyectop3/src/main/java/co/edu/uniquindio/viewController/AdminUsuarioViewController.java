package co.edu.uniquindio.viewController;

import co.edu.uniquindio.App;
import co.edu.uniquindio.controller.AdminUsuarioController;
import co.edu.uniquindio.model.BilleteraVirtual;
import co.edu.uniquindio.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class AdminUsuarioViewController {
    App app;
    private AdminUsuarioController adminUsuarioController = AdminUsuarioController.getInstance();

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public AdminUsuarioController getAdminUsuarioController() {
        return adminUsuarioController;
    }

    @FXML
    private TableColumn<Usuario, String> columnNombreUsuario;

    @FXML
    private TableView<Usuario> tableViewUsuarios;

    @FXML
    private TextField txtDireccion;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private TextField txtNumeroTelefono;

    @FXML
    private TableColumn<Usuario, String> columnCorreoUsuario;

    @FXML
    private Button btnAgregar;

    @FXML
    private TableColumn<Usuario, String> columnId;

    @FXML
    private TextField txtCorreoElectronico;

    @FXML
    private Button btnActualizar;

    @FXML
    private TextField txtId;

    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private Usuario usuarioSeleccionado;

    @FXML
    void agregarUsuarioEvent(ActionEvent event) {
        adminUsuarioController.crearUsuario(txtId.getText(), txtNombreCompleto.getText(),
                txtCorreoElectronico.getText(), txtNumeroTelefono.getText(), txtDireccion.getText());
        actualizarVistaUsuarios();
    }

    @FXML
    void actualizarUsuarioEvent(ActionEvent event) throws JAXBException, IOException {
        adminUsuarioController.actualizarUsuario(usuarioSeleccionado.getIdUsuario(),
                txtNombreCompleto.getText(), txtCorreoElectronico.getText(),
                txtNumeroTelefono.getText(), txtDireccion.getText());
        tableViewUsuarios.refresh();
        limpiarCamposUsuario();
    }

    @FXML
    void eliminarUsuarioEvent(ActionEvent event) {
        adminUsuarioController.eliminarUsuario(usuarioSeleccionado.getIdUsuario());
        listaUsuarios.remove(usuarioSeleccionado);
        limpiarCamposUsuario();
    }

    @FXML
    void initialize() {
        inicializarView();
    }

    private void inicializarView() {
        obtenerUsuarios();
        inicializarBinding();
        tableViewUsuarios.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios.setAll(adminUsuarioController.obtenerUsuarios());
    }

    private void inicializarBinding() {
        columnNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCompleto()));
        columnCorreoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        columnId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario()));
    }

    private void listenerSelection() {
        tableViewUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(Usuario usuarioSeleccionado) {
        if (usuarioSeleccionado != null) {
            txtNombreCompleto.setText(usuarioSeleccionado.getNombreCompleto());
            txtDireccion.setText(usuarioSeleccionado.getDireccion());
            txtCorreoElectronico.setText(usuarioSeleccionado.getCorreo());
            txtNumeroTelefono.setText(usuarioSeleccionado.getTelefono());
            txtId.setText(usuarioSeleccionado.getIdUsuario());
        }
    }

    private void limpiarCamposUsuario() {
        txtNombreCompleto.clear();
        txtDireccion.clear();
        txtNumeroTelefono.clear();
        txtCorreoElectronico.clear();
        txtId.clear();
    }

    private void actualizarVistaUsuarios() {
        listaUsuarios.setAll(adminUsuarioController.obtenerUsuarios());
        tableViewUsuarios.refresh();
        limpiarCamposUsuario();
    }
}
