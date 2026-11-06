package com.mycompany.taller_soap_pacientes.logic;

import com.mycompany.taller_soap_pacientes.dao.IPacienteDAO;
import com.mycompany.taller_soap_pacientes.dao.PacienteDAOImpl;
import com.mycompany.taller_soap_pacientes.exception.PacienteNoEncontradoException;
import com.mycompany.taller_soap_pacientes.model.Paciente;
import java.util.List;

/**
 * Módulo de Lógica: Consulta y Búsqueda de Pacientes.
 * Asignado a: Persona 2.
 */
public class ConsultaLogic {

    private final IPacienteDAO pacienteDAO = PacienteDAOImpl.getInstancia();

    /**
     * Retorna la colección completa de pacientes registrados en memoria.
     * 
     * @return Lista de pacientes
     */
    public List<Paciente> listarPacientes() {
        return pacienteDAO.obtenerTodos();
    }

    /**
     * Busca un paciente por su ID. Si no existe, emite PacienteNoEncontradoException
     * para que JAX-WS genere un SOAP Fault nativo.
     * 
     * @param id Identificador del paciente
     * @return Paciente localizado
     * @throws PacienteNoEncontradoException Si el ID no existe en la clínica
     */
    public Paciente buscarPaciente(int id) throws PacienteNoEncontradoException {
        return pacienteDAO.buscarPorId(id)
                .orElseThrow(() -> new PacienteNoEncontradoException(id));
    }
}
