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


-- Insertar un agente voluntario
INSERT INTO AgenteVoluntario (nombre, email, password) 
VALUES ('Camila', 'camila@compatitas.com', 'hash1234');

-- Insertar mascotas asociadas al agente (id_agente = 1)
INSERT INTO Mascota (nombre, estado, descripcion, id_agente) 
VALUES ('Luna', 'Adoptable', 'Mestiza, tamaño mediano, muy juguetona', 1),
       ('Milo', 'En recuperación', 'Gato rubio, tranquilo, requiere dieta especial', 1);

-- Insertar una solicitud de adopción para la mascota Luna (id_mascota = 1)
INSERT INTO SolicitudAdopcion (nombre_interesado, telefono, email, mensaje, id_mascota) 
VALUES ('Juan Pérez', '3515551234', 'juan@email.com', 'Me encantaría adoptar a Luna.', 1);



-- Consultar las solicitudes cruzando datos con la mascota y el agente a cargo
SELECT 
    s.nombre_interesado AS 'Interesado',
    s.email AS 'Contacto',
    m.nombre AS 'Mascota Solicitada',
    m.estado AS 'Estado Actual',
    a.nombre AS 'Agente Responsable'
FROM 
    solicitudes_adopcion s
JOIN 
    Mascota m ON s.id_mascota = m.id_mascota
JOIN 
    AgenteVoluntario a ON m.id_agente = a.id_agente;


-- Se borran en orden inverso a la creación para no romper la integridad referencial
DELETE FROM solicitudes_adopcion; 
DELETE FROM mascotas; 
DELETE FROM agentes_voluntarios;

