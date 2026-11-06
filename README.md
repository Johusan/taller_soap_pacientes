# Sistema de Gestión de Historias Clínicas (SOAP)
## Proyecto Académico - Escenario 02: Programación de Aplicaciones Distribuidas

Bienvenido al repositorio oficial del proyecto. Este servicio web está desarrollado bajo la especificación **Jakarta XML Web Services (JAX-WS)** en formato **Code-First**, administrado como un EJB sin estado (`@Stateless`) y desplegado en **Payara Server Community 7.x** sobre **Java 21**.

---

## 🛠️ Entorno Tecnológico Obligatorio

* **Plataforma Java:** OpenJDK 21 LTS (Eclipse Temurin).
* **Servidor de Aplicaciones:** Payara Server Community 7.x (puerto por defecto `8080`).
* **Gestor de Dependencias:** Apache Maven (Jakarta EE 10.0.0 API `provided`).
* **IDE Recomendado:** Apache NetBeans IDE 20+.
* **Cliente de Pruebas:** Extensión **Boomerang - SOAP & REST Client** (Chrome / Edge).
* **URL del Contrato WSDL:**  
  `http://localhost:8080/taller_soap_pacientes-1.0-SNAPSHOT/PacienteWS?wsdl`

---

## 📁 Arquitectura del Proyecto

El código está estructurado en 4 capas para garantizar que podamos desarrollar simultáneamente sin conflictos de Git:

```
src/main/java/com/mycompany/taller_soap_pacientes/
├── model/        -> Paciente.java (Entidad JavaBean JAXB con alergias) [COMPARTIDO]
├── exception/    -> PacienteNoEncontradoException.java (@WebFault)     [COMPARTIDO]
├── dao/          -> IPacienteDAO y PacienteDAOImpl (Almacén en memoria)[COMPARTIDO]
├── logic/        -> Clases de lógica asignadas a cada integrante       [TRABAJO INDIVIDUAL]
└── service/      -> PacienteWS.java (Fachada SOAP @Stateless)          [COMPARTIDO]
```

---

## 👥 Asignación de Responsabilidades (4 Integrantes)

Cada integrante tiene una **clase exclusiva** en el paquete `logic/` y una rama de Git asignada.  
> ⚠️ **Regla de oro:** Cada integrante **solo debe modificar su archivo de lógica**. No modifiques clases de tus compañeros ni los archivos compartidos (`model`, `dao`, `service`, `pom.xml`).

| Integrante | Archivo Asignado | Rama Git | Operaciones SOAP |
| :--- | :--- | :--- | :--- |
| **Persona 1** | `logic/RegistroLogic.java` | `feature/p1-registro` | `registrarPaciente` |
| **Persona 2** | `logic/ConsultaLogic.java` | `feature/p2-consulta` | `listarPacientes`, `buscarPaciente` |
| **Persona 3** | `logic/ActualizacionLogic.java` | `feature/p3-actualizacion` | `actualizarPaciente` |
| **Persona 4** | `logic/BajaLogic.java` | `feature/p4-baja` | `darDeAltaPaciente` |

---

### Detalle de Tareas por Integrante

#### 👤 Persona 1: Registro de Pacientes
* **Archivo de trabajo:** `src/main/java/com/mycompany/taller_soap_pacientes/logic/RegistroLogic.java`
* **Operación asignada:** `registrarPaciente(nombres, apellidos, dni, alergias)`
* **Responsabilidades:**
  1. Validar que los nombres, apellidos y DNI no vengan vacíos ni nulos.
  2. (Opcional recomendado) Validar que el DNI tenga formato de 8 dígitos numéricos.
  3. Invocar al DAO para persistir el nuevo registro con ID correlativo autoincremental.
  4. Formatear y retornar el mensaje de éxito con el nuevo ID generado.
* **Entregable en Boomerang:** Captura de la petición SOAP XML enviando un nuevo paciente con su lista de alergias y la respuesta XML con el mensaje de confirmación.

---

