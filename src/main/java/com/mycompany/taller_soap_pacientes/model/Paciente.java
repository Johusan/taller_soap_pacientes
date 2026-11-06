package com.mycompany.taller_soap_pacientes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Entidad JavaBean que representa a un paciente en el sistema de Historias Clínicas.
 * Cumple con los requisitos JAXB y Serializable para transmisión en mensajes SOAP.
 */
@XmlRootElement(name = "paciente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombres;
    private String apellidos;
    private String dni;

    @XmlElementWrapper(name = "alergias")
    @XmlElement(name = "alergia")
    private List<String> alergias;

    /**
     * Constructor vacío por defecto obligatorio para JAXB.
     */
    public Paciente() {
        this.alergias = new ArrayList<>();
    }

    /**
     * Constructor con todos los campos.
     */
    public Paciente(int id, String nombres, String apellidos, String dni, List<String> alergias) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.alergias = (alergias != null) ? new ArrayList<>(alergias) : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<String> getAlergias() {
        if (this.alergias == null) {
            this.alergias = new ArrayList<>();
        }
        return alergias;
    }

    public void setAlergias(List<String> alergias) {
        this.alergias = (alergias != null) ? new ArrayList<>(alergias) : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", dni=" + dni + ", alergias=" + alergias + '}';
    }
}
