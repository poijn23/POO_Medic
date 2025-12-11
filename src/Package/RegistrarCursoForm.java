package Package;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistrarCursoForm extends JFrame {

    private JTextField txtId;      // NUEVO CAMPO
    private JTextField txtNombre;
    private JCheckBox chkObligatorio, chkMedicina, chkEnfermeria, chkOdontologia, chkNutriologia;
    private JFormattedTextField txtFechaInicio, txtFechaFin;
    private JButton btnGuardar, btnLimpiar;

    private Consult_Database db;

    public RegistrarCursoForm(Consult_Database db) {
        setTitle("Registrar curso");
        setSize(600, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.db = db;

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {

        // Formato de fecha
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        formato.setLenient(false);
        DateFormatter dateFormatter = new DateFormatter(formato);

        setLayout(new BorderLayout(10, 10));

        // Panel principal del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 8, 8));

        txtId = new JTextField();   // NUEVO
        txtNombre = new JTextField();

        chkObligatorio = new JCheckBox("Obligatorio");
        chkMedicina = new JCheckBox("Medicina");
        chkEnfermeria = new JCheckBox("Enfermería");
        chkOdontologia = new JCheckBox("Odontología");
        chkNutriologia = new JCheckBox("Nutriología");

        txtFechaInicio = new JFormattedTextField(dateFormatter);
        txtFechaFin = new JFormattedTextField(dateFormatter);

        // Añadir campos al formulario
        panelFormulario.add(new JLabel("ID del curso:"));     // NUEVO
        panelFormulario.add(txtId);

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

        add(panelFormulario, BorderLayout.NORTH);

        // Panel inferior
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(new JLabel("Asegúrese de revisar que la información sea correcta"));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(e -> guardar());
        btnLimpiar.addActionListener(e -> limpiar());

        chkObligatorio.addActionListener(e -> {
            if (chkObligatorio.isSelected()) chkMedicina.setSelected(true);
        });
    }

    private void guardar() {
        try {
            // validación Id vacío
            String idTexto = txtId.getText().trim();
            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el ID del curso.");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.");
                return;
            }

            // Verificar si ya existe el Id
            if (db.existsCurso(id)) {
                JOptionPane.showMessageDialog(this, "El ID ingresado ya existe. Ingrese otro ID.");
                return;
            }

            // validación nombre vacío
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            String inicioTexto = txtFechaInicio.getText().trim();
            String finTexto = txtFechaFin.getText().trim();

            // Validación de fechas vacías
            if (inicioTexto.isEmpty() || finTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar ambas fechas.");
                return;
            }

            LocalDate inicio = LocalDate.parse(inicioTexto);
            LocalDate fin = LocalDate.parse(finTexto);

            if (fin.isBefore(inicio)) {
                JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la fecha de inicio.");
                return;
            }

            // validación una especialidad seleccionada
            if (!chkMedicina.isSelected() &&
                    !chkEnfermeria.isSelected() &&
                    !chkOdontologia.isSelected() &&
                    !chkNutriologia.isSelected()) {

                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una especialidad.");
                return;
            }

            // Crear el objeto Curso
            Curso c = new Curso();
            c.setId(id);
            c.setNombreCurso(txtNombre.getText().trim());
            c.setObligatorio(chkObligatorio.isSelected());
            c.setMedicina(chkMedicina.isSelected());
            c.setEnfermeria(chkEnfermeria.isSelected());
            c.setOdontologia(chkOdontologia.isSelected());
            c.setNutriologia(chkNutriologia.isSelected());
            c.setFechaInicio(inicio);
            c.setFechaFin(fin);

            boolean ok = db.createCurso(c);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Curso registrado correctamente.");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el curso.");
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado al registrar el curso.");
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        chkObligatorio.setSelected(false);
        chkMedicina.setSelected(false);
        chkEnfermeria.setSelected(false);
        chkOdontologia.setSelected(false);
        chkNutriologia.setSelected(false);
    }
}