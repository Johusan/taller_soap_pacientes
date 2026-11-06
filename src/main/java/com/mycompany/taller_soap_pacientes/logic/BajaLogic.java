package com.mycompany.taller_soap_pacientes.logic;

import com.mycompany.taller_soap_pacientes.dao.IPacienteDAO;
import com.mycompany.taller_soap_pacientes.dao.PacienteDAOImpl;
import com.mycompany.taller_soap_pacientes.exception.PacienteNoEncontradoException;

/**
 * Módulo de Lógica: Egreso y Alta Definitiva de Pacientes (Eliminación).
 * Asignado a: Persona 4.
 */
public class BajaLogic {

    private final IPacienteDAO pacienteDAO = PacienteDAOImpl.getInstancia();

    /**
     * Da de alta médica definitiva a un paciente, removiéndolo de la memoria.
     * Valida previamente si el paciente existe; si no, lanza PacienteNoEncontradoException (SOAP Fault).
     * 
     * @param id Identificador único del paciente
     * @return Mensaje confirmatorio de alta médica
     * @throws PacienteNoEncontradoException Si el ID no existe en la clínica
     */
    public String darDeAltaPaciente(int id) throws PacienteNoEncontradoException {
        if (!pacienteDAO.existePorId(id)) {
            throw new PacienteNoEncontradoException(id);
        }

        boolean eliminado = pacienteDAO.eliminar(id);
        if (!eliminado) {
            throw new PacienteNoEncontradoException("Error: No se pudo procesar el alta del paciente con ID " + id);
        }

        return "Éxito: El paciente con ID " + id + " ha sido dado de alta médica y retirado del sistema.";
    }
}

