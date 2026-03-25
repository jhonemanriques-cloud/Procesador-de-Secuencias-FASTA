package instituto.fasta;

import instituto.fasta.application.services.ProcesadorFastaService;
import instituto.fasta.domain.repositories.RegistroRepository;
import instituto.fasta.infrastructure.database.RegistroRepositoryMySQL;
import instituto.fasta.presentation.gui.FastaAppGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // 1. Instanciamos la Infraestructura (Base de datos)
        RegistroRepository repositorio = new RegistroRepositoryMySQL();

        // 2. Instanciamos la Aplicación (Servicio), inyectando el repositorio
        ProcesadorFastaService servicio = new ProcesadorFastaService(repositorio);

        // 3. Arrancamos la Presentación (GUI), inyectando el servicio
        SwingUtilities.invokeLater(() -> {
            FastaAppGUI app = new FastaAppGUI(servicio);
            app.setVisible(true);
        });
    }
}