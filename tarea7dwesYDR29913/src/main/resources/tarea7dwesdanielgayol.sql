-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-03-2025 a las 14:14:54
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
-- Base de datos: `tarea7dwesdanielgayol`
--
CREATE DATABASE IF NOT EXISTS `tarea7dwesdanielgayol` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tarea7dwesdanielgayol`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

DROP TABLE IF EXISTS `credenciales`;
CREATE TABLE `credenciales` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `perfil` varchar(255) DEFAULT NULL,
  `persona_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`id`, `password`, `usuario`, `perfil`, `persona_id`) VALUES
(1, 'admin', 'admin', 'Admin', 1),
(19, 'dani', 'dani', 'Personal', 16),
(23, 'prueba', 'prueba', 'Personal', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejemplares`
--

DROP TABLE IF EXISTS `ejemplares`;
CREATE TABLE `ejemplares` (
  `id` bigint(20) NOT NULL,
  `id_planta` bigint(20) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ejemplares`
--

INSERT INTO `ejemplares` (`id`, `id_planta`, `nombre`) VALUES
(34, 18, 'rosa-1'),
(35, 18, 'rosa-2'),
(36, 23, 'violeta-1'),
(37, 23, 'violeta-2'),
(38, 18, 'rosa-3'),
(39, 23, 'violeta-3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

DROP TABLE IF EXISTS `mensajes`;
CREATE TABLE `mensajes` (
  `fecha_hora` date DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `id_ejemplar` bigint(20) DEFAULT NULL,
  `id_persona` bigint(20) DEFAULT NULL,
  `mensaje` varchar(255) DEFAULT NULL,
  `id_planta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mensajes`
--

INSERT INTO `mensajes` (`fecha_hora`, `id`, `id_ejemplar`, `id_persona`, `mensaje`, `id_planta`) VALUES
('2025-03-25', 45, 34, 1, 'Añadido ejemplar rosa-1', 18),
('2025-03-25', 46, 35, 1, 'Añadido ejemplar rosa-2', 18),
('2025-03-25', 47, 36, 1, 'Añadido ejemplar violeta-1', 23),
('2025-03-25', 48, 37, 1, 'Añadido ejemplar violeta-2', 23),
('2025-03-25', 49, 34, 1, 'Añadido Mensaje de Admin', 18),
('2025-03-25', 50, 38, 16, 'Añadido ejemplar rosa-3', 18),
('2025-03-25', 51, 39, 16, 'Añadido ejemplar violeta-3', 23),
('2025-03-25', 52, 38, 16, 'Añadido Mensaje de Dani', 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

DROP TABLE IF EXISTS `personas`;
CREATE TABLE `personas` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `email`, `nombre`) VALUES
(1, 'admin@gmail.com', 'Admin'),
(16, 'dani@gmail.com', 'Dani'),
(20, 'prueba@gmail.com', 'Prueba');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plantas`
--

DROP TABLE IF EXISTS `plantas`;
CREATE TABLE `plantas` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre_cientifico` varchar(255) DEFAULT NULL,
  `nombre_comun` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `plantas`
--

INSERT INTO `plantas` (`id`, `codigo`, `nombre_cientifico`, `nombre_comun`) VALUES
(18, 'rosa', 'Rosa-Cientifico', 'Rosa-Comun'),
(23, 'violeta', 'Violeta_Cientifico', 'Violeta-Comun');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK3kd2kgw099okafq78kw0j5v8e` (`persona_id`);

--
-- Indices de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK44fnp4rtcs2j6ppkmkoqbtton` (`id_planta`);

--
-- Indices de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKidbx1mhngh3c3ry5bqisftxbv` (`id_ejemplar`),
  ADD KEY `FK2e6au5w562m7skcvx9jckiba6` (`id_persona`),
  ADD KEY `FKrs58ep8tnh47xtksqpjuh9m3b` (`id_planta`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `plantas`
--
ALTER TABLE `plantas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbqo6lbeads0ifdh6dohhfhryp` (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `plantas`
--
ALTER TABLE `plantas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD CONSTRAINT `FKdrfftlast5p4qtiisw19u888w` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`);

--
-- Filtros para la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD CONSTRAINT `FK44fnp4rtcs2j6ppkmkoqbtton` FOREIGN KEY (`id_planta`) REFERENCES `plantas` (`id`);

--
-- Filtros para la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD CONSTRAINT `FK2e6au5w562m7skcvx9jckiba6` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id`),
  ADD CONSTRAINT `FKidbx1mhngh3c3ry5bqisftxbv` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id`),
  ADD CONSTRAINT `FKrs58ep8tnh47xtksqpjuh9m3b` FOREIGN KEY (`id_planta`) REFERENCES `plantas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
