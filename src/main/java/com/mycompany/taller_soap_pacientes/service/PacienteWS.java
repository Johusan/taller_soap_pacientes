package com.mycompany.taller_soap_pacientes.service;

import com.mycompany.taller_soap_pacientes.exception.PacienteNoEncontradoException;
import com.mycompany.taller_soap_pacientes.logic.ActualizacionLogic;
import com.mycompany.taller_soap_pacientes.logic.BajaLogic;
import com.mycompany.taller_soap_pacientes.logic.ConsultaLogic;
import com.mycompany.taller_soap_pacientes.logic.RegistroLogic;
import com.mycompany.taller_soap_pacientes.model.Paciente;
import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

/**
 * Servicio Web SOAP que actúa como punto de acceso y fachada para el sistema de Historias Clínicas.
 * Gestionado como EJB sin estado (@Stateless) en Payara Server Community 7.x.
 * Delega las operaciones a los módulos de lógica correspondientes.
 */
@Stateless
@WebService(serviceName = "PacienteWS")
public class PacienteWS {

    private final RegistroLogic registroLogic = new RegistroLogic();
    private final ConsultaLogic consultaLogic = new ConsultaLogic();
    private final ActualizacionLogic actualizacionLogic = new ActualizacionLogic();
    private final BajaLogic bajaLogic = new BajaLogic();

    /**
     * Operación 1: Retorna la colección completa de pacientes en memoria.
     */
    @WebMethod(operationName = "listarPacientes")
    public List<Paciente> listarPacientes() {
        return consultaLogic.listarPacientes();
    }

    /**
     * Operación 2: Localiza un paciente por su ID. Lanza SOAP Fault si no existe.
     */
    @WebMethod(operationName = "buscarPaciente")
    public Paciente buscarPaciente(@WebParam(name = "idPaciente") int id) throws PacienteNoEncontradoException {
        return consultaLogic.buscarPaciente(id);
    }

    /**
     * Operación 3: Registra un nuevo paciente con lista de alergias y genera ID correlativo.
     */
    @WebMethod(operationName = "registrarPaciente")
    public String registrarPaciente(
            @WebParam(name = "nombres") String nombres,
            @WebParam(name = "apellidos") String apellidos,
            @WebParam(name = "dni") String dni,
            @WebParam(name = "alergias") List<String> alergias) {
        return registroLogic.registrarPaciente(nombres, apellidos, dni, alergias);
    }

    /**
     * Operación 4: Modifica los datos médicos de un paciente existente. Lanza SOAP Fault si no existe.
     */
    @WebMethod(operationName = "actualizarPaciente")
    public String actualizarPaciente(
            @WebParam(name = "idPaciente") int id,
            @WebParam(name = "nombres") String nombres,
            @WebParam(name = "apellidos") String apellidos,
            @WebParam(name = "dni") String dni,
            @WebParam(name = "alergias") List<String> alergias) throws PacienteNoEncontradoException {
        return actualizacionLogic.actualizarPaciente(id, nombres, apellidos, dni, alergias);
    }

    /**
     * Operación 5: Remueve al paciente de la estructura en memoria simulando su alta clínica. Lanza SOAP Fault si no existe.
     */
    @WebMethod(operationName = "darDeAltaPaciente")
    public String darDeAltaPaciente(@WebParam(name = "idPaciente") int id) throws PacienteNoEncontradoException {
        return bajaLogic.darDeAltaPaciente(id);
    }
}

