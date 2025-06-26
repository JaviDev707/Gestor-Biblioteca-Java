package com.mibiblioteca.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mibiblioteca.Modelo.Usuario;

public class UsuarioDAO {
    
    private Connection conexion;

    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Metodo para obtener todos los datos de la tabla usuarios
     */
    public List<Usuario> obtenerTodos() throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios;";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            usuarios.add(crearUsuario(rs));
        }
        return usuarios;
    }
    /**
     * Metodo que busca al usuario por su email
     */
    public Usuario consultaEmail(String email) throws SQLException{

        String sql = "SELECT * FROM usuarios WHERE email = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return crearUsuario(rs);
        } else {
            System.out.println("No se ha encontrado un usuario asociado a ese email");
            return null;
        }
        
    }
    /**
     * Metodo que busca al usuario por su id
     */
    public Usuario filtroId(int id) throws SQLException{

        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return crearUsuario(rs);
        } else {
            System.out.println("El usuario con id " + id + " no exite");
            return null;
        }
    }
    /**
     * Metodo que inserta una nueva fila de la tabla usuarios
     */
    public void insertarUsuario(Usuario usuario) throws SQLException{

        String sql = "INSERT INTO usuarios (nombre, apellido, email) VALUES (?, ?, ?)";

        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, usuario.getNombre());
        stmt.setString(2, usuario.getApellido());
        stmt.setString(3, usuario.getEmail());
        stmt.executeUpdate();   
    }
    /**
     * Metodo que borra un usuario por su id
     */
    public boolean deleteUsuario(int id) throws SQLException{

        String sql = "DELETE FROM usuarios WHERE id_usuario = ?;";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            //Devuelve true si se elimina al menos una fila
            return stmt.executeUpdate() > 0;   
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            System.out.println("No puede borrar un usuario con prestamos activos!");
            return false;
        }
        
    }


    
    /**
     * Metodo privado que crea un objeto Usuario
     */
    private Usuario crearUsuario(ResultSet rs) throws SQLException{
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("email")
            );
    }
}
