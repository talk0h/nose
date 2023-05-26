package org.FGI.tarea;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/yes";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public static void guardarDatos(String nombre, String apellido, String correoUsuario, String cargo, String departamento, String contrasena) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = conectar();
            String query = "INSERT INTO usuarios (nombre, apellido, correo_usuario, cargo, departamento, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correoUsuario);
            statement.setString(4, cargo);
            statement.setString(5, departamento);
            statement.setString(6, contrasena);
            statement.executeUpdate();
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

    public static List<Map<String, String>> obtenerUsuarios() {
        List<Map<String, String>> usuarios = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = conectar();
            String query = "SELECT * FROM usuarios";
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Map<String, String> usuario = new HashMap<>();
                usuario.put("nombre", resultSet.getString("nombre"));
                usuario.put("apellido", resultSet.getString("apellido"));
                usuario.put("correoUsuario", resultSet.getString("correo_usuario"));
                usuario.put("cargo", resultSet.getString("cargo"));
                usuario.put("departamento", resultSet.getString("departamento"));
                usuario.put("contrasena", resultSet.getString("contrasena"));

                usuarios.add(usuario);
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


        Collections.sort(usuarios, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> u1, Map<String, String> u2) {
                int comparacionDepartamento = u1.get("departamento").compareToIgnoreCase(u2.get("departamento"));
                if (comparacionDepartamento != 0) {
                    return comparacionDepartamento;
                }

                int comparacionCargo = u1.get("cargo").compareToIgnoreCase(u2.get("cargo"));
                if (comparacionCargo != 0) {
                    return comparacionCargo;
                }

                return u1.get("nombre").compareToIgnoreCase(u2.get("nombre"));
            }
        });

        return usuarios;
    }

    public static String obtenerCargo(String correoUsuario) {
        String cargo = null;
        try (Connection conn = conectar();
             PreparedStatement statement = conn.prepareStatement("SELECT cargo FROM usuarios WHERE correo_usuario = ?")) {
            statement.setString(1, correoUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cargo = resultSet.getString("cargo");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cargo;
    }


    public static String obtenerDepartamento(String correoUsuario) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = conectar();
            String query = "SELECT departamento FROM usuarios WHERE correo_usuario = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, correoUsuario);
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
}
