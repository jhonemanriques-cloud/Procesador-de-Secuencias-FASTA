package instituto.fasta;

import instituto.fasta.application.services.ProcesadorFastaService;
import instituto.fasta.domain.repositories.RegistroRepository;
import instituto.fasta.infrastructure.database.RegistroRepositoryMySQL;
import instituto.fasta.presentation.gui.FastaAppGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        RegistroRepository repositorio = new RegistroRepositoryMySQL();


        ProcesadorFastaService servicio = new ProcesadorFastaService(repositorio);


        SwingUtilities.invokeLater(() -> {
            FastaAppGUI app = new FastaAppGUI(servicio);
            app.setVisible(true);
        });
    }
}