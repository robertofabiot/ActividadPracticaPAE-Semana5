package com.example.actividadpracticapaesemana5.controller;

import com.example.actividadpracticapaesemana5.model.Cliente;
import com.example.actividadpracticapaesemana5.util.SceneManager;
import com.example.actividadpracticapaesemana5.util.Sesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ClienteConsultaController {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipoCliente;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, String> colFechaNacimiento;
    @FXML private TableColumn<Cliente, String> colTipoSolicitud;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));

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
        Stage stage = (Stage) tblClientes.getScene().getWindow();
        SceneManager.cambiarEscena(stage,
                "/com/example/actividadpracticapaesemana5/fxml/menu-principal-view.fxml",
                "Menú Principal");
    }

    private void cargarDatosPrueba() {
        listaClientes.setAll(Sesion.clientes);
        tblClientes.setItems(listaClientes);
    }
}
