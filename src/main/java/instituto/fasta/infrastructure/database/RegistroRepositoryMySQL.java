package instituto.fasta.infrastructure.database;

import instituto.fasta.domain.models.RegistroGenomico;
import instituto.fasta.domain.repositories.RegistroRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistroRepositoryMySQL implements RegistroRepository {

    @Override
    public RegistroGenomico guardar(RegistroGenomico registro) throws Exception {
        String sql = "INSERT INTO secuencias_pacientes (documento, nombre, apellido, secuencia) VALUES (?, ?, ?, ?)";

        // Usamos try-with-resources para asegurar que la conexión se cierre automáticamente
        try (Connection conn = ConexionDB.obtenerConexion();
             // ¡CLAVE DEL RETO!: Statement.RETURN_GENERATED_KEYS le dice a MySQL que nos devuelva el ID creado
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, registro.getDocumento());
            stmt.setString(2, registro.getNombre());
            stmt.setString(3, registro.getApellido());
            stmt.setString(4, registro.getSecuencia());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new Exception("Fallo al guardar el registro, no se afectaron filas.");
            }

            // Recuperamos el ID generado por la base de datos
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Asignamos el ID real a nuestro objeto del Dominio
                    registro.setId(generatedKeys.getInt(1));
                } else {
                    throw new Exception("Fallo al guardar el registro, no se obtuvo el ID.");
                }
            }
        }

        // Retornamos el objeto completo, ahora con su ID oficial [cite: 13, 14]
        return registro;
    }
}