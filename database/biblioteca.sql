-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-10-2025 a las 02:29:42
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `biblioteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE `libros` (
  `id_libros` int(11) NOT NULL,
  `autor` varchar(100) NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `paginas` int(11) DEFAULT NULL,
  `anio` int(11) DEFAULT NULL,
  `disponibilidad` tinyint(1) DEFAULT 1,
  `estante` varchar(10) DEFAULT NULL,
  `cantidad` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`id_libros`, `autor`, `titulo`, `categoria`, `paginas`, `anio`, `disponibilidad`, `estante`, `cantidad`) VALUES
(1, 'Gabriel García Márquez', 'Cien años de soledad', 'Realismo mágico', 417, 1967, 1, 'A-01', 5),
(2, 'Julio Cortázar', 'Rayuela', 'Novela', 635, 1963, 1, 'A-02', 3),
(3, 'Isabel Allende', 'La casa de los espíritus', 'Realismo mágico', 433, 1982, 1, 'A-01', 4),
(4, 'Jorge Luis Borges', 'Ficciones', 'Cuento', 203, 1944, 1, 'B-03', 7),
(5, 'Stephen King', 'It (Eso)', 'Terror', 1138, 1986, 1, 'C-05', 2),
(6, 'Haruki Murakami', 'Tokio Blues (Norwegian Wood)', 'Novela', 389, 1987, 1, 'B-01', 3),
(7, 'Frank Herbert', 'Dune', 'Ciencia Ficción', 412, 1965, 1, 'C-01', 5),
(8, 'J.R.R. Tolkien', 'El Señor de los Anillos: La Comunidad del Anillo', 'Fantasía', 423, 1954, 1, 'F-02', 3),
(9, 'George Orwell', '1984', 'Distopía', 328, 1949, 1, 'D-01', 6),
(10, 'Jane Austen', 'Orgullo y Prejuicio', 'Clásico', 279, 1813, 1, 'B-04', 4),
(11, 'Mary Shelley', 'Frankenstein', 'Gótico', 280, 1818, 1, 'C-05', 3),
(12, 'Adolfo Bioy Casares', 'La invención de Morel', 'Ciencia Ficción', 112, 1940, 1, 'B-03', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo`
--

CREATE TABLE `prestamo` (
  `id_prestamo` int(11) NOT NULL,
  `id_socios` int(11) NOT NULL,
  `id_libros` int(11) DEFAULT NULL,
  `id_vinilos` int(11) DEFAULT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `estado` enum('activo','devuelto') DEFAULT 'activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `prestamo`
--

INSERT INTO `prestamo` (`id_prestamo`, `id_socios`, `id_libros`, `id_vinilos`, `fecha_inicio`, `fecha_fin`, `estado`) VALUES
(1, 1, 7, NULL, '2025-10-30', '2025-11-06', 'devuelto');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socios`
--

CREATE TABLE `socios` (
  `id_socios` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `socios`
--

INSERT INTO `socios` (`id_socios`, `nombre`, `dni`, `email`, `numero`) VALUES
(1, 'Ana Pérez', '40123456', 'ana.perez@email.com', '+549111234567'),
(2, 'Juan González', '38987654', 'juan.gonzalez@email.com', '+549351765432'),
(3, 'María Rodríguez', '42345678', 'maria.r@email.com', '+549261876543'),
(4, 'Carlos López', '35111222', 'carlos.lopez@email.com', NULL),
(5, 'Laura Fernández', '41555666', 'laura.f@email.com', '+549119876543'),
(6, 'Martín Sánchez', '39888777', 'martin.sanchez@email.com', '+549221456789'),
(7, 'Lucía Giménez', '43111222', 'lucia.gimenez@email.com', NULL),
(8, 'Diego Torres', '37555444', 'diego.torres@email.com', '+54911333222');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vinilos`
--

CREATE TABLE `vinilos` (
  `id_vinilos` int(11) NOT NULL,
  `autor` varchar(100) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `canciones` varchar(255) DEFAULT NULL,
  `anio` int(11) DEFAULT NULL,
  `disponibilidad` tinyint(1) DEFAULT 1,
  `cantidad` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vinilos`
--

INSERT INTO `vinilos` (`id_vinilos`, `autor`, `nombre`, `canciones`, `anio`, `disponibilidad`, `cantidad`) VALUES
(1, 'Pink Floyd', 'The Dark Side of the Moon', 'Speak to Me, Breathe, On the Run, Time, The Great Gig in the Sky', 1973, 1, 3),
(2, 'The Beatles', 'Abbey Road', 'Come Together, Something, Maxwell\'s Silver Hammer, Oh! Darling, Octopus\'s Garden', 1969, 1, 5),
(3, 'Daft Punk', 'Random Access Memories', 'Give Life Back to Music, The Game of Love, Get Lucky, Lose Yourself to Dance', 2013, 1, 2),
(4, 'Gustavo Cerati', 'Bocanada', 'Tabú, Engaña, Bocanada, Puente, Río Babel, Raíz', 1999, 1, 4),
(5, 'Fleetwood Mac', 'Rumours', 'Second Hand News, Dreams, Never Going Back Again, Go Your Own Way, Don\'t Stop', 1977, 1, 3),
(6, 'Michael Jackson', 'Thriller', 'Wanna Be Startin\' Somethin\', Baby Be Mine, The Girl Is Mine, Thriller, Beat It', 1982, 1, 6),
(7, 'Charly García', 'Clics Modernos', 'Nos siguen pegando abajo (Pecado mortal), No soy un extraño, Dos cero uno (Transas), Nuevos trapos', 1983, 1, 4),
(8, 'Queen', 'A Night at the Opera', 'Death on Two Legs, Lazing on a Sunday Afternoon, You\'re My Best Friend, Bohemian Rhapsody', 1975, 1, 3),
(9, 'Nirvana', 'Nevermind', 'Smells Like Teen Spirit, In Bloom, Come as You Are, Breed, Lithium', 1991, 1, 5),
(10, 'Pescado Rabioso', 'Artaud', 'Todas las hojas son del viento, Cantata de puentes amarillos, Bajan, A Starosta, el idiota', 1973, 1, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`id_libros`);

--
-- Indices de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`id_prestamo`),
  ADD KEY `id_socios` (`id_socios`),
  ADD KEY `id_libros` (`id_libros`),
  ADD KEY `id_vinilos` (`id_vinilos`);

--
-- Indices de la tabla `socios`
--
ALTER TABLE `socios`
  ADD PRIMARY KEY (`id_socios`);

--
-- Indices de la tabla `vinilos`
--
ALTER TABLE `vinilos`
  ADD PRIMARY KEY (`id_vinilos`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `id_libros` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `id_prestamo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `socios`
--
ALTER TABLE `socios`
  MODIFY `id_socios` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `vinilos`
--
ALTER TABLE `vinilos`
  MODIFY `id_vinilos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `prestamo_ibfk_1` FOREIGN KEY (`id_socios`) REFERENCES `socios` (`id_socios`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `prestamo_ibfk_2` FOREIGN KEY (`id_libros`) REFERENCES `libros` (`id_libros`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `prestamo_ibfk_3` FOREIGN KEY (`id_vinilos`) REFERENCES `vinilos` (`id_vinilos`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
