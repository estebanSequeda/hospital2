class User {
    private String usuario;
    private String password;
    private String nombre;
    private int edad;
    private String id;
    private String cargo;

    public User(String usuario, String password, String nombre, int edad, String id, String cargo) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
        this.cargo = cargo;
    }

    public User(String cargo, int edad, String id, String nombre, String password, String usuario) {
        this.cargo = cargo;
        this.edad = edad;
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }
}