package com.example.actividadpracticapaesemana5.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegacion {
    public static void cambiarVentana(Stage stage, String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource(rutaFXML));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar el FXML: " + rutaFXML);
            e.printStackTrace();
        }
    }
}
