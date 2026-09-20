package com.example.actividadpracticapaesemana5.controller;

import com.example.actividadpracticapaesemana5.model.Cliente;
import com.example.actividadpracticapaesemana5.util.SceneManager;
import com.example.actividadpracticapaesemana5.util.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ClienteRegistroController {
    private static final String RUTA_MENU = "/com/example/actividadpracticapaesemana5/fxml/menu-principal-view.fxml";
    @FXML private TextField txtNombres, txtApellidos, txtTelefono;
    @FXML private ComboBox<String> cmbTipoCliente, cmbCiudad;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ToggleGroup grupoSolicitud;
    @FXML private RadioButton rbInformacion;
    @FXML private CheckBox chkInternet, chkTelefonia, chkSoporte;
    @FXML private ImageView imgFotografia;
    @FXML private Label lblEstado;
    @FXML private ToolBar toolBar;
    private String rutaFotografia;

    @FXML public void initialize() {
        cmbTipoCliente.getItems().setAll("Natural", "Empresa", "Institución");
        cmbCiudad.getItems().setAll("Managua", "León", "Granada", "Masaya", "Estelí", "Matagalpa");
        aplicarFormato(txtNombres, "[A-Za-zÁÉÍÓÚáéíóúÑñÜü .'-]*");
        aplicarFormato(txtApellidos, "[A-Za-zÁÉÍÓÚáéíóúÑñÜü .'-]*");
        aplicarFormato(txtTelefono, "\\d{0,8}");
        dpFechaNacimiento.setDayCellFactory(p -> new DateCell() { @Override public void updateItem(LocalDate fecha, boolean empty) { super.updateItem(fecha, empty); setDisable(empty || fecha.isAfter(LocalDate.now())); }});
        SceneManager.configurarTooltipsRapidos(toolBar);
    }
    private void aplicarFormato(TextField campo, String patron) { UnaryOperator<TextFormatter.Change> filtro = c -> c.getControlNewText().matches(patron) ? c : null; campo.setTextFormatter(new TextFormatter<>(filtro)); }
    @FXML private void avanzarApellidos(ActionEvent e) { txtApellidos.requestFocus(); }
    @FXML private void avanzarTelefono(ActionEvent e) { txtTelefono.requestFocus(); }
    @FXML private void avanzarTipoCliente(ActionEvent e) { cmbTipoCliente.requestFocus(); }
    @FXML private void avanzarCiudad(ActionEvent e) { cmbCiudad.requestFocus(); }
    @FXML private void avanzarFecha(ActionEvent e) { dpFechaNacimiento.requestFocus(); }
    @FXML private void avanzarSolicitud(ActionEvent e) { rbInformacion.requestFocus(); }
    @FXML private void seleccionarFotografia(ActionEvent e) {
        FileChooser chooser = new FileChooser(); chooser.setTitle("Seleccionar fotografía del cliente"); chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File archivo = chooser.showOpenDialog(stage());
        if (archivo != null) { rutaFotografia = archivo.getAbsolutePath(); imgFotografia.setImage(new Image(archivo.toURI().toString())); lblEstado.setText("Fotografía seleccionada: " + archivo.getName()); }
    }
    @FXML private void guardarCliente(ActionEvent e) {
        if (!validar()) return;
        Cliente cliente = new Cliente(txtNombres.getText().trim(), txtApellidos.getText().trim(), cmbTipoCliente.getValue(), cmbCiudad.getValue(), dpFechaNacimiento.getValue(), ((RadioButton) grupoSolicitud.getSelectedToggle()).getText(), servicios(), rutaFotografia);
        cliente.setTelefono(txtTelefono.getText()); Sesion.clientes.add(cliente); limpiar(); lblEstado.setText("Cliente guardado correctamente. Puedes registrar el siguiente."); txtNombres.requestFocus();
    }
    private boolean validar() {
        if (txtNombres.getText().trim().isEmpty()) return error("Ingresa los nombres del cliente.", txtNombres);
        if (txtApellidos.getText().trim().isEmpty()) return error("Ingresa los apellidos del cliente.", txtApellidos);
        if (txtTelefono.getText().length() != 8) return error("El teléfono debe contener exactamente 8 dígitos.", txtTelefono);
        if (cmbTipoCliente.getValue() == null) return error("Selecciona el tipo de cliente.", cmbTipoCliente);
        if (cmbCiudad.getValue() == null) return error("Selecciona una ciudad.", cmbCiudad);
        if (dpFechaNacimiento.getValue() == null) return error("Selecciona la fecha de nacimiento.", dpFechaNacimiento);
        return grupoSolicitud.getSelectedToggle() != null || error("Selecciona el tipo de solicitud.", rbInformacion);
    }
    private boolean error(String texto, Node campo) { new Alert(Alert.AlertType.WARNING, texto, ButtonType.OK).showAndWait(); campo.requestFocus(); return false; }
    private List<String> servicios() { List<String> r = new ArrayList<>(); if(chkInternet.isSelected())r.add(chkInternet.getText()); if(chkTelefonia.isSelected())r.add(chkTelefonia.getText()); if(chkSoporte.isSelected())r.add(chkSoporte.getText()); return r; }
    @FXML private void limpiarFormulario(ActionEvent e) { limpiar(); lblEstado.setText("Formulario limpiado."); txtNombres.requestFocus(); }
    private void limpiar() { txtNombres.clear(); txtApellidos.clear(); txtTelefono.clear(); cmbTipoCliente.getSelectionModel().clearSelection(); cmbCiudad.getSelectionModel().clearSelection(); dpFechaNacimiento.setValue(null); grupoSolicitud.selectToggle(null); chkInternet.setSelected(false); chkTelefonia.setSelected(false); chkSoporte.setSelected(false); imgFotografia.setImage(null); rutaFotografia = null; }
    @FXML private void cancelar(ActionEvent e) { SceneManager.cambiarEscena(stage(), RUTA_MENU, "Menú Principal"); }
    @FXML private void abrirMenu(ActionEvent e) { SceneManager.cambiarEscena(stage(), RUTA_MENU, "Menú Principal"); }
    @FXML private void abrirRegistro(ActionEvent e) { limpiarFormulario(e); }
    @FXML private void abrirConsulta(ActionEvent e) { SceneManager.cambiarEscena(stage(), "/com/example/actividadpracticapaesemana5/fxml/cliente-consulta-view.fxml", "Consulta de Clientes"); }
    @FXML private void cerrarSesion(ActionEvent e) { if (new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cerrar la sesión actual?", ButtonType.YES, ButtonType.NO).showAndWait().filter(b -> b == ButtonType.YES).isPresent()) SceneManager.cambiarEscena(stage(), "/com/example/actividadpracticapaesemana5/fxml/login-view.fxml", "Inicio de Sesión"); }
    @FXML private void salir(ActionEvent e) { System.exit(0); }
    @FXML private void acercaDe(ActionEvent e) { Alert info = new Alert(Alert.AlertType.INFORMATION); info.setHeaderText("Sistema de Solicitudes"); info.setContentText("Versión 1.0 - Semana 5\nJavaFX + Scene Builder"); info.showAndWait(); }
    private Stage stage() { return (Stage) txtNombres.getScene().getWindow(); }
}
