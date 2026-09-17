package com.example.actividadpracticapaesemana5.controller;

import com.example.actividadpracticapaesemana5.model.Cliente;
import com.example.actividadpracticapaesemana5.util.SceneManager;
import com.example.actividadpracticapaesemana5.util.Sesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Optional;


public class ClienteConsultaController {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipoCliente;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, String> colFechaNacimiento;
    @FXML private TableColumn<Cliente, String> colTipoSolicitud;

    @FXML private ToolBar toolBar;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));

        SceneManager.configurarTooltipsRapidos(toolBar);
        cargarDatosPrueba();
    }

    @FXML
    private void onTableClicked(MouseEvent event) {
        if (event.getClickCount() == 2 && tblClientes.getSelectionModel().getSelectedItem() != null) {
            Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();
            Sesion.clienteSeleccionado = clienteSeleccionado;
            abrirVentanaDetalle(clienteSeleccionado);
        }
    }

    private void abrirVentanaDetalle(Cliente cliente) {
        Stage stage = (Stage) tblClientes.getScene().getWindow();
        SceneManager.cambiarEscena(stage,
                "/com/example/actividadpracticapaesemana5/fxml/cliente-detalle-view.fxml",
                "Detalle del Cliente - " + cliente.getNombreCompleto());
    }

    @FXML
    private void onVolverMenu(ActionEvent event) {
        abrirMenu(event);
    }

    @FXML private void abrirMenu(ActionEvent e) {
        Stage stage = (Stage) tblClientes.getScene().getWindow();
        SceneManager.cambiarEscena(stage,
                "/com/example/actividadpracticapaesemana5/fxml/menu-principal-view.fxml",
                "Menú Principal");
    }
    @FXML private void abrirRegistro(ActionEvent e) {
        Stage stage = (Stage) tblClientes.getScene().getWindow();
        SceneManager.cambiarEscena(stage, "/com/example/actividadpracticapaesemana5/fxml/cliente-registro-view.fxml", "Registro de Cliente");
    }
    @FXML private void abrirConsulta(ActionEvent e) { cargarDatosPrueba(); }
    @FXML private void cerrarSesion(ActionEvent e) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText("¿Desea cerrar la sesión actual?");
        Optional<ButtonType> r = confirm.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            Stage stage = (Stage) tblClientes.getScene().getWindow();
            SceneManager.cambiarEscena(stage, "/com/example/actividadpracticapaesemana5/fxml/login-view.fxml", "Inicio de Sesión");
        }
    }
    @FXML private void salir(ActionEvent e) {
        System.exit(0);
    }
    @FXML private void acercaDe(ActionEvent e) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Sistema de Solicitudes");
        info.setContentText("Versión 1.0 - Semana 5\nJavaFX + Scene Builder");
        info.showAndWait();
    }

    private void cargarDatosPrueba() {
        listaClientes.setAll(Sesion.clientes);
        tblClientes.setItems(listaClientes);
    }
}
