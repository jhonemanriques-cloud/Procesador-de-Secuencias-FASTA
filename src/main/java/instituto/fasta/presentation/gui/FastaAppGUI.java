package instituto.fasta.presentation.gui;

import instituto.fasta.application.services.ProcesadorFastaService;
import instituto.fasta.domain.models.RegistroGenomico;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class FastaAppGUI extends JFrame {

    private final ProcesadorFastaService service;

    // Componentes de la UI solicitados en el reto
    private JLabel lblPaciente;
    private JLabel lblDocumento;
    private JLabel lblIdRegistro;
    private JButton btnCargarArchivo;

    // Inyectamos el servicio de la capa de Aplicación
    public FastaAppGUI(ProcesadorFastaService service) {
        this.service = service;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Procesador de Secuencias FASTA");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10)); // Diseño de cuadrícula simple
    }

    private void inicializarComponentes() {
        btnCargarArchivo = new JButton("Cargar Archivo FASTA");

        // Inicializamos los JLabel en blanco o con un texto por defecto
        lblPaciente = new JLabel("Paciente: ", SwingConstants.CENTER);
        lblDocumento = new JLabel("Documento: ", SwingConstants.CENTER);
        lblIdRegistro = new JLabel("ID de Registro: ", SwingConstants.CENTER);

        // Agregamos la acción al botón
        btnCargarArchivo.addActionListener(e -> procesarArchivo());

        // Añadimos los componentes a la ventana
        add(btnCargarArchivo);
        add(lblPaciente);
        add(lblDocumento);
        add(lblIdRegistro);
    }

    private void procesarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        // Filtro estricto para archivos .fasta
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos FASTA (*.fasta)", "fasta");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                // Leemos el contenido físico del archivo
                String contenido = new String(Files.readAllBytes(archivoSeleccionado.toPath()));

                // Llamamos a nuestro servicio (Capa de Aplicación)
                RegistroGenomico resultado = service.procesarContenidoFasta(contenido);

                // Actualizamos las etiquetas con los datos extraídos [cite: 19]
                lblPaciente.setText("Paciente: " + resultado.getNombre() + " " + resultado.getApellido());
                lblDocumento.setText("Documento: " + resultado.getDocumento());
                lblIdRegistro.setText("ID de Registro: " + resultado.getId());

                JOptionPane.showMessageDialog(this, "Archivo procesado y guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                // Manejo de errores (formato inválido, error de base de datos, etc.)
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error de Procesamiento", JOptionPane.ERROR_MESSAGE);
                limpiarEtiquetas();
            }
        }
    }

    private void limpiarEtiquetas() {
        lblPaciente.setText("Paciente: ");
        lblDocumento.setText("Documento: ");
        lblIdRegistro.setText("ID de Registro: ");
    }
}