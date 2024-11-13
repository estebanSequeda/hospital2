public class Persona {

    private String nombre;
    private String primerApellido;
    private String tipoId; // e.g., "cedula"
    private long id;
    private String nacimiento;
    private boolean genero; // true for masculine, false for feminine
    private String direccion;
    private long telefono;
    private String correo;
    private String nombreContacto;
    private String relacion;
    private long telefonoContacto;

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getTipoId() {
        return this.tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNacimiento() {
        return this.nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public boolean isGenero() {
        return this.genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getTelefono() {
        return this.telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreContacto() {
        return this.nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getRelacion() {
        return this.relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public long getTelefonoContacto() {
        return this.telefonoContacto;
    }

    public void setTelefonoContacto(long telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    
}
