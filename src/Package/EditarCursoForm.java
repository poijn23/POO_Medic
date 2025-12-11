package Package;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditarCursoForm extends JFrame{

    private JTextField txtNombre;
    private JCheckBox chkObligatorio, chkMedicina, chkEnfermeria, chkOdontologia, chkNutriologia;
    private JFormattedTextField txtFechaInicio, txtFechaFin;
    private JButton btnActualizar, btnCancelar;

    private int idCurso;

    public EditarCursoForm(int id) {
        this.idCurso = id;

        setTitle("Editar curso");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarDatos(id);

        setVisible(true);
    }

    private void inicializarComponentes() {
        // Formato de fecha
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setLenient(false);
        DateFormatter dateFormatter = new DateFormatter(formato);

        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));

        txtNombre = new JTextField();

        chkObligatorio = new JCheckBox("Obligatorio");
        chkMedicina = new JCheckBox("Medicina");
        chkEnfermeria = new JCheckBox("Enfermería");
        chkOdontologia = new JCheckBox("Odontología");
        chkNutriologia = new JCheckBox("Nutriología");

        txtFechaInicio = new JFormattedTextField(dateFormatter);
        txtFechaFin = new JFormattedTextField(dateFormatter);

        panelFormulario.add(new JLabel("Nombre del curso:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Obligatorio:"));
        panelFormulario.add(chkObligatorio);

        panelFormulario.add(new JLabel("Especialidades:"));
        JPanel panelChecks = new JPanel(new GridLayout(2, 2));
        panelChecks.add(chkMedicina);
        panelChecks.add(chkEnfermeria);
        panelChecks.add(chkOdontologia);
        panelChecks.add(chkNutriologia);
        panelFormulario.add(panelChecks);

        panelFormulario.add(new JLabel("Fecha inicio (YYYY-MM-DD):"));
        panelFormulario.add(txtFechaInicio);

        panelFormulario.add(new JLabel("Fecha fin (YYYY-MM-DD):"));
        panelFormulario.add(txtFechaFin);

        add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();

        btnActualizar = new JButton("Guardar cambios");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnActualizar.addActionListener(e -> actualizar());
        btnCancelar.addActionListener(e -> dispose());
    }

    //Cargar los datos predefinidos
    private void cargarDatos(int id) {
        Consult_Database cd = new Consult_Database();
        Curso c = cd.getCursoByID(id);

        if (c == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el curso",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        txtNombre.setText(c.getNombreCurso());
        chkObligatorio.setSelected(c.isObligatorio());
        chkMedicina.setSelected(c.isMedicina());
        chkEnfermeria.setSelected(c.isEnfermeria());
        chkOdontologia.setSelected(c.isOdontologia());
        chkNutriologia.setSelected(c.isNutriologia());

        txtFechaInicio.setText(c.getFechaInicio().toString());
        txtFechaFin.setText(c.getFechaFin().toString());
    }

    //Actualizar el curso
    private void actualizar() {
        try {

            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            String inicioTexto = txtFechaInicio.getText().trim();
            String finTexto = txtFechaFin.getText().trim();

            if (inicioTexto.isEmpty() || finTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar ambas fechas.");
                return;
            }

            LocalDate inicio = LocalDate.parse(inicioTexto);
            LocalDate fin = LocalDate.parse(finTexto);

            if (fin.isBefore(inicio)) {
                JOptionPane.showMessageDialog(this,
                        "La fecha de fin debe ser después de la fecha de inicio.");
                return;
            }

            if (!chkMedicina.isSelected() &&
                    !chkEnfermeria.isSelected() &&
                    !chkOdontologia.isSelected() &&
                    !chkNutriologia.isSelected()) {

                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar al menos una especialidad.");
                return;
            }

            // Construir curso actualizado
            Curso c = new Curso();
            c.setId(idCurso);
            c.setNombreCurso(txtNombre.getText().trim());
            c.setObligatorio(chkObligatorio.isSelected());
            c.setMedicina(chkMedicina.isSelected());
            c.setEnfermeria(chkEnfermeria.isSelected());
            c.setOdontologia(chkOdontologia.isSelected());
            c.setNutriologia(chkNutriologia.isSelected());
            c.setFechaInicio(inicio);
            c.setFechaFin(fin);

            Consult_Database cd = new Consult_Database();

            if (cd.updateCurso(c)) {
                JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar el curso.");
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido.");
        }
    }

}
