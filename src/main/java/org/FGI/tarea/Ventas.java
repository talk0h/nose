package org.FGI.tarea;
import javax.swing.*;
import java.awt.*;

public class Ventas extends JFrame {
    public Ventas () {
        setTitle("Área de Trabajo - Ventas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Área de Trabajo - Ventas");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JTextArea txtAreaTrabajo = new JTextArea();
        txtAreaTrabajo.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(txtAreaTrabajo);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Informacion guardada" );
        });
        panelPrincipal.add(btnGuardar, BorderLayout.SOUTH);

        add(panelPrincipal);

        setVisible(true);
    }

}