#### 👤 Persona 2: Catálogo y Búsqueda
* **Archivo de trabajo:** `src/main/java/com/mycompany/taller_soap_pacientes/logic/ConsultaLogic.java`
* **Operaciones asignadas:** `listarPacientes()` y `buscarPaciente(idPaciente)`
* **Responsabilidades:**
  1. En `listarPacientes`: Obtener la colección completa de pacientes desde el DAO y retornarla.
  2. En `buscarPaciente`: Validar la existencia del ID. Si existe, retornar la entidad `Paciente`.
  3. **SOAP Fault obligatorio:** Si el ID no existe en la clínica, lanzar `PacienteNoEncontradoException(id)`. El protocolo SOAP interceptará esta excepción y generará automáticamente el bloque `<SOAP-ENV:Fault>`.
* **Entregables en Boomerang:**
  - Captura del listado general evidenciando la estructura de alergias en XML.
  - Captura de búsqueda exitosa (ej. ID `1`).
  - Captura de búsqueda fallida (ej. ID `999`) mostrando el `<SOAP-ENV:Fault>` con el mensaje de error.

---

#### 👤 Persona 3: Actualización Médica
* **Archivo de trabajo:** `src/main/java/com/mycompany/taller_soap_pacientes/logic/ActualizacionLogic.java`
* **Operación asignada:** `actualizarPaciente(idPaciente, nombres, apellidos, dni, alergias)`
* **Responsabilidades:**
  1. Comprobar primero si el paciente existe mediante el DAO.
  2. **SOAP Fault obligatorio:** Si no existe, lanzar inmediatamente `PacienteNoEncontradoException(id)`.
  3. Si existe, actualizar sus datos personales y su lista dinámica de alergias.
  4. Retornar mensaje de confirmación de actualización exitosa.
* **Entregables en Boomerang:**
  - Captura de actualización exitosa (modificando por ejemplo al paciente `1` o `2`).
  - Captura del intento de actualización de un ID inexistente con su respectivo `<SOAP-ENV:Fault>`.

---

#### 👤 Persona 4: Egreso y Alta Definitiva
* **Archivo de trabajo:** `src/main/java/com/mycompany/taller_soap_pacientes/logic/BajaLogic.java`
* **Operación asignada:** `darDeAltaPaciente(idPaciente)`
* **Responsabilidades:**
  1. Validar la presencia del paciente en memoria.
  2. **SOAP Fault obligatorio:** Si el ID no existe, disparar `PacienteNoEncontradoException(id)`.
  3. Si existe, remover al paciente de la estructura de datos simulando su alta médica definitiva.
  4. Responder con un mensaje claro confirmando el alta y retiro del paciente del sistema.
* **Entregables en Boomerang:**
  - Captura de eliminación exitosa de un paciente (ej. ID `3`).
  - Captura de intento de alta médica con un ID inexistente reflejando el `<SOAP-ENV:Fault>`.

---

## 🚀 Flujo de Trabajo en Git

1. **Clonar el proyecto:**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd taller_soap_pacientes
   ```
2. **Actualizar la rama base `develop` y crear tu rama de trabajo:**
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/pX-tu-modulo   # (ejemplo: feature/p1-registro)
   ```
3. **Abrir el proyecto en NetBeans, programar tu clase y probar:**
   - Hacer clic derecho en el proyecto -> *Clean and Build*.
   - Desplegar en Payara Server y probar tu operación en Boomerang.
4. **Hacer commit ÚNICAMENTE de tu archivo:**
   ```bash
   git add src/main/java/com/mycompany/taller_soap_pacientes/logic/TuClaseLogic.java
   git commit -m "feat: implementacion y pruebas de modulo X"
   ```
5. **Subir los cambios a GitHub y abrir Pull Request:**
   ```bash
   git push -u origin feature/pX-tu-modulo
   ```
   - Ir a GitHub y crear un Pull Request hacia la rama `develop` para revisión e integración.

---

## 📌 Datos Iniciales Semilla (Para Pruebas)

Al iniciar el servidor, el DAO carga automáticamente 3 pacientes para que todos puedan probar de inmediato sin esperarse:

* **ID 1:** Carlos Perez Gomez | DNI: `10203040` | Alergias: Penicilina, Polvo.
* **ID 2:** Maria Rodriguez Ruiz | DNI: `20304050` | Alergias: Mariscos, Aspirina.
* **ID 3:** Jorge Vidaurre Prado | DNI: `30405060` | Alergias: Lactosa.

