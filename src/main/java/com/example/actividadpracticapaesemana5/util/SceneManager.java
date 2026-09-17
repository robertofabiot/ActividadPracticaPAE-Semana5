package com.example.actividadpracticapaesemana5.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private SceneManager() {
    }

    public static void cambiarEscena(Stage stage, String rutaFxml, String titulo) {
        try {
            Parent raiz = FXMLLoader.load(Objects.requireNonNull(
                    SceneManager.class.getResource(rutaFxml), "No se encontró: " + rutaFxml));
            stage.setScene(new Scene(raiz));
            stage.setTitle(titulo);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("No fue posible abrir la vista " + rutaFxml, e);
        }
    }
}
