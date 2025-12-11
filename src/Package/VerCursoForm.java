package Package;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class VerCursoForm extends JFrame {

    public VerCursoForm(int id) {
        setTitle("Información del curso");
        setSize(480, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Consult_Database cd = new Consult_Database();
        Curso c = cd.getCursoByID(id);

        if (c == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró la información del curso",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setLayout(new BorderLayout(10, 10));

        JPanel panelDatos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        // Helper para agregar filas
        BiConsumer<String, String> addRow = (label, value) -> {
            gbc.gridx = 0;
            panelDatos.add(new JLabel(label + ":"), gbc);

            gbc.gridx = 1;
            panelDatos.add(new JLabel(value), gbc);

            gbc.gridy++;
        };

        // Datos del curso
        addRow.accept("Nombre del curso", c.getNombreCurso());
        addRow.accept("Obligatorio", c.isObligatorio() ? "Sí" : "No");

        addRow.accept("Fecha inicio",
                c.getFechaInicio() != null ? c.getFechaInicio().toString() : "Sin fecha");

        addRow.accept("Fecha fin",
                c.getFechaFin() != null ? c.getFechaFin().toString() : "Sin fecha");

        // Especialidades
        addRow.accept(
                "Especialidades asignadas",
                "<html>" + obtenerEspecialidadesAsignadas(c).replace("\n", "<br>") + "</html>"
        );

        // Se agrega panel central
        add(panelDatos, BorderLayout.CENTER);

        // Botón cerrar
        JPanel panelBoton = new JPanel();
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBoton.add(btnCerrar);

        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Genera la lista de especialidades
    private String obtenerEspecialidadesAsignadas(Curso c) {
        StringBuilder sb = new StringBuilder();

        if (c.isMedicina()) sb.append("Medicina\n");
        if (c.isEnfermeria()) sb.append("Enfermería\n");
        if (c.isOdontologia()) sb.append("Odontología\n");
        if (c.isNutriologia()) sb.append("Nutriología\n");

        if (sb.length() == 0)
            return "Ninguna";

        return sb.toString();
    }
}