package Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class GenerarReporte extends JFrame {

    private JTextField txtFechaDesde, txtFechaHasta;
    private JTextField txtMatriculaEstudiante;
    private JComboBox<String> cmbEspecialidad;
    private JComboBox<String> cmbGrupo;
    private JButton btnGenerarReporte, btnExportarPDF, btnExportarExcel;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    private Consult_Database dbManager;

    public GenerarReporte(Consult_Database dbManager) {
        super("Generación de Reportes de Asistencias");
        this.dbManager = dbManager;
        this.setLayout(new BorderLayout(10, 10));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.txtFechaDesde = new JTextField(8);
        this.txtFechaHasta = new JTextField(8);
        this.txtMatriculaEstudiante = new JTextField(10);

        setupPanelFiltros();
        setupPanelResultados();
        setupPanelExportar();

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setupPanelFiltros() {
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        cmbEspecialidad = new JComboBox<>(new String[]{"Todas", "Medicina", "Enfermeria", "Odontologia", "Nutriologia"});
        cmbGrupo = new JComboBox<>(new String[]{"Todos", "G21-A", "G21-B", "G22"});

        btnGenerarReporte = new JButton("Generar Reporte");

        panelFiltros.add(new JLabel("Desde (AAAA-MM-DD):"));
        panelFiltros.add(txtFechaDesde);
        panelFiltros.add(new JLabel("Hasta (AAAA-MM-DD):"));
        panelFiltros.add(txtFechaHasta);

        panelFiltros.add(new JLabel("Especialidad:"));
        panelFiltros.add(cmbEspecialidad);

        panelFiltros.add(new JLabel("Grupo:"));
        panelFiltros.add(cmbGrupo);

        panelFiltros.add(new JLabel("Matrícula de Estudiante:"));
        panelFiltros.add(txtMatriculaEstudiante);

        panelFiltros.add(btnGenerarReporte);

        btnGenerarReporte.addActionListener(new ReporteGeneralActionListener());

        this.add(panelFiltros, BorderLayout.NORTH);
    }

    private void setupPanelResultados() {
        String[] columnasIniciales = {"Fecha", "Matrícula", "Nombre", "Especialidad", "Grupo", "Entrada", "Salida", "Horas"};
        modeloTabla = new DefaultTableModel(columnasIniciales, 0);
        tablaResultados = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setPreferredSize(new Dimension(1000, 350));

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void setupPanelExportar() {
        JPanel panelExportar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnExportarPDF = new JButton("Exportar a PDF");
        btnExportarExcel = new JButton("Exportar a Excel (XLSM)");

        btnExportarPDF.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función: Exportar tabla actual a PDF."));
        btnExportarExcel.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función: Exportar tabla actual a Excel."));

        panelExportar.add(btnExportarPDF);
        panelExportar.add(btnExportarExcel);

        this.add(panelExportar, BorderLayout.SOUTH);
    }

    private List<Object[]> obtenerReporteDeAsistencias(String desde, String hasta, String esp, String grupo, String matricula) {
        return dbManager.getReporteAsistencias(desde, hasta, esp, grupo, matricula);
    }

    private class ReporteGeneralActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String fechaDesde = txtFechaDesde.getText();
            String fechaHasta = txtFechaHasta.getText();
            String especialidad = (String) cmbEspecialidad.getSelectedItem();
            String grupo = (String) cmbGrupo.getSelectedItem();
            String matriculaEstudiante = txtMatriculaEstudiante.getText();

            System.out.printf("Generando reporte de ASISTENCIAS. Filtros: Desde=%s, Hasta=%s, Especialidad=%s, Grupo=%s, Matrícula=%s%n",
                    fechaDesde, fechaHasta, especialidad, grupo, matriculaEstudiante);

            List<Object[]> datosReporte = obtenerReporteDeAsistencias(
                    fechaDesde, fechaHasta, especialidad, grupo, matriculaEstudiante
            );

            modeloTabla.setRowCount(0);

            if (datosReporte.isEmpty()) {
                JOptionPane.showMessageDialog(GenerarReporte.this,
                        "No se encontraron registros de asistencia con los filtros seleccionados.",
                        "Sin Resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Object[] fila : datosReporte) {
                    modeloTabla.addRow(fila);
                }
            }
        }
    }

    private static class ReportData {
        String[] columnas;
        Vector<Vector<Object>> datos;

        public ReportData(String[] columnas, Vector<Vector<Object>> datos) {
            this.columnas = columnas;
            this.datos = datos;
        }
    }
}