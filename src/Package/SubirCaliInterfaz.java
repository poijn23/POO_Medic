package Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubirCaliInterfaz extends JFrame {

    private JComboBox<String> guardiaSelector;
    private JComboBox<String> periodoSelector; // <-- NUEVO SELECTOR
    private JTable tablaEstudiantes;
    private DefaultTableModel tableModel;
    private JButton btnGuardar;
    private Consult_Database myDatabase;
    // ELIMINAMOS: private static final String PERIODO_ACTUAL = "2025-A";

    public SubirCaliInterfaz(Consult_Database myDatabase) {
        this.myDatabase = myDatabase;
        setTitle("Carga de Calificaciones Finales");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Panel Superior (Selector de Periodo y Guardia)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //Selector de Periodo
        periodoSelector = new JComboBox<>();
        periodoSelector.addItem("Seleccione un Periodo...");
        cargarPeriodosEnComboBox();
        topPanel.add(new JLabel("Seleccionar Periodo:"));
        topPanel.add(periodoSelector);

        //Selector de Guardia
        guardiaSelector = new JComboBox<>();
        guardiaSelector.addItem("Seleccione una Guardia...");
        cargarGuardiasEnComboBox();
        topPanel.add(new JLabel("Seleccionar Guardia:"));
        topPanel.add(guardiaSelector);

        add(topPanel, BorderLayout.NORTH);

        //Tabla de Estudiantes
        String[] columnNames = {"ID/Matrícula", "Nombre", "Calificación Anterior", "Nueva Calificación"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return Float.class;
                return super.getColumnClass(columnIndex);
            }
        };
        tablaEstudiantes = new JTable(tableModel);
        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        // 3. Botón de Guardar
        btnGuardar = new JButton("Guardar Todas las Calificaciones");
        add(btnGuardar, BorderLayout.SOUTH);

        agregarListeners();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // Metodos de interfaz

    private void agregarListeners() {
        periodoSelector.addActionListener(e -> cargarDatosSiAmbosEstanSeleccionados());
        guardiaSelector.addActionListener(e -> cargarDatosSiAmbosEstanSeleccionados());

        btnGuardar.addActionListener(e -> {
            guardarCalificaciones();
        });
    }

    private void cargarDatosSiAmbosEstanSeleccionados() {
        String guardiaSeleccionada = (String) guardiaSelector.getSelectedItem();
        String periodoSeleccionado = (String) periodoSelector.getSelectedItem();

        boolean guardiaValida = guardiaSeleccionada != null && !guardiaSeleccionada.startsWith("Seleccione");
        boolean periodoValido = periodoSeleccionado != null && !periodoSeleccionado.startsWith("Seleccione");

        if (guardiaValida && periodoValido) {
            cargarEstudiantesYCalificaciones(guardiaSeleccionada, periodoSeleccionado);
        } else {
            tableModel.setRowCount(0);
        }
    }

    private void cargarPeriodosEnComboBox() {
        String sql = "SELECT idPeriodo FROM periodo";
        try (Connection conn = myDatabase.isConnected();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                periodoSelector.addItem(rs.getString("idPeriodo"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar periodos: " + ex.getMessage(), "Error de DB", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cargarGuardiasEnComboBox() {
        String sql = "SELECT tipoGuardia FROM Guardia";
        try (Connection conn = myDatabase.isConnected();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                guardiaSelector.addItem(rs.getString("tipoGuardia"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar guardias: " + ex.getMessage(), "Error de DB", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cargarEstudiantesYCalificaciones(String tipoGuardia, String idPeriodo) {
        tableModel.setRowCount(0);

        String sql = "SELECT e.ID, e.Nombre, c.calificacionFinal " +
                "FROM Estudiantes e " +
                "LEFT JOIN calificacion c ON e.ID = c.IdEstudiante AND c.idPeriodo = ? " +
                "WHERE e.tipoGuadiaFK = ?";

        try (Connection conn = myDatabase.isConnected();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idPeriodo); //id del Periodo
            pstmt.setString(2, tipoGuardia); //guardia seleccionada

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String nombre = rs.getString("Nombre");
                    Object califAnterior = rs.getObject("calificacionFinal");

                    tableModel.addRow(new Object[]{id, nombre, califAnterior, null});
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), "Error de DB", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    private void guardarCalificaciones() {
        String guardiaSeleccionada = (String) guardiaSelector.getSelectedItem();
        String periodoSeleccionado = (String) periodoSelector.getSelectedItem();

        if (guardiaSeleccionada == null || guardiaSeleccionada.startsWith("Seleccione") ||
                periodoSeleccionado == null || periodoSeleccionado.startsWith("Seleccione")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Periodo y una Guardia antes de guardar.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String insertSql = "INSERT INTO calificacion (idEstudiante, idPeriodo, calificacionFinal) VALUES (?, ?, ?)";
        String updateSql = "UPDATE calificacion SET calificacionFinal = ? WHERE IdEstudiante = ? AND idPeriodo = ?";

        try (Connection conn = myDatabase.isConnected()) {

            //transaccion
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

                int rows = tableModel.getRowCount();
                for (int i = 0; i < rows; i++) {
                    String idEstudiante = (String) tableModel.getValueAt(i, 0);
                            idEstudiante = idEstudiante.trim();
                    Object nuevaCalificacionObj = tableModel.getValueAt(i, 3);
                    Object calificacionAnteriorObj = tableModel.getValueAt(i, 2);

                    if (nuevaCalificacionObj == null) {
                        continue;
                    }

                    Float nuevaCalificacion;

                    try {
                        nuevaCalificacion = ((Number) nuevaCalificacionObj).floatValue();
                    } catch (ClassCastException | NullPointerException e) {
                        //si el valor ingresado no es un número válido
                        JOptionPane.showMessageDialog(this, "Error de formato de calificación para " + idEstudiante + ". Debe ingresar un número.", "Error de Conversión", JOptionPane.ERROR_MESSAGE);
                        conn.rollback();
                        return;
                    }

                    //validación(0 a 100)
                    if (nuevaCalificacion < 0 || nuevaCalificacion > 100) {
                        JOptionPane.showMessageDialog(this, "La calificación debe estar entre 0 y 100 para el estudiante " + idEstudiante, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        conn.rollback();
                        return;
                    }

                    //insert y update
                    if (calificacionAnteriorObj == null) {
                        insertStmt.setString(1, idEstudiante);
                        insertStmt.setString(2, periodoSeleccionado);
                        insertStmt.setFloat(3, nuevaCalificacion);
                        insertStmt.addBatch();
                    } else {
                        updateStmt.setFloat(1, nuevaCalificacion);
                        updateStmt.setString(2, idEstudiante);
                        updateStmt.setString(3, periodoSeleccionado);
                        updateStmt.addBatch();
                    }
                }

                insertStmt.executeBatch();
                updateStmt.executeBatch();

                conn.commit(); //commit
                JOptionPane.showMessageDialog(this, "Calificaciones guardadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);


                cargarEstudiantesYCalificaciones(guardiaSeleccionada, periodoSeleccionado);

            } catch (SQLException ex) {
                conn.rollback(); //rollback
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error de DB", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

