module com.example.actividadpracticapaesemana5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.actividadpracticapaesemana5 to javafx.fxml;
    exports com.example.actividadpracticapaesemana5;
}