package org.FGI.tarea;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.sql.*;

public class fsd extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public fsd() {
        setTitle("Administrar Usuarios");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Usuario");
        modeloTabla.addColumn("Cargo");
        modeloTabla.addColumn("Departamento");
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarUsuario());
        JButton btnBorrar = new JButton("Borrar");
        btnBorrar.addActionListener(e -> borrarUsuario());
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volver());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnModificar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            statement = conn.createStatement();
            String query = "SELECT nombre, apellido, correo_usuario, cargo, departamento FROM usuarios";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String correo = resultSet.getString("correo_usuario");
                String cargo = resultSet.getString("cargo");
                String departamento = resultSet.getString("departamento");
                modeloTabla.addRow(new Object[]{nombre, apellido, correo, cargo, departamento});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void modificarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String apellido = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            String correo_usuario = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
            String cargo = (String) modeloTabla.getValueAt(filaSeleccionada, 3);
            String departamento = (String) modeloTabla.getValueAt(filaSeleccionada, 4);


            JTextField txtNuevoNombre = new JTextField(nombre);
            JTextField txtNuevoApellido = new JTextField(apellido);
            JTextField txtNuevoCorreo = new JTextField(correo_usuario);
            JTextField txtNuevoCargo = new JTextField(cargo);
            JTextField txtNuevoDepartamento = new JTextField(departamento);

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Nuevo Nombre:"));
            panel.add(txtNuevoNombre);
            panel.add(new JLabel("Nuevo Apellido:"));
            panel.add(txtNuevoApellido);
            panel.add(new JLabel("Nuevo Correo:"));
            panel.add(txtNuevoCorreo);
            panel.add(new JLabel("Nuevo Cargo:"));
            panel.add(txtNuevoCargo);
            panel.add(new JLabel("Nuevo Departamento:"));
            panel.add(txtNuevoDepartamento);

            int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar Usuario",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion == JOptionPane.OK_OPTION) {
                String nuevoNombre = txtNuevoNombre.getText().trim();
                String nuevoApellido = txtNuevoApellido.getText().trim();
                String nuevoCorreo = txtNuevoCorreo.getText().trim();
                String nuevoCargo = txtNuevoCargo.getText().trim();
                String nuevoDepartamento = txtNuevoDepartamento.getText().trim();


                Connection conn = null;
                PreparedStatement statement = null;
                try {
                    conn = Conexion.conectar();
                    String query = "UPDATE usuarios SET nombre = ?, apellido = ?, correo_usuario = ?, cargo = ?, departamento = ? WHERE nombre = ? AND apellido = ?";
                    statement = conn.prepareStatement(query);
                    statement.setString(1, nuevoNombre);
                    statement.setString(2, nuevoApellido);
                    statement.setString(3, nuevoCorreo);
                    statement.setString(4, nuevoCargo);
                    statement.setString(5, nuevoDepartamento);
                    statement.setString(6, nombre);
                    statement.setString(7, apellido);
                    statement.executeUpdate();


                    cargarUsuarios();

                    JOptionPane.showMessageDialog(this, "Usuario modificado exitosamente");
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
        }
    }

    private void borrarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String apellido = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            int opcion = JOptionPane.showConfirmDialog(this, "¿Desea borrar el usuario: " + nombre + " " + apellido + "?",
                    "Borrar Usuario", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                // Borrar el usuario de la base de datos
                Connection conn = null;
                PreparedStatement statement = null;
                try {
                    conn = Conexion.conectar();
                    String query = "DELETE FROM usuarios WHERE nombre = ? AND apellido = ?";
                    statement = conn.prepareStatement(query);
                    statement.setString(1, nombre);
                    statement.setString(2, apellido);
                    statement.executeUpdate();


                    cargarUsuarios();

                    JOptionPane.showMessageDialog(this, "Usuario borrado exitosamente");
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
        }
    }

    private void volver() {

        dispose();


       opciones opciones = new opciones();
        opciones.setVisible(true);
    }



    }





