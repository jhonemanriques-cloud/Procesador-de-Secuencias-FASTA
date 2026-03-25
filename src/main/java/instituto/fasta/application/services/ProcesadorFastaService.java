package instituto.fasta.application.services;

import instituto.fasta.domain.models.RegistroGenomico;
import instituto.fasta.domain.repositories.RegistroRepository;

public class ProcesadorFastaService {

    private final RegistroRepository repository;

    // Inyección de dependencias: Recibimos la interfaz, no la implementación directa.
    public ProcesadorFastaService(RegistroRepository repository) {
        this.repository = repository;
    }

    public RegistroGenomico procesarContenidoFasta(String contenido) throws Exception {
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("El archivo FASTA está vacío.");
        }

        // Separamos el contenido por líneas
        String[] lineas = contenido.split("\\r?\\n");
        String encabezado = lineas[0].trim();

        // REGLA 1: Validar que el texto inicie con '>'
        if (!encabezado.startsWith(">")) {
            throw new IllegalArgumentException("Formato inválido: El archivo debe iniciar con el carácter '>'.");
        }

        // REGLA 2: Extraer documento, nombre y apellido
        // Formato esperado: >documentoPersona nombre | apellido
        String sinMayorQue = encabezado.substring(1).trim(); // Quitamos el '>'

        // El primer espacio separa el documento del nombre
        int indicePrimerEspacio = sinMayorQue.indexOf(' ');
        if (indicePrimerEspacio == -1) {
            throw new IllegalArgumentException("Formato inválido: No se encontró separación entre documento y nombre.");
        }

        String documento = sinMayorQue.substring(0, indicePrimerEspacio).trim();
        String restoEncabezado = sinMayorQue.substring(indicePrimerEspacio + 1).trim();

        // Separamos el nombre y el apellido usando el delimitador '|'
        String[] partesNombreApellido = restoEncabezado.split("\\|");
        if (partesNombreApellido.length < 2) {
            throw new IllegalArgumentException("Formato inválido: Falta el separador '|' entre el nombre y el apellido.");
        }

        String nombre = partesNombreApellido[0].trim();
        String apellido = partesNombreApellido[1].trim();

        // REGLA 3: Extraer la secuencia genética (Líneas posteriores)
        StringBuilder secuenciaBuilder = new StringBuilder();
        for (int i = 1; i < lineas.length; i++) {
            secuenciaBuilder.append(lineas[i].trim());
        }
        String secuencia = secuenciaBuilder.toString();

        if (secuencia.isEmpty()) {
            throw new IllegalArgumentException("El archivo no contiene una secuencia genética válida.");
        }

        // Ensamblamos la entidad de nuestro Dominio
        RegistroGenomico nuevoRegistro = new RegistroGenomico(documento, nombre, apellido, secuencia);

        // Enviamos al repositorio para persistir.
        // El repositorio nos devolverá el mismo objeto, pero ahora con el ID autoincremental asignado.
        return repository.guardar(nuevoRegistro);
    }
}