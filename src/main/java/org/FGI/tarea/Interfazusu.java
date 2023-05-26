package org.FGI.tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfazusu extends JFrame {
    private JTextField txtNombreUsuario;
    private JTextField txtApellidoUsuario;
    private JTextField txtCargo;
    private JComboBox<String> cmbDepartamento;
    private JPasswordField txtContrasena;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    public Interfazusu() {
        setTitle("Registro de Usuario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        JLabel lblNombreUsuario = new JLabel("Nombre:");
        txtNombreUsuario = new JTextField();
        JLabel lblApellidoUsuario = new JLabel("Apellido:");
        txtApellidoUsuario = new JTextField();

        JLabel lblCargo = new JLabel("Cargo:");
        txtCargo = new JTextField();
        JLabel lblDepartamento = new JLabel("Departamento:");
        cmbDepartamento = new JComboBox<>(new String[]{"Finanzas", "Ventas", "Marketing", "Recursos Humanos"});
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarUsuario());
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volver());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(lblNombreUsuario);
        add(txtNombreUsuario);
        add(lblApellidoUsuario);
        add(txtApellidoUsuario);
        add(lblCargo);
        add(txtCargo);
        add(lblDepartamento);
        add(cmbDepartamento);
        add(lblContrasena);
        add(txtContrasena);
        add(panelBotones);

        setVisible(true);
    }

    private void guardarUsuario() {
        String nombre = txtNombreUsuario.getText().trim();
        String apellido = txtApellidoUsuario.getText().trim();


        String[] nombrePartes = nombre.split(" ");
        String[] apellidoPartes = apellido.split(" ");
        String primerNombre = nombrePartes[0];
        String primerApellido = apellidoPartes[0];

        String correoUsuario = primerNombre + "_" + primerApellido + "@fgi.com";
        String cargo = txtCargo.getText().trim();
        String departamento = (String) cmbDepartamento.getSelectedItem();
        String contrasena = new String(txtContrasena.getPassword());


        if (nombre.isEmpty() || apellido.isEmpty() || cargo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.");
            return;
        }


        Conexion.guardarDatos(nombre, apellido, correoUsuario, cargo, departamento, contrasena);

        JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");

        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombreUsuario.setText("");
        txtApellidoUsuario.setText("");
        txtCargo.setText("");
        txtContrasena.setText("");
    }

    private void volver() {
        dispose();
        opciones opciones = new opciones();
        opciones.setVisible(true);
    }
}
