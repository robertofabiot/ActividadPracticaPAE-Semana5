package com.example.actividadpracticapaesemana5.controller;

import com.example.actividadpracticapaesemana5.model.Cliente;
import com.example.actividadpracticapaesemana5.util.SceneManager;
import com.example.actividadpracticapaesemana5.util.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class ClienteDetalleController {

    private static final String RUTA_CONSULTA =
            "/com/example/actividadpracticapaesemana5/fxml/cliente-consulta-view.fxml";
    private static final String RUTA_MENU =
            "/com/example/actividadpracticapaesemana5/fxml/menu-principal-view.fxml";

    @FXML private Label lblNombre;
    @FXML private Label lblTipo;
    @FXML private Label lblCiudad;
    @FXML private Label lblFecha;
    @FXML private Label lblSolicitud;
    @FXML private Label lblServicios;
    @FXML private ImageView imgFoto;

    @FXML
    public void initialize() {
        Cliente c = Sesion.clienteSeleccionado;

        if (c == null) {
            lblNombre.setText("(sin datos)");
            return;
        }

        lblNombre.setText(c.getNombreCompleto());
        lblTipo.setText(c.getTipoCliente() != null ? c.getTipoCliente() : "");
        lblCiudad.setText(c.getCiudad() != null ? c.getCiudad() : "");
        lblFecha.setText(c.getFechaComoTexto());   // formato dd/MM/yyyy
        lblSolicitud.setText(c.getTipoSolicitud() != null ? c.getTipoSolicitud() : "");
        lblServicios.setText(c.getServiciosComoTexto());

        cargarFotografia(c);
    }

    /**
     * Convierte la ruta String de la fotografía en una Image y la muestra.
     */
    private void cargarFotografia(Cliente c) {
        String ruta = c.getRutaFotografia();
        if (ruta == null || ruta.isEmpty()) {
            imgFoto.setImage(null);
            return;
        }

        File archivo = new File(ruta);
        if (archivo.exists()) {
            Image img = new Image(archivo.toURI().toString());
            imgFoto.setImage(img);
        } else {
            imgFoto.setImage(null);
            System.err.println("No se encontró la imagen: " + ruta);
        }
    }

    @FXML
    public void regresarConsulta(ActionEvent event) {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        SceneManager.cambiarEscena(stage, RUTA_CONSULTA, "Consulta de Clientes");
    }

    @FXML
    public void regresarMenu(ActionEvent event) {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        SceneManager.cambiarEscena(stage, RUTA_MENU, "Menú Principal");
    }
}
