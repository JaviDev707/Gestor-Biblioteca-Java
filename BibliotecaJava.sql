CREATE TABLE `autores` (
    `id_autor` INT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(25) NOT NULL,
    `apellido1` VARCHAR(25) NOT NULL,
    `apellido2` VARCHAR(25) NOT NULL,
    `fecha_nacimiento` DATE
) ENGINE=InnoDB;

CREATE TABLE `generos` (
    `id_genero` INT AUTO_INCREMENT PRIMARY KEY,
    `genero` VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `libros` (
    `isbn` BIGINT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL,
    `fecha_salida` DATE NOT NULL,
    `stock` INT,
    `id_autor` INT NOT NULL,
    `id_genero` INT NOT NULL,
    FOREIGN KEY (`id_autor`) REFERENCES `Autores`(`id_autor`),
    FOREIGN KEY (`id_genero`) REFERENCES `Generos`(`id_genero`)
) ENGINE=InnoDB;

CREATE TABLE `usuarios` (
    `id_usuario` INT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(25) NOT NULL,
    `apellido` VARCHAR(25) NOT NULL,
    `email` VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `prestamos` (
    `id_prestamo` INT AUTO_INCREMENT PRIMARY KEY,
    `fecha_prestamo` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `fecha_devolucion` DATETIME,
    `id_usuario` INT NOT NULL,
    `id_libro` BIGINT NOT NULL,
    FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios`(`id_usuario`),
    FOREIGN KEY (`id_libro`) REFERENCES `Libros`(`isbn`)
) ENGINE=InnoDB;
