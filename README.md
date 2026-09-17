# Práctica Semana 5: Sistema de Gestión y Solicitudes de Clientes

Aplicación de escritorio desarrollada en **JavaFX 21+** y **Maven**, diseñada con arquitectura MVC, navegación desacoplada mediante FXML y persistencia temporal en memoria.

## Estructura del Proyecto

```plaintext
ActividadPracticaPAE-Semana5/
├── src/main/
│   ├── java/com/example/actividadpracticapaesemana5/
│   │   ├── Launcher.java
│   │   ├── application/
│   │   │   └── ClientesApplication.java
│   │   ├── controller/
│   │   │   ├── LoginController.java
│   │   │   ├── MenuPrincipalController.java
│   │   │   ├── ClienteRegistroController.java
│   │   │   ├── ClienteConsultaController.java
│   │   │   └── ClienteDetalleController.java
│   │   ├── model/
│   │   │   ├── Cliente.java
│   │   │   └── Usuario.java
│   │   ├── util/
│   │   │   └── SceneManager.java
│   │   └── module-info.java
│   └── resources/com/example/actividadpracticapaesemana5/
│       └── fxml/
│           ├── login-view.fxml
│           ├── menu-principal-view.fxml
│           ├── cliente-registro-view.fxml
│           ├── cliente-consulta-view.fxml
│           └── cliente-detalle-view.fxml
├── pom.xml
└── README.md
```

## Distribución de Responsabilidades

### `application/`

- **`Launcher.java`**: Contiene únicamente el método `main(String[] args)` que delega en `ClientesApplication.main(args)`. Resuelve el error de inicio de JavaFX sin necesidad de configurar parámetros de máquina virtual en ejecución.
- **`ClientesApplication.java`**: Punto de entrada de JavaFX (`extends Application`). Instancia el `Stage` principal, inicializa el `SceneManager` y despliega la vista de autenticación (`login-view.fxml`).

### `model/`

- **`Cliente.java`**: POJO central con los atributos requeridos por la rúbrica:
    - `nombres`, `apellidos`, `tipoCliente` (`ComboBox`), `ciudad` (`ComboBox`).
    - `fechaNacimiento` (`LocalDate` vía `DatePicker`).
    - `tipoSolicitud` (`RadioButton` / `ToggleGroup`).
    - `serviciosInteres` (`List` recopilados de los `CheckBox`).
    - `rutaFotografia` (`String` absoluto retornado por `FileChooser`).
    - Métodos de utilidad como `getNombreCompleto()` para mapeo directo en tabla.
- **`Usuario.java`**: Modelo para validación de credenciales en el inicio de sesión.
- **Manejo de Datos en Memoria**: La lista global `ObservableList` puede mantenerse como colección estática (por ejemplo, dentro de `SceneManager` o `ClientesApplication`) para que el registro actualice la consulta en tiempo real sin base de datos.

### `controller/`

- **`LoginController.java`**:
    - Controla acceso mediante `TextField` (usuario) y `PasswordField` (contraseña).
    - Validaciones con diálogos `Alert` (Error/Advertencia si hay campos vacíos).
    - Salida con confirmación (`Alert.AlertType.CONFIRMATION`).
    - **KeyEvent**: Permite enviar el formulario presionando `ENTER` o cancelar con `ESC`.

- **`MenuPrincipalController.java`**:
    - Estructura principal con `MenuBar`, `ToolBar` y `ContextMenu`.
    - Navegación hacia Registro y Consulta.
    - Selector de carpetas (`DirectoryChooser`) para exportación/respaldos.
    - Diálogo de entrada (`TextInputDialog`) para búsquedas rápidas o notas.

- **`ClienteRegistroController.java`**:
    - Manejo de controles interactivos: `ComboBox`, `DatePicker`, `ToggleGroup` para `RadioButton` y `CheckBox`.
    - Carga de imagen con **`FileChooser`** (filtros `.png`, `.jpg`) reflejada en el `ImageView`.
    - Validaciones antes de guardar y confirmaciones de operación exitosa (`Alert.AlertType.INFORMATION`).

- **`ClienteConsultaController.java`**:
    - Configuración del `TableView` asociado a la lista de clientes.
    - **MouseEvent**: Evento de doble clic (`event.getClickCount() == 2`) sobre una fila para abrir la ventana modal de detalle.

- **`ClienteDetalleController.java`**:
    - Vista de solo lectura.
    - Expone el método receptor `public void setCliente(Cliente c)` para renderizar los datos y la imagen del cliente seleccionado.

### `util/`

