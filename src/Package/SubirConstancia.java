package Package;

import javax.swing.*;
import java.awt.*;

public class SubirConstancia extends JFrame {
    public JButton subirConstancia;
    public JTextField numSerie;
    public JTextField fechaCurso;
    public JTextField idCurso;
    public JTextField numCurso;
    public JTextField tamanioArchivo;
    public JTextField formatoArchivo;
    String[] especialidades = {"Medicina", "Enfermeria", "Nutricion", "Odontología"};
    public JComboBox<String> especialidadesComboBox = new JComboBox<>(especialidades);
    String[] cursos = {"1 Acciones Esenciales para la Seguridad del Paciente en el marco del Modelo Único de Evaluación de la Calidad (AESP‑MUEC)", "2 Trato Digno en los Servicios de Salud", "3 Interculturalidad en Salud", "4 Higiene de Manos, una práctica segura", "5 Principios de Propedéutica Médica y Expediente Clínico", "6 Medicina preventiva en adultos mayores", "7 Triage-profesional médico", "8 Enfermedad por coronavirus 2019 (COVID-19)", "9 COVID 19: Temas selectos para el personal médico", "10 Diagnóstico y tratamiento de los trastornos mentales en el primer nivel de atención", "11 Atención de Enfermería en el paciente con enfermedades crónicas no transmisibles", "12 Trastorno depresivo mayor", "13 Introducción a la Atención Primaria de Salud", "14 Diversidad sexo-genérica", "15 violencia y  género", "16 Trato digno", "17 Administración segura de medicamentos","18 Diagnóstico y tratamiento de la esclerosis múltiple", "19 Atención integral del niño", "20 Medicina basada en evidencia", "21 Temas selectos de nutrición", "22 Código delirium para el personal de Enfermería ", "23 Bases para el tratamiento nutricional", "24 Nutrición en situaciones especiales", "25 Nutrición en pacientes con padecimientos digestivos", "26 Diagnóstico y tratamiento de los principales traumatismos en el primer nivel de atención", "27 Economía de la salud: Temas selectos", "28 Lactancia materna", "29 Rehabilitación integral pos-COVID-19: aspectos cardiovasculares"};
    public JComboBox<String> cursosComboBox = new JComboBox<>(cursos);
    private Consult_Database mydatabase;


    public SubirConstancia(Consult_Database mydatabase) {
        setTitle("Subir Constancia");
        setSize(400, 200);
        inicializarComponentes();

        JPanel centro = new JPanel();
        centro.setLayout(null);

        especialidadesComboBox.setBounds(125, 50, 150, 30);
        centro.add(especialidadesComboBox);

        cursosComboBox.setBounds(125, 100, 150, 30);
        centro.add(cursosComboBox);

        subirConstancia.setBounds(125, 100, 150, 30);
        centro.add(subirConstancia);

        numSerie.setBounds(125, 100, 150, 30);
        centro.add(numSerie);

        fechaCurso.setBounds(125, 100, 150, 30);
        centro.add(fechaCurso);

        idCurso.setBounds(125, 100, 150, 30);
        centro.add(idCurso);

        tamanioArchivo.setBounds(125, 100, 150, 30);
        centro.add(tamanioArchivo);

        formatoArchivo.setBounds(125, 100, 150, 30);
        centro.add(formatoArchivo);

        numCurso.setBounds(125, 100, 150, 30);
        centro.add(numCurso);

        this.add(centro, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.mydatabase=mydatabase;
    }

    public void inicializarComponentes(){
        subirConstancia = new JButton("Subir Constancia");
        especialidadesComboBox = new JComboBox<>(especialidades);
        cursosComboBox = new JComboBox<>(cursos);
        numSerie = new JTextField();
        fechaCurso = new JTextField("DD-MM-AAAA");
        idCurso = new JTextField();
        numCurso = new JTextField();
        tamanioArchivo = new JTextField();
        formatoArchivo = new JTextField();

    }
}
