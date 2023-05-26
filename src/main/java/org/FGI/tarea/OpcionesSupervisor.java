package org.FGI.tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpcionesSupervisor extends JFrame implements ActionListener {
    private String departamento;
    private JButton btnAreaTrabajo;
    private JButton btnAgregarUsuario;
    private JButton btnAdministrarUsuarios;

    public OpcionesSupervisor() {
        this.departamento = departamento;
        setTitle("Opciones - Supervisor de " + departamento);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        btnAreaTrabajo = new JButton("Área de Trabajo");
        btnAgregarUsuario = new JButton("Agregar Usuario");
        btnAdministrarUsuarios = new JButton("Administrar Usuarios");

        btnAreaTrabajo.addActionListener(this);
        btnAgregarUsuario.addActionListener(this);
        btnAdministrarUsuarios.addActionListener(this);

        add(btnAreaTrabajo);
        add(btnAgregarUsuario);
        add(btnAdministrarUsuarios);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAreaTrabajo) {

        } else if (e.getSource() == btnAgregarUsuario) {

        } else if (e.getSource() == btnAdministrarUsuarios) {

        }
    }
}
