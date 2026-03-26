package instituto.fasta.domain.models;

public class RegistroGenomico {
    private Integer id;
    private String documento;
    private String nombre;
    private String apellido;
    private String secuencia;

    // Constructor vacío
    public RegistroGenomico() {}

    // Constructor sin ID (para cuando recién leemos el archivo)
    public RegistroGenomico(String documento, String nombre, String apellido, String secuencia) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.secuencia = secuencia;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getSecuencia() { return secuencia; }
    public void setSecuencia(String secuencia) { this.secuencia = secuencia; }
}