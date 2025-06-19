package com.mibiblioteca.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mibiblioteca.Modelo.Libro;
import com.mibiblioteca.Modelo.Prestamo;
import com.mibiblioteca.Modelo.Usuario;

public class PrestamoDAO {
    
    private Connection conexion;
    private final String BASE_QUERY = "SELECT usuarios.id_usuario, usuarios.nombre, usuarios.apellido, usuarios.email,\n" + //
                        "libros.isbn, libros.nombre, libros.año, autores.nombre, autores.apellido1, autores.apellido2, generos.genero,\n" + //
                        "prestamos.id_prestamo, prestamos.fecha_prestamo\n" + //
                        "FROM prestamos\n" + //
                        "JOIN usuarios ON prestamos.id_usuario = usuarios.id_usuario\n" + //
                        "JOIN libros ON prestamos.isbn = libros.isbn\n" + //
                        "JOIN autores ON libros.id_autor = autores.id_autor\n" + //
                        "JOIN generos ON libros.id_genero = generos.id_genero\n";

    public PrestamoDAO(Connection conexion){
        this.conexion = conexion;
    }

    /**
     * Metodo que inserta una nueva fila en la tabla prestamos
     */
    public void insertarPrestamo(int id_usuario, int isbn) throws SQLException{

        String sql = "INSERT INTO prestamos (id_usuario, isbn) VALUES (?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id_usuario);
        stmt.setInt(2, isbn);
        stmt.executeUpdate();
    }
    /**
     * Metodo que comprueba si el libro esta prestado (se encuentra en la tabla prestamos)
     */
    public boolean comprobarPrestamo(int isbn) throws SQLException{
        
        String sql = "SELECT * FROM prestamos WHERE isbn = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, isbn);
        ResultSet rs = stmt.executeQuery();

        if (rs.next() == true) {
            return true;
        } else return false;
    }
    /**
     * Metodo que devuelve todas las filas de la tabla prestamos
     */
    public List<Prestamo> obtenerTodos() throws SQLException {

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = BASE_QUERY + "ORDER BY prestamos.id_usuario";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            prestamos.add(crearPrestamo(rs));
        }
        return prestamos;
    }
    /**
     * Metodo que devuelve los prestamos filtrados por usuario
     */
    public List<Prestamo> filtroUsuario(int id_usuario) throws SQLException{

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = BASE_QUERY + " WHERE prestamos.id_usuario = ? ORDER BY prestamos.id_usuario";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id_usuario);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            prestamos.add(crearPrestamo(rs));
        }
        return prestamos;
    }
    /**
     * Metodo que borra una fila de la tabla prestamos
     */
    public boolean deletePrestamo(int id) throws SQLException{

        String sql = "DELETE FROM prestamos WHERE id_prestamo = ?;";

        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id);
        //Devuelve true si se elimina al menos una fila
        return stmt.executeUpdate() > 0;   
    }
    /**
     * Metodo privado que crea un objeto Prestamo
     */
    private Prestamo crearPrestamo(ResultSet rs) throws SQLException{

        return new Prestamo(
                rs.getInt("prestamos.id_prestamo"), 
                new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email")),
                new Libro(rs.getInt("libros.isbn"),
                    rs.getString("libros.nombre"),
                    rs.getInt("libros.año"), 
                    rs.getString("autores.nombre") + " " + rs.getString("autores.apellido1") + " " + rs.getString("autores.apellido2"),
                    rs.getString("generos.genero")),
                rs.getDate("prestamos.fecha_prestamo"), 
                null);
    }

}
