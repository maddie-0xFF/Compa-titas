-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS compatitas_db;
USE compatitas_db;

-- Tabla para el Actor: Agente Voluntario
CREATE TABLE AgenteVoluntario (
    id_agente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para las tarjetas de Mascotas
CREATE TABLE Mascota (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estado ENUM('En recuperación', 'Adoptable') DEFAULT 'En recuperación',
    descripcion TEXT,
    id_agente INT,
    FOREIGN KEY (id_agente) REFERENCES AgenteVoluntario(id_agente)
);

-- Tabla para almacenar los formularios de los Visitantes (Solicitudes)
CREATE TABLE SolicitudAdopcion (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    nombre_interesado VARCHAR(100) NOT NULL,
    telefono VARCHAR(50),
    email VARCHAR(150),
    mensaje TEXT,
    id_mascota INT,
    FOREIGN KEY (id_mascota) REFERENCES Mascota(id_mascota)
);