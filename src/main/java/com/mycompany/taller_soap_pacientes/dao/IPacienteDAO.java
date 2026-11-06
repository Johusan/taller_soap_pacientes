package com.mycompany.taller_soap_pacientes.dao;

import com.mycompany.taller_soap_pacientes.model.Paciente;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de acceso a datos para la administración de historias clínicas de pacientes.
 */
public interface IPacienteDAO {
    List<Paciente> obtenerTodos();
    Optional<Paciente> buscarPorId(int id);
    Paciente guardar(String nombres, String apellidos, String dni, List<String> alergias);
    boolean actualizar(int id, String nombres, String apellidos, String dni, List<String> alergias);
    boolean eliminar(int id);
    boolean existePorId(int id);
}
