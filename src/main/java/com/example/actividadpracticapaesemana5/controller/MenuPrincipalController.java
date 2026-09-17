package com.example.actividadpracticapaesemana5.controller;

import com.example.actividadpracticapaesemana5.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class MenuPrincipalController {
    @FXML private Label lblBienvenida;
    @FXML private javafx.scene.control.ToolBar toolBar;

    @FXML
    public void initialize() {
        lblBienvenida.setText("Sistema de Clientes");
        SceneManager.configurarTooltipsRapidos(toolBar);
    }

    @FXML
    public void abrirMenu(ActionEvent event) {
        // Ya está en el menú principal - no hace nada
    }

    @FXML
    public void abrirRegistro(ActionEvent event) {
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        SceneManager.cambiarEscena(stage,
                "/com/example/actividadpracticapaesemana5/fxml/cliente-registro-view.fxml",
                "Registro de Cliente");
    }

    @FXML
    public void abrirConsulta(ActionEvent event) {
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        SceneManager.cambiarEscena(stage,
                "/com/example/actividadpracticapaesemana5/fxml/cliente-consulta-view.fxml",
                "Consulta de Clientes");
    }

    @FXML
    public void cerrarSesion(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText("¿Desea cerrar la sesión actual?");
        Optional<ButtonType> r = confirm.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            Stage stage = (Stage) lblBienvenida.getScene().getWindow();
            SceneManager.cambiarEscena(stage,
                    "/com/example/actividadpracticapaesemana5/fxml/login-view.fxml",
                    "Inicio de Sesión");
        }
    }

    @FXML
    public void salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void acercaDe(ActionEvent event) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("Sistema de Solicitudes");
        info.setContentText("Versión 1.0 - Semana 5\nJavaFX + Scene Builder");
        info.showAndWait();
    }

}
