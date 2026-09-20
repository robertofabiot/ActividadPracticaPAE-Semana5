package com.example.actividadpracticapaesemana5.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private SceneManager() {
    }

    public static void cambiarEscena(Stage stage, String rutaFxml, String titulo) {
        try {
            Parent raiz = FXMLLoader.load(Objects.requireNonNull(
                    SceneManager.class.getResource(rutaFxml), "No se encontró: " + rutaFxml));
            configurarTooltipsRapidos(raiz);
            stage.setScene(new Scene(raiz));
            stage.setTitle(titulo);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("No fue posible abrir la vista " + rutaFxml, e);
        }
    }

    public static void configurarTooltipsRapidos(Parent root) {
        if (root == null) return;
        recorrerNodos(root);
    }

    private static void recorrerNodos(Parent parent) {
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (child instanceof Control control && control.getTooltip() != null) {
                control.getTooltip().setShowDelay(Duration.millis(100));
                control.getTooltip().setShowDuration(Duration.seconds(10));
            }
            if (child instanceof Parent subParent) {
                recorrerNodos(subParent);
            }
        }
    }
}
