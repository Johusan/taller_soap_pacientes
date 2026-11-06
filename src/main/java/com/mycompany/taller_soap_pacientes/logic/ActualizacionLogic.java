package com.mycompany.taller_soap_pacientes.logic;

import com.mycompany.taller_soap_pacientes.dao.IPacienteDAO;
import com.mycompany.taller_soap_pacientes.dao.PacienteDAOImpl;
import com.mycompany.taller_soap_pacientes.exception.PacienteNoEncontradoException;
import java.util.List;

/**
 * Módulo de Lógica: Actualización de Pacientes.
 * Asignado a: Persona 3.
 */
public class ActualizacionLogic {

    private final IPacienteDAO pacienteDAO = PacienteDAOImpl.getInstancia();

    /**
     * Modifica los datos médicos y personales de un paciente existente.
     * Valida estrictamente la existencia previa; si no existe, lanza excepción (SOAP Fault).
     * 
     * @param id Identificador único del paciente
     * @param nombres Nuevos nombres
     * @param apellidos Nuevos apellidos
     * @param dni Nuevo DNI
     * @param alergias Nueva lista de restricciones médicas
     * @return Mensaje de éxito
     * @throws PacienteNoEncontradoException Si el ID no existe en el registro
     */
    public String actualizarPaciente(int id, String nombres, String apellidos, String dni, List<String> alergias) 
            throws PacienteNoEncontradoException {
        
        if (!pacienteDAO.existePorId(id)) {
            throw new PacienteNoEncontradoException(id);
        }

        boolean actualizado = pacienteDAO.actualizar(id, nombres, apellidos, dni, alergias);
        if (!actualizado) {
            throw new PacienteNoEncontradoException("Error: No se pudo actualizar el paciente con ID " + id);
        }

        return "Éxito: Los datos del paciente con ID " + id + " fueron actualizados correctamente.";
    }
}
