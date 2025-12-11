package Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarCursosForm extends JFrame{
    private JTable tablaCursos;
    private DefaultTableModel modeloTabla;

    private JButton btnVerInfo;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private Consult_Database db;

    public ListarCursosForm() {
        setTitle("Lista de Cursos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        db = new Consult_Database();

        inicializarComponentes();
        cargarCursos();

        setVisible(true);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Inicio", "Fin"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCursos = new JTable(modeloTabla);
        tablaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnVerInfo = new JButton("Ver información");
        btnActualizar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnVerInfo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        btnVerInfo.addActionListener(e -> verInformacionCurso());
        btnActualizar.addActionListener(e -> actualizarCurso());
        btnEliminar.addActionListener(e -> eliminarCurso());
    }

    private void cargarCursos() {
        List<Curso> cursos = db.getAllCursos();  // MÉTODO ADAPTADO

        modeloTabla.setRowCount(0);

        for (Curso c : cursos) {
            modeloTabla.addRow(new Object[]{
                    c.getId(),
                    c.getNombreCurso(),
                    c.getFechaInicio(),
                    c.getFechaFin()
            });
        }

        actualizarEstadoBotones();
    }

    private void actualizarEstadoBotones() {
        boolean hayCursos = modeloTabla.getRowCount() > 0;

        btnActualizar.setEnabled(hayCursos);
        btnEliminar.setEnabled(hayCursos);
        btnVerInfo.setEnabled(hayCursos);

        if (!hayCursos) {
            JOptionPane.showMessageDialog(this, "No hay cursos registrados");
        }

    }

    //Obtiene la fila seleccionada
    private int obtenerIdSeleccionado() {
        int fila = tablaCursos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso primero.");
            return -1;
        }

        Object valor = modeloTabla.getValueAt(fila, 0);

        try {
            if (valor instanceof Integer) {
                return (Integer) valor;
            } else {
                return Integer.parseInt(valor.toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener el ID del curso.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    private void verInformacionCurso() {
        int id = obtenerIdSeleccionado();
        if (id == -1) return;

        //new VerCursoForm(id);
    }

    private void actualizarCurso() {
        int id = obtenerIdSeleccionado();
        if (id == -1) return;

        //new EditarCursoForm(id);
        cargarCursos();
    }

    private void eliminarCurso() {
        int id = obtenerIdSeleccionado();
        if (id == -1) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este curso?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            db.deleteCurso(id);
            cargarCursos();
        }
    }

}
