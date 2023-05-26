package org.FGI.tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class opciones extends JFrame implements ActionListener {
    private JButton btnAgregarUsuario;
    private JButton btnAdministrarUsuarios;

    public opciones() {
        setTitle("Ventana de Opciones");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        btnAgregarUsuario = new JButton("Agregar Usuario");
        btnAgregarUsuario.addActionListener(this);
        btnAdministrarUsuarios = new JButton("Administrar Usuarios");
        btnAdministrarUsuarios.addActionListener(this);

        add(btnAgregarUsuario);
        add(btnAdministrarUsuarios);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregarUsuario) {
            Interfazusu interfazusu = new Interfazusu();
            interfazusu.setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnAdministrarUsuarios) {
            fsd fsd = new fsd();
            fsd.setVisible(true);
            this.dispose();
    }


    }}

