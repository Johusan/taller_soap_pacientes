package com.mycompany.taller_soap_pacientes.logic;

import com.mycompany.taller_soap_pacientes.dao.IPacienteDAO;
import com.mycompany.taller_soap_pacientes.dao.PacienteDAOImpl;
import com.mycompany.taller_soap_pacientes.model.Paciente;
import java.util.List;

/**
 * Módulo de Lógica: Registro de Pacientes.
 * Asignado a: Persona 1.
 */
public class RegistroLogic {

    private final IPacienteDAO pacienteDAO = PacienteDAOImpl.getInstancia();

    /**
     * Registra un nuevo paciente generando un ID autoincremental en el sistema.
     * 
     * @param nombres Nombres del paciente
     * @param apellidos Apellidos del paciente
     * @param dni Documento nacional de identidad
     * @param alergias Lista dinámica de alergias o restricciones médicas
     * @return Mensaje confirmatorio con el ID asignado
     */
    public String registrarPaciente(String nombres, String apellidos, String dni, List<String> alergias) {
        // Validación de datos de entrada
        if (nombres == null || nombres.trim().isEmpty() || apellidos == null || apellidos.trim().isEmpty()) {
            return "Error: Los nombres y apellidos del paciente son obligatorios.";
        }
        
        Paciente nuevo = pacienteDAO.guardar(nombres, apellidos, dni, alergias);
        return "Éxito: Paciente " + nuevo.getNombres() + " " + nuevo.getApellidos() + 
               " registrado correctamente con el ID " + nuevo.getId();
    }
}
