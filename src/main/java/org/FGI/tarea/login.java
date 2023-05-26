package org.FGI.tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends JFrame implements ActionListener {
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    public login() {
        setTitle("Iniciar Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(this);

        add(lblUsuario);
        add(txtUsuario);
        add(lblContrasena);
        add(txtContrasena);
        add(new JLabel());
        add(btnIniciarSesion);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnIniciarSesion) {
            String usuario = txtUsuario.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());
            if ("admin@fgi.com".equals(usuario) && "adminfgi456".equals(contrasena)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso como administrador");
                dispose(); // Cerrar la ventana de inicio de sesión
                opciones opciones = new opciones();
                opciones.setVisible(true);
            } else {
                String cargo = verificarCredenciales(usuario, contrasena);
                if ("Supervisor".equals(cargo)) {
                    JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso como supervisor");
                    dispose(); // Cerrar la ventana de inicio de sesión
                    OpcionesSupervisor opcionesSupervisor = new OpcionesSupervisor();
                    opcionesSupervisor.setVisible(true);
                } else {
                    String departamento = obtenerDepartamento(usuario);
                    if (departamento != null) {
                        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso como empleado de " + departamento);
                        dispose(); // Cerrar la ventana de inicio de sesión

                        if ("Ventas".equals(departamento)) {
                            Ventas ventas = new Ventas();
                            ventas.setVisible(true);
                        } else if ("Finanzas".equals(departamento)) {
                            Finanzas finanzas = new Finanzas();
                            finanzas.setVisible(true);
                        } else if ("Recursos Humanos".equals(departamento)) {
                            RecursosHumanos recursosHumanos = new RecursosHumanos();
                            recursosHumanos.setVisible(true);
                        } else if ("Marketing".equals(departamento)) {
                            Marketing marketing = new Marketing();
                            marketing.setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
                    }
                }
            }
        }
    }

    private String verificarCredenciales(String usuario, String contrasena) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = conectar();
            String query = "SELECT cargo FROM usuarios WHERE correo_usuario = ? AND contrasena = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("cargo");
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

        return null;
    }

    private String obtenerDepartamento(String usuario) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = conectar();
            String query = "SELECT departamento FROM usuarios WHERE correo_usuario = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, usuario);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("departamento");
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

        return null;
    }

    private Connection conectar() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/yes";
        String usuarioBD = "root";
        String contrasenaBD = "";
        return DriverManager.getConnection(url, usuarioBD, contrasenaBD);
    }


}
