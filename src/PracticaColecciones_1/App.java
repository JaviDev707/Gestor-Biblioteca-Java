package PracticaColecciones_1;
/**
 * Crear un sistema para gestionar los libros de una biblioteca. 
 * El sistema permitirá registrar libros, usuarios, préstamos y devoluciones. 
 */
public class App {
    public static void main(String[] args) throws Exception {

        Libro libro1 = new Libro("1111", "AAA", "Mandarino", 2014, "Comedia");
        Libro libro2 = new Libro("2222", "BBB", "Manuel Vargas",2004, "Drama");
        Libro libro3 = new Libro("3333", "CCC", "Mela Pela",2018, "Policiaca");
        Libro libro4 = new Libro("4444", "DDD", "Paco Paco",2000, "Terror");
        Libro libro5 = new Libro("5555", "EEE", "Sin Sentido",2009, "Fantasia");

        Usuario usuario1 = new Usuario(1, "Pepe", "Pepe@gmail.com");
        Usuario usuario2 = new Usuario(2, "Tony", "Tony@gmail.com");
        Usuario usuario3 = new Usuario(3, "Juanito", "Juanito@gmail.com");

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.registrarLibro(libro1);
        biblioteca.registrarLibro(libro2);
        biblioteca.registrarLibro(libro3);
        biblioteca.registrarLibro(libro4);
        biblioteca.registrarLibro(libro5);
        biblioteca.registrarUsuario(usuario1);
        biblioteca.registrarUsuario(usuario2);
        biblioteca.registrarUsuario(usuario3);

    }
}
