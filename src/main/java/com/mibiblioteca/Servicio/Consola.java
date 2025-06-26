package com.mibiblioteca.Servicio;

import java.sql.SQLException;
import java.util.Scanner;

public class Consola {
    
    private Biblioteca biblioteca;
    private Scanner scanner;

    public Consola() throws SQLException{

        biblioteca = new Biblioteca();
        scanner = new Scanner(System.in);
    }

    public void gestionLibros() throws SQLException{

        System.out.println("1 - Mostrar todos los libros");
        System.out.println("2 - Filtrar por ISBN");
        System.out.println("3 - Filtrar por Autor");
        System.out.println("4 - Filtrar por género");
        System.out.println("=======================================");
        System.out.print("Seleccione su opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        System.out.println("=======================================");

        switch (opcion) {
            case 1:

                biblioteca.mostrarTablaLibros();
                break;
        
            case 2:

                System.out.print("ISBN: ");
                int isbn = Integer.parseInt(scanner.nextLine());
                biblioteca.filtrarIsbn(isbn);
                break;

            case 3:

                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Primer apellido: ");
                String apellido1 = scanner.nextLine();
                System.out.print("Segundo apellido: ");
                String apellido2 = scanner.nextLine();
                biblioteca.filtrarAutor(nombre, apellido1, apellido2);
                break;

            case 4:

                System.out.print("Género: ");
                String genero = scanner.nextLine();
                biblioteca.filtrarGenero(genero);
                break;

            default:
                break;
        }
    }

    public void gestionUsuarios() throws SQLException{

        System.out.println("1 - Mostrar todos los usuarios");
        System.out.println("2 - Buscar usuario por su email");
        System.out.println("3 - Insertar un nuevo usuario");
        System.out.println("4 - Borrar usuario");
        System.out.println("=======================================");
        System.out.print("Seleccione su opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        System.out.println("=======================================");

        switch (opcion) {
            case 1:
                
                biblioteca.mostrarTablaUsuarios();
                break;
        
            case 2:

                System.out.print("Email: ");
                String email = scanner.nextLine();
                biblioteca.buscarUsuario(email);
                break;

            case 3:

                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Primer apellido: ");
                String apellido1 = scanner.nextLine();
                System.out.print("Segundo apellido: ");
                String new_email = scanner.nextLine();
                biblioteca.registrarUsuario(nombre, apellido1, new_email);
                break;

            case 4: 

                System.out.print("ID del usuario: ");
                int id_usuario = Integer.parseInt(scanner.nextLine());
                biblioteca.eliminarUsuario(id_usuario);
                break;

            default:
                break;
        }
    }

    public void gestionPrestamos() throws SQLException{

        System.out.println("1 - Mostrar todos los prestamos activos");
        System.out.println("2 - Mostrar los prestamos a un usuario");
        System.out.println("3 - Registrar un nuevo prestamo");
        System.out.println("4 - Devolución");
        System.out.println("=======================================");
        System.out.print("Seleccione su opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        System.out.println("=======================================");

        switch (opcion) {
            case 1:
                
                biblioteca.mostrarTablaPrestamos();
                break;
        
            case 2:

                System.out.print("ID del usuario: ");
                int id_usuario = Integer.parseInt(scanner.nextLine());
                biblioteca.mostrarPrestamos(id_usuario);
                break;

            case 3:

                System.out.print("ID del usuario: ");
                int id_usuario_p = Integer.parseInt(scanner.nextLine());
                System.out.print("ISBN: ");
                int isnb_p = Integer.parseInt(scanner.nextLine());
                biblioteca.registrarPrestamo(id_usuario_p, isnb_p);
                break;

            case 4:

                System.out.print("ID del usuario: ");
                int id_usuario_d = Integer.parseInt(scanner.nextLine());
                System.out.print("ISBN: ");
                int isnb_d = Integer.parseInt(scanner.nextLine());
                biblioteca.devolucion(id_usuario_d, isnb_d);
                break;
                
            default:
                break;
        }
    }

    public void historial_prestamos() throws SQLException{

        biblioteca.mostrarTablaHistorialPrestamos();
    }
}
