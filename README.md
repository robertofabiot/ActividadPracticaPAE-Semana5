# Sistema de solicitudes de clientes

Aplicación de escritorio en JavaFX para iniciar sesión, registrar clientes, consultar los registros creados y ver su detalle. Los datos se mantienen únicamente en memoria mientras la aplicación está abierta.

## Tecnologías

- Java 21
- JavaFX 21.0.6 (`javafx-controls` y `javafx-fxml`)
- Maven
- FXML

## Ejecución

Desde la raíz del proyecto:

```bash
./mvnw javafx:run
```

Las credenciales incluidas para ingresar son:

```text
Usuario: admin
Contraseña: admin
```

## Funcionalidades disponibles

- Inicio de sesión con validación de campos vacíos y credenciales.
- Menú principal con `MenuBar`, `ToolBar`, botones y menú contextual para acceder al registro y la consulta.
- Registro de clientes con nombres, apellidos, teléfono, tipo de cliente, ciudad, fecha de nacimiento, tipo de solicitud, servicios y fotografía opcional.
- Validación del formulario antes de guardar:
  - Nombres y apellidos aceptan letras, espacios, apóstrofes, puntos y guiones.
  - El teléfono acepta únicamente números y debe tener ocho dígitos.
  - La fecha de nacimiento no permite fechas futuras.
  - Los campos obligatorios se comprueban con alertas de advertencia.
- Selección de fotografía mediante `FileChooser` y previsualización en el formulario.
- Navegación por teclado: Enter avanza en los campos de texto y, al guardar, se limpia el formulario y se enfoca nuevamente el campo de nombres.
- Consulta de clientes en un `TableView`.
- Apertura del detalle del cliente mediante doble clic sobre una fila de la consulta.
- Confirmación antes de abandonar un registro sin guardar, cerrar sesión o salir de la aplicación.

## Flujo de navegación

```text
Inicio de sesión → Menú principal → Registro de cliente
                                  → Consulta de clientes → Detalle de cliente
```

`SceneManager` cambia la escena del `Stage` principal. Los clientes registrados se almacenan en `Sesion.clientes`; la consulta los carga desde esa misma lista y el detalle usa `Sesion.clienteSeleccionado`.

## Estructura del proyecto

```text
ActividadPracticaPAE-Semana5/
├── .mvn/
│   └── wrapper/
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java
│       │   └── com/example/actividadpracticapaesemana5/
│       │       ├── Launcher.java
│       │       ├── application/
│       │       │   └── ClientesApplication.java
│       │       ├── controller/
│       │       │   ├── ClienteConsultaController.java
│       │       │   ├── ClienteDetalleController.java
│       │       │   ├── ClienteRegistroController.java
│       │       │   ├── LoginController.java
│       │       │   └── MenuPrincipalController.java
│       │       ├── model/
│       │       │   ├── Cliente.java
│       │       │   └── Usuario.java
│       │       └── util/
│       │           ├── Navegacion.java
│       │           ├── SceneManager.java
│       │           └── Sesion.java
│       └── resources/
│           └── com/example/actividadpracticapaesemana5/
│               ├── fxml/
│               │   ├── cliente-consulta-view.fxml
│               │   ├── cliente-detalle-view.fxml
│               │   ├── cliente-registro-view.fxml
│               │   ├── login-view.fxml
│               │   └── menu-principal-view.fxml
│               └── icons/
│                   ├── passwordField.png
│                   ├── usuario.png
│                   └── usuarioField.png
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Componentes principales

| Archivo o clase | Responsabilidad |
| --- | --- |
| `Launcher` | Inicia JavaFX con `ClientesApplication`. |
| `ClientesApplication` | Abre la vista de inicio de sesión. |
| `LoginController` | Valida las credenciales e inicia la sesión. |
| `MenuPrincipalController` | Gestiona los accesos del menú principal. |
| `ClienteRegistroController` | Controla el formulario, los formatos de entrada, la fotografía y el guardado. |
| `ClienteConsultaController` | Configura la tabla y abre el detalle con doble clic. |
| `ClienteDetalleController` | Muestra los datos y la fotografía del cliente seleccionado. |
| `Cliente` | Representa la información de un cliente. |
| `Sesion` | Mantiene el usuario, los clientes y el cliente seleccionado en memoria. |
| `SceneManager` | Centraliza los cambios de escena del `Stage` principal. |
