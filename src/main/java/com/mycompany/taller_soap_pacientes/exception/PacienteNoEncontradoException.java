package com.mycompany.taller_soap_pacientes.exception;

import jakarta.xml.ws.WebFault;

/**
 * Excepción de negocio estructurada para ser interceptada por JAX-WS
 * y mapeada automáticamente a un elemento <SOAP-ENV:Fault>.
 */
@WebFault(name = "PacienteNoEncontradoFault", targetNamespace = "http://service.taller_soap_pacientes.mycompany.com/")
public class PacienteNoEncontradoException extends Exception {

    private static final long serialVersionUID = 1L;

    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public PacienteNoEncontradoException(int id) {
        super("Error: El paciente con ID especificado (" + id + ") no existe en la clínica.");
    }
}
