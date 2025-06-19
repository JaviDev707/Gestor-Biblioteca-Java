package com.mibiblioteca.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mibiblioteca.Modelo.Libro;

public class LibroDAO {
    
    private Connection conexion;
    private final String BASE_QUERY = "SELECT libros.isbn, libros.nombre, libros.año, autores.nombre, autores.apellido1, autores.apellido2, generos.genero FROM libros\n" + //
                        "JOIN autores\n" + //
                        "ON libros.id_autor = autores.id_autor\n" + //
                        "JOIN generos\n" + //
                        "ON libros.id_genero = generos.id_genero\n";

    public LibroDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Metodo para obtener todos los datos de la tabla libros
     */
    public List<Libro> obtenerTodos() throws SQLException {

        List<Libro> libros = new ArrayList<>();
        String sql = BASE_QUERY + "ORDER BY libros.isbn;";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            libros.add(crearLibro(rs));
        }
        return libros;
    }
    /**
     * Metodo que devuelve 1 libro segun su ISBN
     */
    public Libro filtroISBN(int isbn) throws SQLException{
        
        String sql = BASE_QUERY + "WHERE libros.isbn = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, isbn);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Libro(rs.getInt("libros.isbn"),
            rs.getString("libros.nombre"),
            rs.getInt("libros.año"), 
            rs.getString("autores.nombre") + " " + rs.getString("autores.apellido1") + " " + rs.getString("autores.apellido2"),
            rs.getString("generos.genero")
        );
        } else {
            System.out.println("No se ha encontrado un libro con ese ISBN");
            return null;
        }
        
    }
    /**
     * Metodo que devuelve libros filtrados por su autor
     */
    public List<Libro> filtroAutor(String nombre, String apellido1, String apellido2) throws SQLException {
        
        List<Libro> libros = new ArrayList<>();
        String sql = BASE_QUERY + "WHERE autores.nombre = ? AND autores.apellido1 = ? AND autores.apellido2 = ? ORDER BY autores.apellido1";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, nombre);
        stmt.setString(2, apellido1);
        stmt.setString(3, apellido2);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            libros.add(crearLibro(rs));
        }
        return libros;
    }
    /**
     * Metodo que devuelve libros filtrados por su genero
     */
    public List<Libro> filtroGenero(String genero) throws SQLException {

        List<Libro> libros = new ArrayList<>();
        String sql = BASE_QUERY + "WHERE generos.genero = ? ORDER BY generos.genero";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, genero);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            libros.add(crearLibro(rs));
        }
        return libros;
    }
    /**
     * Metodo privado que crea un objeto Libro
     */
    private Libro crearLibro(ResultSet rs) throws SQLException{
        return new Libro(rs.getInt("libros.isbn"),
                rs.getString("libros.nombre"),
                rs.getInt("libros.año"), 
                rs.getString("autores.nombre") + " " + rs.getString("autores.apellido1") + " " + rs.getString("autores.apellido2"),
                rs.getString("generos.genero")
            );
    }
}
