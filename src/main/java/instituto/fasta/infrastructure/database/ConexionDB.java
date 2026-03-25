package instituto.fasta.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Cambia el puerto si tu MySQL no usa el 3306
    private static final String URL = "jdbc:mysql://localhost:3306/instituto_genomica";
    private static final String USER = "root"; // Tu usuario de MySQL
    private static final String PASSWORD = "J19h02o19n87+"; // Tu contraseña de MySQL

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}