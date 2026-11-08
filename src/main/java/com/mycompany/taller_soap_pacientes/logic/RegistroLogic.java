package com.mycompany.taller_soap_pacientes.logic;

import com.mycompany.taller_soap_pacientes.dao.IPacienteDAO;
import com.mycompany.taller_soap_pacientes.dao.PacienteDAOImpl;
import com.mycompany.taller_soap_pacientes.model.Paciente;
import java.util.ArrayList;
import java.util.List;

/**
 * Módulo de Lógica de Negocio: Registro de Pacientes.
 * Responsable: Persona 1.
 */
public class RegistroLogic {

    private final IPacienteDAO pacienteDAO = PacienteDAOImpl.getInstancia();

    /**
     * Registra un nuevo paciente en la clínica previa validación estricta
     * de sus datos personales, formato de DNI y restricciones médicas.
     * 
     * @param nombres   Nombres completos del paciente (obligatorio)
     * @param apellidos Apellidos completos del paciente (obligatorio)
     * @param dni       Documento Nacional de Identidad de 8 dígitos (obligatorio)
     * @param alergias  Colección dinámica de alergias o condiciones preexistentes
     * @return Mensaje estructurado con el resultado de la operación y el ID
     *         generado
     */
    public String registrarPaciente(String nombres, String apellidos, String dni, List<String> alergias) {
        // 1. Validación de campos obligatorios
        if (nombres == null || nombres.trim().isEmpty()) {
            return "Error de validación: El campo 'nombres' es obligatorio y no puede estar vacío.";
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            return "Error de validación: El campo 'apellidos' es obligatorio y no puede estar vacío.";
        }
        if (dni == null || dni.trim().isEmpty()) {
            return "Error de validación: El campo 'dni' es obligatorio.";
        }

        // 2. Normalización de cadenas
        String nombresLimpios = nombres.trim();
        String apellidosLimpios = apellidos.trim();
        String dniLimpio = dni.trim();

        // 3. Validación de formato de DNI (exactamente 8 dígitos numéricos)
        if (!dniLimpio.matches("\\d{8}")) {
            return "Error de validación: El DNI '" + dniLimpio
                    + "' no es válido. Debe contener exactamente 8 dígitos numéricos.";
        }

        // 4. Verificación de DNI existente en el sistema
        boolean dniDuplicado = pacienteDAO.obtenerTodos().stream()
                .anyMatch(p -> p.getDni() != null && p.getDni().equalsIgnoreCase(dniLimpio));
        if (dniDuplicado) {
            return "Error de negocio: Ya existe un paciente registrado con el DNI " + dniLimpio + " en la clínica.";
        }

        // 5. Depuración y saneamiento de la colección de alergias
        List<String> alergiasLimpias = new ArrayList<>();
        if (alergias != null) {
            for (String alergia : alergias) {
                if (alergia != null && !alergia.trim().isEmpty()) {
                    String alergiaFormateada = alergia.trim();
                    if (!alergiasLimpias.contains(alergiaFormateada)) {
                        alergiasLimpias.add(alergiaFormateada);
                    }
                }
            }
        }

        // 6. Persistencia a través del DAO (ID correlativo autoincremental)
        Paciente nuevo = pacienteDAO.guardar(nombresLimpios, apellidosLimpios, dniLimpio, alergiasLimpias);

        return "Éxito: Paciente " + nuevo.getNombres() + " " + nuevo.getApellidos() +
                " (DNI: " + nuevo.getDni() + ") registrado correctamente con el ID " + nuevo.getId() +
                " y " + nuevo.getAlergias().size() + " alergia(s) registrada(s).";
    }
}