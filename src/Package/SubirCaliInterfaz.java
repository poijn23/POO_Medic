package Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubirCaliInterfaz extends JFrame {

    private JComboBox<String> guardiaSelector;
    private JTable tablaEstudiantes;
    private DefaultTableModel tableModel;
    private JButton btnGuardar;



    private static final String DB_URL = "jdbc:mysql://34.31.14.40:3306/ProyectoDyPOO" +
            "?serverTimezone=UTC&useSSL=false";
    private static final String DB_USER = "grupoPOO";
    private static final String DB_PASS = "LfftG2acd9Mv7%k7";
    private static final String PERIODO_ACTUAL = "2025-A";

    public SubirCaliInterfaz() {
        setTitle("Carga de Calificaciones Finales");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //(Selector de Guardia)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        guardiaSelector = new JComboBox<>();
        guardiaSelector.addItem("Seleccione una Guardia...");
        cargarGuardiasEnComboBox();

        topPanel.add(new JLabel("Seleccionar Guardia:"));
        topPanel.add(guardiaSelector);
        add(topPanel, BorderLayout.NORTH);

        // Tabla de Estudiantes
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

        //Botón de Guardar
        btnGuardar = new JButton("Guardar Todas las Calificaciones");
        add(btnGuardar, BorderLayout.SOUTH);

        agregarListeners();
    }

    //Métodos de Interfaz (Listeners)
    private void agregarListeners() {
        // Evento al seleccionar una guardia
        guardiaSelector.addActionListener(e -> {
            String guardiaSeleccionada = (String) guardiaSelector.getSelectedItem();
            if (guardiaSeleccionada != null && !guardiaSeleccionada.startsWith("Seleccione")) {
                cargarEstudiantesYCalificaciones(guardiaSeleccionada);
            } else {
                tableModel.setRowCount(0);
            }
        });

        // Evento al hacer clic en Guardar
        btnGuardar.addActionListener(e -> {
            guardarCalificaciones();
        });
    }

    // Métodos de Acceso a Datos
    private void cargarGuardiasEnComboBox() {
        String sql = "SELECT tipoGuardia FROM Guardia";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
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


    private void cargarEstudiantesYCalificaciones(String tipoGuardia) {
        tableModel.setRowCount(0);

        // Consulta
        String sql = "SELECT e.ID, e.Nombre, c.calificacionFinal " +
                "FROM Estudiantes e " +
                "LEFT JOIN calificacion c ON e.ID = c.idEstudiante AND c.idPeriodo = ? " +
                "WHERE e.tipoGuadiaFK = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, PERIODO_ACTUAL);
            pstmt.setString(2, tipoGuardia);

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
        //INSERT:
        String insertSql = "INSERT INTO calificacion (idEstudiante, idPeriodo, calificacionFinal) VALUES (?, ?, ?)";
        //UPDATE:
        String updateSql = "UPDATE calificacion SET calificacionFinal = ? WHERE idEstudiante = ? AND idPeriodo = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            //Transaccion
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

                int rows = tableModel.getRowCount();
                for (int i = 0; i < rows; i++) {
                    String idEstudiante = (String) tableModel.getValueAt(i, 0);
                    Object nuevaCalificacionObj = tableModel.getValueAt(i, 3);
                    Object calificacionAnteriorObj = tableModel.getValueAt(i, 2);

                    if (nuevaCalificacionObj == null) {
                        continue;
                    }

                    Float nuevaCalificacion = ((Number) nuevaCalificacionObj).floatValue();

                    //Validación(0 a 100)
                    if (nuevaCalificacion < 0 || nuevaCalificacion > 100) {
                        JOptionPane.showMessageDialog(this, "La calificación debe estar entre 0 y 100 para el estudiante " + idEstudiante, "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        conn.rollback();
                        return;
                    }

                    //INSERT o UPDATE
                    if (calificacionAnteriorObj == null) {
                        //Si no hay calificación -> INSERT
                        insertStmt.setString(1, idEstudiante);
                        insertStmt.setString(2, PERIODO_ACTUAL);
                        insertStmt.setFloat(3, nuevaCalificacion);
                        insertStmt.addBatch();
                    } else {
                        //si hay calificación -> UPDATE
                        updateStmt.setFloat(1, nuevaCalificacion);
                        updateStmt.setString(2, idEstudiante);
                        updateStmt.setString(3, PERIODO_ACTUAL);
                        updateStmt.addBatch();
                    }
                }

                insertStmt.executeBatch();
                updateStmt.executeBatch();

                conn.commit(); // Commit
                JOptionPane.showMessageDialog(this, "Calificaciones guardadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Recargar los datos
                cargarEstudiantesYCalificaciones((String) guardiaSelector.getSelectedItem());

            } catch (SQLException ex) {
                conn.rollback(); // Rollback si hay un error
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error de DB", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}

