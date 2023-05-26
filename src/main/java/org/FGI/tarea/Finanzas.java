package org.FGI.tarea;

import javax.swing.*;
import java.awt.*;

public class Finanzas extends JFrame {
    private String departamento;

    public Finanzas() {
        this.departamento = departamento;
        setTitle("Área de Trabajo - Finanzas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Área de Trabajo - Finanzas");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JTextArea txtAreaTrabajo = new JTextArea();
        txtAreaTrabajo.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(txtAreaTrabajo);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Información guardada");
        });
        panelPrincipal.add(btnGuardar, BorderLayout.SOUTH);

        add(panelPrincipal);

        setVisible(true);
    }
}

