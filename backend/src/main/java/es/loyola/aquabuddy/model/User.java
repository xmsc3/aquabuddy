package es.loyola.aquabuddy.model;

public class User {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private String pwdHash;
    private double peso;
    private double altura;
    private String nivelActividad;
    private int objetivoMl;

    public User() { }

    public User(int id, String nombre, String apellidos, String email, String pwdHash,
                double peso, double altura, String nivelActividad, int objetivoMl) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.pwdHash = pwdHash;
        this.peso = peso;
        this.altura = altura;
        this.nivelActividad = nivelActividad;
        this.objetivoMl = objetivoMl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPwdHash() { return pwdHash; }
    public void setPwdHash(String pwdHash) { this.pwdHash = pwdHash; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public String getNivelActividad() { return nivelActividad; }
    public void setNivelActividad(String nivelActividad) { this.nivelActividad = nivelActividad; }

    public int getObjetivoMl() { return objetivoMl; }
    public void setObjetivoMl(int objetivoMl) { this.objetivoMl = objetivoMl; }
}
