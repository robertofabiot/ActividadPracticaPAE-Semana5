package com.example.actividadpracticapaesemana5.controller;

import com.example.actividadpracticapaesemana5.model.Usuario;
import com.example.actividadpracticapaesemana5.util.SceneManager;
import com.example.actividadpracticapaesemana5.util.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnEntrar;

    private List<Usuario> listaUsuarios;

    @FXML
    public void initialize() {
        listaUsuarios = new ArrayList<>();
        // Primer elemento con nombre admin y contraseña admin
        listaUsuarios.add(new Usuario("admin", "admin"));
    }

    @FXML
    public void onEntrarClick(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if (usuario == null || usuario.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor ingrese su usuario y contraseña.", Alert.AlertType.WARNING);
            return;
        }

        Usuario usuarioAutenticado = autenticar(usuario.trim(), password);

        if (usuarioAutenticado != null) {
            Sesion.usuarioLogueado = usuarioAutenticado.getNombreDeUsuario();
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            SceneManager.cambiarEscena(stage,
                    "/com/example/actividadpracticapaesemana5/fxml/menu-principal-view.fxml",
                    "Menú Principal");
        } else {
            mostrarAlerta("Error de inicio de sesión", "Nombre de usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
            txtPassword.clear();
            txtPassword.requestFocus();
        }
    }

    private Usuario autenticar(String nombreDeUsuario, String contrasenia) {
        for (Usuario u : listaUsuarios) {
            if (u.getNombreDeUsuario().equals(nombreDeUsuario) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtUsuario.clear();
        txtPassword.clear();
        txtUsuario.requestFocus();
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void agregarUsuario(Usuario usuario) {
        if (this.listaUsuarios != null && usuario != null) {
            this.listaUsuarios.add(usuario);
        }
    }
}
