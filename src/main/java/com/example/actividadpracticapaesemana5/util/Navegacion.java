package com.example.actividadpracticapaesemana5.util;

import javafx.stage.Stage;

public class Navegacion {
    public static void cambiarVentana(Stage stage, String rutaFXML, String titulo) {
        SceneManager.cambiarEscena(stage, rutaFXML, titulo);
    }
}
