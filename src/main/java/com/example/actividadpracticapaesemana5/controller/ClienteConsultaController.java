package com.example.actividadpracticapaesemana5.controller;

import com.example.actividadpracticapaesemana5.model.Cliente;
import com.example.actividadpracticapaesemana5.util.Navegacion;
import com.example.actividadpracticapaesemana5.util.Sesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ClienteConsultaController {

    private static final String RUTA_MENU =
            "/com/example/actividadpracticapaesemana5/fxml/menu-principal-view.fxml";
    private static final String RUTA_REGISTRO =
            "/com/example/actividadpracticapaesemana5/fxml/cliente-registro-view.fxml";
    private static final String RUTA_CONSULTA =
            "/com/example/actividadpracticapaesemana5/fxml/cliente-consulta-view.fxml";
    private static final String RUTA_DETALLE =
            "/com/example/actividadpracticapaesemana5/fxml/cliente-detalle-view.fxml";
    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipoCliente;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, String> colFechaNacimiento;
    @FXML private TableColumn<Cliente, String> colTipoSolicitud;
    @FXML private TableColumn<Cliente, String> colFechaHora;

    @FXML private ToolBar toolBar;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private javafx.collections.transformation.FilteredList<Cliente> clientesFiltrados;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaComoTexto"));
        colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));
        // El getter en Cliente se llama getFechaHoraRegistroComoTexto -> propiedad "fechaHoraRegistroComoTexto"
        colFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHoraRegistroComoTexto"));

        // Cargar los clientes registrados desde Sesion
        cargarClientes();
    }

    @FXML
    public void onBuscarClick(ActionEvent event) {
        if (txtBuscar != null && clientesFiltrados != null) {
            String filterText = txtBuscar.getText();
            clientesFiltrados.setPredicate(cliente -> {
                if (filterText == null || filterText.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = filterText.toLowerCase();
                if (cliente.getNombreCompleto() != null && cliente.getNombreCompleto().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cliente.getCiudad() != null && cliente.getCiudad().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        }
    }

    private void cargarClientes() {
        listaClientes.setAll(Sesion.clientes);
        clientesFiltrados = new javafx.collections.transformation.FilteredList<>(listaClientes, b -> true);
        javafx.collections.transformation.SortedList<Cliente> sortedData = new javafx.collections.transformation.SortedList<>(clientesFiltrados);
        sortedData.comparatorProperty().bind(tblClientes.comparatorProperty());
        tblClientes.setItems(sortedData);
    }

    private Stage stage() {
        return (Stage) tblClientes.getScene().getWindow();
    }

    @FXML
    private void onTableClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado != null) {
                Sesion.clienteSeleccionado = clienteSeleccionado;
                Navegacion.cambiarVentana(stage(), RUTA_DETALLE, "Detalle del Cliente");
            }
        }
    }

    // ── Navegación desde menú y toolbar del FXML ──────────────────────────────
    @FXML public void abrirMenu(ActionEvent event) {
        Navegacion.cambiarVentana(stage(), RUTA_MENU, "Menú Principal");
    }

    @FXML public void abrirRegistro(ActionEvent event) {
        Navegacion.cambiarVentana(stage(), RUTA_REGISTRO, "Registro de Cliente");
    }

    @FXML public void abrirConsulta(ActionEvent event) {
        // Ya estamos en esta ventana.
    }

    @FXML public void cerrarSesion(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cerrar la sesión actual?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().filter(b -> b == ButtonType.YES).ifPresent(b ->
                Navegacion.cambiarVentana(stage(), "/com/example/actividadpracticapaesemana5/fxml/login-view.fxml", "Inicio de Sesión"));
    }

    // ── Métodos del menú Navegación interno (legacy) ───────────────────────────
    @FXML public void irAlMenu(ActionEvent event)    { abrirMenu(event); }
    @FXML public void irARegistro(ActionEvent event) { abrirRegistro(event); }
    @FXML public void irAConsulta(ActionEvent event) { /* ya estamos aquí */ }

    @FXML private void onVolverMenu(ActionEvent event) { abrirMenu(event); }

    @FXML private void salir(ActionEvent e) { System.exit(0); }

    @FXML private void acercaDe(ActionEvent e) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Sistema de Solicitudes");
        info.setContentText("Versión 1.0 - Semana 5\n");
        info.showAndWait();
    }

}