- **`SceneManager.java`**:
    - Orquesta la navegación y el cambio de vistas sobre el `Stage` principal.
    - Permite abrir ventanas secundarias modales (`Modality.APPLICATION_MODAL`).
    - Obtiene la referencia del controlador de destino (`loader.getController()`) para permitir la inyección de datos, por ejemplo, pasar el `Cliente` seleccionado al `ClienteDetalleController`.
    - Centraliza utilitarios para invocar `Alert` estándar y diálogos interactivos.

## Flujo de Navegación y Paso de Datos

```plaintext
[login-view.fxml] ──(Validación OK)──► [menu-principal-view.fxml]
                                                │
                 ┌──────────────────────────────┴──────────────────────────────┐
                 ▼                                                             ▼
    [cliente-registro-view.fxml]                                 [cliente-consulta-view.fxml]
                 │                                                             │
      (Guarda en ObservableList)                                     (Doble clic en fila)
                 │                                                             │
                 └──────────────────► [TableView actualiza] ◄──────────────────┘
                                                │
                                                ▼ (Paso de objeto seleccionado)
                                   [cliente-detalle-view.fxml]
```

1. **Registro ➔ Consulta**: Al pulsar *Guardar*, se valida el formulario, se crea la instancia `Cliente` y se añade a la `ObservableList`. El `TableView` refleja el cambio de forma automática.
2. **Consulta ➔ Detalle**: Al detectar doble clic en una fila del `TableView`, el controlador obtiene la instancia seleccionada (`tblClientes.getSelectionModel().getSelectedItem()`), carga `cliente-detalle-view.fxml`, obtiene su controlador e invoca `controller.setCliente(seleccionado)`.

## Cobertura de Eventos y Controles Requeridos

| **Categoría** | **Implementación en el Proyecto** |
|---|---|
| **ActionEvent** | Botones de Inicio de Sesión, Guardar, Limpiar, Cancelar, y opciones de `MenuBar` / `ToolBar`. |
| **MouseEvent** | Doble clic en filas del `TableView` de consulta para desplegar el detalle (`event.getClickCount() == 2`). |
| **KeyEvent** | Detección de tecla `ENTER` en el formulario de login y restricción de caracteres o escape `ESC`. |
| **Alerts** | Informativa (éxito al guardar), Advertencia (campos incompletos), Confirmación (salir del sistema). |
| **Dialogs** | `TextInputDialog` para solicitudes de texto puntuales desde el menú principal. |
| **Choosers** | `FileChooser` para seleccionar la fotografía (`*.jpg`, `*.png`) y `DirectoryChooser` para elegir rutas de guardado. |

## Configuración Modular (`module-info.java`)

Asegurar la configuración de apertura y exportación reflexiva para que FXML y los enlaces de tabla funcionen sin excepciones de acceso:

```java
module com.example.actividadpracticapaesemana5 {
    requires javafx.controls;
    requires javafx.fxml;

    // FXML necesita instanciar controladores e inyectar campos @FXML
    opens com.example.actividadpracticapaesemana5.controller to javafx.fxml;

    // PropertyValueFactory del TableView requiere acceso por reflexión a los getters
    opens com.example.actividadpracticapaesemana5.model to javafx.base;

    exports com.example.actividadpracticapaesemana5.application;
}
```

## Guía Rápida para Enlace y Archivos

### 1. Vincular columnas del TableView

```java
colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
colTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));
```

### 2. Carga dinámica de imágenes con FileChooser

```java
FileChooser fileChooser = new FileChooser();
fileChooser.setTitle("Seleccionar Fotografía del Cliente");
fileChooser.getExtensionFilters().addAll(
    new FileChooser.ExtensionFilter(
        "Imágenes (*.png, *.jpg, *.jpeg)",
        "*.png",
        "*.jpg",
        "*.jpeg"
    )
);

File archivoSeleccionado = fileChooser.showOpenDialog(
    btnSeleccionarFoto.getScene().getWindow()
);

if (archivoSeleccionado != null) {
    rutaFotoSeleccionada = archivoSeleccionado.getAbsolutePath();
    imgFotografia.setImage(
        new Image(archivoSeleccionado.toURI().toString())
    );
}
```

### 3. Doble clic en TableView

```java
tblClientes.setOnMouseClicked(event -> {
    if (event.getClickCount() == 2 &&
        tblClientes.getSelectionModel().getSelectedItem() != null) {

        Cliente seleccionado =
            tblClientes.getSelectionModel().getSelectedItem();

        SceneManager.abrirModalDetalle(seleccionado);
    }
});
```