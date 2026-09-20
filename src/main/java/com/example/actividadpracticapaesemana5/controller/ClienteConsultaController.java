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

    @FXML private MenuItem miIrMenu;
    @FXML private MenuItem miIrRegistro;
    @FXML private MenuItem miIrConsulta;

    @FXML private ToolBar toolBar;
    @FXML private TextField txtBuscar;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private javafx.collections.transformation.FilteredList<Cliente> clientesFiltrados;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaComoTexto"));
        colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));
        colFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHoraComoTexto"));

        // Cargar los clientes registrados desde Sesion
        cargarClientes();

        // Configurar el buscador
        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                clientesFiltrados.setPredicate(cliente -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (cliente.getNombreCompleto() != null && cliente.getNombreCompleto().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cliente.getCiudad() != null && cliente.getCiudad().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
        }

        // Desactivar el MenuItem de la ventana actual
        if (miIrConsulta != null) {
            miIrConsulta.setDisable(true);
        }
    }
    private void cargarClientes() {
        listaClientes.setAll(Sesion.clientes);
        clientesFiltrados = new javafx.collections.transformation.FilteredList<>(listaClientes, b -> true);
        javafx.collections.transformation.SortedList<Cliente> sortedData = new javafx.collections.transformation.SortedList<>(clientesFiltrados);
        sortedData.comparatorProperty().bind(tblClientes.comparatorProperty());
        tblClientes.setItems(sortedData);
    }

    @FXML
    private void onTableClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado != null) {
                Sesion.clienteSeleccionado = clienteSeleccionado;   // 👈 guarda en Sesion
                Stage stage = (Stage) tblClientes.getScene().getWindow();
                Navegacion.cambiarVentana(stage, RUTA_DETALLE, "Detalle del Cliente");
            }
        }
    }

    @FXML
    private void onVolverMenu(ActionEvent event) {
        Stage stage = (Stage) tblClientes.getScene().getWindow();
        Navegacion.cambiarVentana(stage, RUTA_MENU, "Menú Principal");
    }

    // Menú de navegación
    @FXML
    public void irAlMenu(ActionEvent event) {
        Stage stage = (Stage) tblClientes.getScene().getWindow();
        Navegacion.cambiarVentana(stage, RUTA_MENU, "Menú Principal");
    }

    @FXML
    public void irARegistro(ActionEvent event) {
        Stage stage = (Stage) tblClientes.getScene().getWindow();
        Navegacion.cambiarVentana(stage, RUTA_REGISTRO, "Registro de Cliente");
    }
    @FXML private void salir(ActionEvent e) {
        System.exit(0);
    }
    @FXML private void acercaDe(ActionEvent e) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Sistema de Solicitudes");
        info.setContentText("Versión 1.0 - Semana 5\n");
        info.showAndWait();
    }

    @FXML
    public void irAConsulta(ActionEvent event) {
        // Ya estamos en esta ventana. El MenuItem está desactivado.
        // Este metodo existe por referencia del FXML
    }

}
