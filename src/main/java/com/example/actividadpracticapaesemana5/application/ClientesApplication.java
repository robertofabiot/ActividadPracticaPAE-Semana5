package com.example.actividadpracticapaesemana5.application;

import com.example.actividadpracticapaesemana5.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientesApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.cambiarEscena(stage,
                "/com/example/actividadpracticapaesemana5/fxml/login-view.fxml",
                "Inicio de Sesión");
    }
}
