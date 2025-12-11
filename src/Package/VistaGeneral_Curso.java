package Package;

import javax.swing.*;
import java.awt.*;

public class VistaGeneral_Curso extends JFrame{
    private JButton btnRegistrarCurso;
    private JButton btnListarCursos;

    public VistaGeneral_Curso() {
        setTitle("Menú principal - Gestión de Cursos");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {
        setLayout(new GridLayout(2, 1, 10, 20));

        btnRegistrarCurso = new JButton("Registrar Curso");
        btnListarCursos = new JButton("Consultar Cursos");

        add(btnRegistrarCurso);
        add(btnListarCursos);

        // Abrir la vista de registro
        btnRegistrarCurso.addActionListener(e -> {
            RegistrarCursoForm registro = new RegistrarCursoForm();
            registro.setVisible(true);
        });

        // Abrir la vista de listado
        btnListarCursos.addActionListener(e -> {
            //ListarCursosForm lista = new ListarCursosForm();
            //lista.setVisible(true);
        });
    }
}
