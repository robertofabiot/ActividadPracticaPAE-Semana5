package com.example.actividadpracticapaesemana5.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nombres;
    private String apellidos;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;
    private List<String> serviciosInteres;
    private String rutaFotografia;
    private LocalDateTime fechaHoraRegistro;

    public Cliente() {
        this.serviciosInteres = new ArrayList<>();
        this.fechaHoraRegistro = LocalDateTime.now();
    }

    public Cliente(String nombres, String apellidos, String tipoCliente, String ciudad,
                   LocalDate fechaNacimiento, String tipoSolicitud,
                   List<String> serviciosInteres, String rutaFotografia) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoCliente = tipoCliente;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSolicitud = tipoSolicitud;
        this.serviciosInteres = (serviciosInteres != null) ? serviciosInteres : new ArrayList<>();
        this.rutaFotografia = rutaFotografia;
        this.fechaHoraRegistro = LocalDateTime.now();
    }

    // Propiedad calculada para mapear directamente en TableView ("nombreCompleto")
    public String getNombreCompleto() {
        String nom = (nombres != null) ? nombres.trim() : "";
        String ape = (apellidos != null) ? apellidos.trim() : "";
        return (nom + " " + ape).trim();
    }

    // Representación en texto de la lista de servicios para tablas o etiquetas de detalle
    public String getServiciosComoTexto() {
        if (serviciosInteres == null || serviciosInteres.isEmpty()) {
            return "Ninguno";
        }
        return String.join(", ", serviciosInteres);
    }

    public String getFechaComoTexto() {
        if (fechaNacimiento == null) return "";
        return fechaNacimiento.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getFechaHoraRegistroComoTexto() {
        if (fechaHoraRegistro == null) return "";
        return fechaHoraRegistro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    // Getters y Setters
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<String> getServiciosInteres() {
        return serviciosInteres;
    }

    public void setServiciosInteres(List<String> serviciosInteres) {
        this.serviciosInteres = serviciosInteres;
    }

    public String getRutaFotografia() {
        return rutaFotografia;
    }

    public void setRutaFotografia(String rutaFotografia) {
        this.rutaFotografia = rutaFotografia;
    }

    public LocalDateTime getFechaHoraRegistro() { return fechaHoraRegistro; }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) { this.fechaHoraRegistro = fechaHoraRegistro;}

    @Override
    public String toString() {
        return getNombreCompleto() + " (" + tipoCliente + " - " + ciudad + ")";
    }
}
