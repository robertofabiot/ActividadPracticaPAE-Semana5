module com.example.actividadpracticapaesemana5 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.actividadpracticapaesemana5 to javafx.fxml;
    opens com.example.actividadpracticapaesemana5.controller to javafx.fxml;
    opens com.example.actividadpracticapaesemana5.model to javafx.base, javafx.fxml;
    opens com.example.actividadpracticapaesemana5.application to javafx.graphics, javafx.fxml;

    exports com.example.actividadpracticapaesemana5;
    exports com.example.actividadpracticapaesemana5.application;
    exports com.example.actividadpracticapaesemana5.controller;
    exports com.example.actividadpracticapaesemana5.model;
    exports com.example.actividadpracticapaesemana5.util;
}