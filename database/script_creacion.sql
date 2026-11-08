-- ==========================================================
-- SCRIPT DE CREACIÓN Y DATOS INICIALES - COMPA-TITAS (TP4)
-- ==========================================================

DROP DATABASE IF EXISTS compatitas_db;
CREATE DATABASE compatitas_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE compatitas_db;

-- 1. Tabla de Agentes Voluntarios
CREATE TABLE agentes_voluntarios (
    id_agente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL
);

-- 2. Tabla de Mascotas (Soporta herencia de Perro y Gato)
CREATE TABLE mascotas (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    especie VARCHAR(20) NOT NULL, -- 'Perro' o 'Gato'
    estado VARCHAR(50) NOT NULL,  -- 'En recuperación' o 'Adoptable'
    descripcion TEXT NOT NULL,
    tamanio VARCHAR(30) DEFAULT NULL,          -- Específico de Perro
    requiere_red BOOLEAN DEFAULT NULL,         -- Específico de Gato
    id_agente INT NOT NULL,
    CONSTRAINT fk_mascota_agente FOREIGN KEY (id_agente) 
        REFERENCES agentes_voluntarios(id_agente) ON DELETE CASCADE
);

-- 3. Tabla de Solicitudes de Adopción
CREATE TABLE solicitudes_adopcion (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    nombre_interesado VARCHAR(100) NOT NULL,
    telefono VARCHAR(40) NOT NULL,
    email VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_mascota INT NOT NULL,
    CONSTRAINT fk_solicitud_mascota FOREIGN KEY (id_mascota) 
        REFERENCES mascotas(id_mascota) ON DELETE CASCADE
);

-- ==========================================================
-- INSERCIÓN DE DATOS INICIALES (DML)
-- ==========================================================

INSERT INTO agentes_voluntarios (nombre, email, contrasena) VALUES 
('Ana Rescatista', 'ana@compatitas.org', 'hash1234'),
('Carlos Voluntario', 'carlos@compatitas.org', 'pass5678');

INSERT INTO mascotas (nombre, edad, especie, estado, descripcion, tamanio, requiere_red, id_agente) VALUES 
('Pipo', 2, 'Perro', 'En recuperación', 'Perrito mestizo encontrado con pata lastimada.', 'Mediano', NULL, 1),
('Luna', 1, 'Perro', 'Adoptable', 'Mestiza, muy juguetona y sociable.', 'Pequeño', NULL, 1),
('Milo', 5, 'Gato', 'En recuperación', 'Gato rubio, tranquilo, requiere dieta especial.', NULL, TRUE, 1),
('Michi', 3, 'Gato', 'Adoptable', 'Gata tricolor rescatada, muy cariñosa.', NULL, FALSE, 2);

INSERT INTO solicitudes_adopcion (nombre_interesado, telefono, email, mensaje, id_mascota) VALUES 
('Madelaine Dos Santos', '3544444444', 'madelaine.ds.01@gmail.com', 'Estoy interesada en adoptar a Luna!', 2);