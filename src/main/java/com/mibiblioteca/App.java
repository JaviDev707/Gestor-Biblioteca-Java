package com.mibiblioteca;

import java.util.Scanner;
import com.mibiblioteca.ConexionDB.ConexionDB;
import com.mibiblioteca.Servicio.Consola;

public class App {
    public static void main(String[] args) throws Exception {

        Consola consola = new Consola();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        System.out.println("Bienvenido a la App Biblioteca. \nQue desea hacer?");
        separador();

        while (!salir) {
            
            System.out.println("1 - Gestion de libros");
            System.out.println("2 - Gestion de usuarios");
            System.out.println("3 - Gestion de prestamos");
            System.out.println("4 - Consultar historial de prestamos");
            System.out.println("0 - Salir");
            separador();
            System.out.print("Selecione su opción: ");
            
            try {

                int opcion = Integer.parseInt(sc.nextLine());
                separador();

                switch (opcion) {

                case 1:
                    
                    consola.gestionLibros();
                    separador();
                    break;

                case 2:
                    
                    consola.gestionUsuarios();
                    separador();
                    break;

                case 3:
                    
                    consola.gestionPrestamos();
                    separador();
                    break;

                case 4:
                    
                    consola.historial_prestamos();
                    separador();
                    break;

                case 0:
                    
                    salir = true;
                    break;
            
                default:
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada incorrecta. Introduzca un numero");
                separador();
            } catch (Exception e){

            }

        }
        System.out.println("Saliendo de la App...");
        sc.close();
        ConexionDB.cerrarConexion();
    }

    private static void separador(){
        System.out.println("=========================================");
    }
}
