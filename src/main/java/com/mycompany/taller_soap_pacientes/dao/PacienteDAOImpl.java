package com.mycompany.taller_soap_pacientes.dao;

import com.mycompany.taller_soap_pacientes.model.Paciente;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementación Thread-Safe del DAO en memoria basada en ConcurrentHashMap y AtomicInteger.
 * Diseñada para soportar múltiples instancias de EJB @Stateless de Payara Server concurrentemente.
 */
public class PacienteDAOImpl implements IPacienteDAO {

    private static final PacienteDAOImpl INSTANCIA = new PacienteDAOImpl();
    private final ConcurrentHashMap<Integer, Paciente> tablaPacientes = new ConcurrentHashMap<>();
    private final AtomicInteger generadorId = new AtomicInteger(0);

    // Constructor privado para patrón Singleton compartido
    private PacienteDAOImpl() {
        // Datos semilla iniciales para pruebas del equipo
        guardarSemilla("Carlos", "Perez Gomez", "10203040", Arrays.asList("Penicilina", "Polvo"));
        guardarSemilla("Maria", "Rodriguez Ruiz", "20304050", Arrays.asList("Mariscos", "Aspirina"));
        guardarSemilla("Jorge", "Vidaurre Prado", "30405060", Arrays.asList("Lactosa"));
    }

    private void guardarSemilla(String nombres, String apellidos, String dni, List<String> alergias) {
        int nuevoId = generadorId.incrementAndGet();
        tablaPacientes.put(nuevoId, new Paciente(nuevoId, nombres, apellidos, dni, alergias));
    }

    public static PacienteDAOImpl getInstancia() {
        return INSTANCIA;
    }

    @Override
    public List<Paciente> obtenerTodos() {
        return new ArrayList<>(tablaPacientes.values());
    }

    @Override
    public Optional<Paciente> buscarPorId(int id) {
        return Optional.ofNullable(tablaPacientes.get(id));
    }

    @Override
    public Paciente guardar(String nombres, String apellidos, String dni, List<String> alergias) {
        int nuevoId = generadorId.incrementAndGet();
        Paciente nuevo = new Paciente(nuevoId, nombres, apellidos, dni, alergias);
        tablaPacientes.put(nuevoId, nuevo);
        return nuevo;
    }

    @Override
    public boolean actualizar(int id, String nombres, String apellidos, String dni, List<String> alergias) {
        if (!tablaPacientes.containsKey(id)) {
            return false;
        }
        Paciente existente = tablaPacientes.get(id);
        existente.setNombres(nombres);
        existente.setApellidos(apellidos);
        existente.setDni(dni);
        existente.setAlergias(alergias);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        return tablaPacientes.remove(id) != null;
    }

    @Override
    public boolean existePorId(int id) {
        return tablaPacientes.containsKey(id);
    }
}
