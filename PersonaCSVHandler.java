import java.io.*;
import java.nio.file.*;

public class PersonaCSVHandler {

    private static final String PERSONAS_DIRECTORY = "Personas";

    // Método para asegurar que el directorio Personas exista
    private static void ensureDirectoryExists() {
        File directory = new File(PERSONAS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Directorio Personas creado.");
        }
    }



    // Crear el directorio "Personas" si no existe
    static {
        File directory = new File(PERSONAS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    // Crear persona en un archivo CSV
    public static void crearPersona(Persona persona) {
        guardarPersona(persona);
    }

    // Modificar persona existente en un archivo CSV
    public static void modificarPersona(Persona persona) {
        String fileName = PERSONAS_DIRECTORY + "/" + persona.getId() + "-basicos.csv";
        File file = new File(fileName);
        if (file.exists()) {
            guardarPersona(persona);
            System.out.println("Persona modificada en: " + fileName);
        } else {
            System.out.println("No se encontró la persona con ID: " + persona.getId());
        }
    }

    // Eliminar archivo CSV de una persona con confirmación
    public static boolean eliminarPersonaConConfirmacion(long id, boolean confirmacion) {
        if (!confirmacion) {
            System.out.println("Operación de eliminación cancelada.");
            return false;
        }
        return eliminarPersona(id);
    }


    public static void guardarPersona(Persona persona) {
        ensureDirectoryExists();
        String fileName = PERSONAS_DIRECTORY + "/" + persona.getId() + "-basicos.csv";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("nombre,primerApellido,tipoId,id,nacimiento,genero,direccion,telefono,correo,nombreContacto,relacion,telefonoContacto\n");
            writer.write(persona.getNombre() + "," + persona.getPrimerApellido() + "," + persona.getTipoId() + "," +
                         persona.getId() + "," + persona.getNacimiento() + "," + (persona.isGenero() ? "masculino" : "femenino") + "," +
                         persona.getDireccion() + "," + persona.getTelefono() + "," + persona.getCorreo() + "," +
                         persona.getNombreContacto() + "," + persona.getRelacion() + "," + persona.getTelefonoContacto() + "\n");
            System.out.println("Persona guardada en: " + fileName);
        } catch (IOException e) {
            System.err.println("Error al guardar la persona: " + e.getMessage());
        }
    }

    // Leer datos de una persona desde un archivo CSV
    public static Persona leerPersona(long id) {
        ensureDirectoryExists();
        String fileName = PERSONAS_DIRECTORY + "/" + id + "-basicos.csv";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); // Saltar la primera línea (cabecera)
            String[] data = reader.readLine().split(",");
            
            Persona persona = new Persona();
            persona.setNombre(data[0]);
            persona.setPrimerApellido(data[1]);
            persona.setTipoId(data[2]);
            persona.setId(Long.parseLong(data[3]));
            persona.setNacimiento(data[4]);
            persona.setGenero(data[5].equals("masculino"));
            persona.setDireccion(data[6]);
            persona.setTelefono(Long.parseLong(data[7]));
            persona.setCorreo(data[8]);
            persona.setNombreContacto(data[9]);
            persona.setRelacion(data[10]);
            persona.setTelefonoContacto(Long.parseLong(data[11]));
            
            return persona;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer la persona: " + e.getMessage());
            return null;
        }
    }

    // Eliminar archivo CSV de una persona
    public static boolean eliminarPersona(long id) {
        ensureDirectoryExists();
        String fileName = PERSONAS_DIRECTORY + "/" + id + "-basicos.csv";
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("Archivo eliminado: " + fileName);
            return true;
        } else {
            System.err.println("No se pudo eliminar el archivo: " + fileName);
            return false;
        }
    }
}
