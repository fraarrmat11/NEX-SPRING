-- Niveles
INSERT INTO nivel (id, experiencia_necesaria, nombre, recompensa_monedas) VALUES
(1, 0, 'pringao', 0),
(2, 500, 'panoli', 50),
(3, 1000, 'paquete', 100),
(4, 1500, 'despistado', 150),
(5, 2000, 'novato', 200),
(6, 3000, 'animal doméstico', 300),
(7, 4000, 'constante', 400),
(8, 5000, 'disciplinado', 500),
(9, 6500, 'cumplidor', 650),
(10, 8000, 'en forma', 800),
(11, 10000, 'maquina', 1000),
(12, 12500, 'pro', 1250),
(13, 15000, 'bestia', 1500),
(14, 18000, 'leyenda urbana', 1800),
(15, 22000, 'referente', 2200),
(16, 27000, 'titán de hábitos', 2700),
(17, 33000, 'monstruo de la constancia', 3300),
(18, 40000, 'máquina imparable', 4000),
(19, 48000, 'semidiós disciplinado', 4800),
(20, 60000, 'dios absoluto del autocontrol', 6000);

-- Skins
INSERT INTO skin_tienda (id, imagen_url, nombre, precio) VALUES
(1, 'skin_default', 'Por defecto', 0),
(2, 'skin_carton', 'Cartón', 100),
(3, 'skin_conejo', 'Conejo', 300),
(4, 'skin_friki', 'Friki', 500),
(5, 'skin_payaso', 'Payaso', 700),
(6, 'skin_esqueleto', 'Esqueleto', 1000),
(7, 'skin_boxeador', 'Boxeador', 1500),
(8, 'skin_pirata', 'Pirata', 1800),
(9, 'skin_vaquero', 'Vaquero', 2200),
(10, 'skin_fuerte', 'Fuerte', 2600),
(11, 'skin_ninja', 'Ninja', 2100),
(12, 'skin_espartano', 'Espartano', 3000),
(13, 'skin_princesa', 'Princesa', 2000),
(14, 'skin_rey', 'Rey', 2300),
(15, 'skin_papa_noel', 'Papá Noel', 2200),
(16, 'skin_dinosaurio', 'Dinosaurio', 5500),
(17, 'skin_robocop', 'Robocop', 3500),
(18, 'skin_omniman', 'Omni Man', 4000),
(19, 'skin_spiderman', 'Spiderman', 5000),
(20, 'skin_batman', 'Batman', 4500),
(21, 'skin_heisenberg', 'Walter White', 6000);

-- Logros
INSERT INTO logro (nombre, descripcion, requisito, experienciaxdesbloquear, tipo) VALUES
('Primer paso', 'Completa 1 hábito en un día', 1, 20, 'HABITO'),
('En racha', 'Completa 3 hábitos en un día', 3, 50, 'HABITO'),
('Día perfecto', 'Completa todos tus hábitos en un día', 5, 100, 'HABITO'),
('Despegando', 'Llega al nivel 3', 3, 80, 'NIVEL'),
('A mitad de camino', 'Llega al nivel 10', 10, 300, 'NIVEL'),
('Leyenda', 'Llega al nivel 20', 20, 1000, 'NIVEL'),
('Primera sesión', 'Completa una sesión de concentración', 1, 30, 'FOCUS'),
('Concentración total', 'Completa una sesión de 1 hora', 60, 100, 'FOCUS'),
('Primera página', 'Escribe tu primera entrada en el diario', 1, 20, 'DIARIO'),
('Escritor constante', 'Escribe 7 entradas en el diario', 7, 150, 'DIARIO');