🏭 DepoManager

Sistema integral de gestión de depósitos, desarrollado en Java, orientado a optimizar el control de stock, el registro de productos y la administración de operaciones internas.

📋 Índice

🎯 Objetivo del proyecto

🛠️ Tecnologías utilizadas

🏗️ Arquitectura general

🧩 Funcionalidades

📦 Ejecución del proyecto

🎓 Información académica

👥 Autores

🎯 Objetivo del proyecto

DepoManager busca digitalizar y simplificar la administración de un depósito mediante una aplicación desarrollada en Java puro, que permite:

Gestión de productos (altas, bajas, modificaciones).

Control de stock y movimientos de inventario.

Registro de operaciones básicas.

Interfaz de uso académico orientada al aprendizaje de buenas prácticas.

🛠️ Tecnologías utilizadas
Tecnología	Uso principal
Java 17 / 20	Lenguaje de programación principal
POO (Programación Orientada a Objetos)	Organización y diseño del sistema
Swing / Consola	Interfaz y visualización de datos (según implementación)
MySQL (opcional)	Almacenamiento persistente
Git & GitHub	Control de versiones y repositorio del equipo
IntelliJ IDEA / Eclipse	IDEs usados en el desarrollo
🏗️ Arquitectura general
com.depotmanager
├── model          → Entidades principales (Producto, Usuario, Stock, etc.)
├── service        → Lógica de negocio y operaciones
├── controller     → Manejo de la interacción con el usuario
├── util           → Clases de apoyo y utilidades
└── main           → Punto de entrada de la aplicación

🧩 Funcionalidades

🔹 Alta, baja y modificación de productos
🔹 Registro y control de stock disponible
🔹 Listado de productos y búsqueda por criterios
🔹 Registro de movimientos de inventario
🔹 Validaciones básicas de datos de entrada
🔹 Persistencia en base de datos (si se configura MySQL)

📦 Ejecución del proyecto

Clonar el repositorio:

git clone https://github.com/EmiBarrientos/TPFinalLabo2.git
cd TPFinalLabo2


Compilar el código desde tu IDE o línea de comandos.

javac -d bin src/**/*.java


Ejecutar la clase principal (reemplazar por la tuya):

java -cp bin com.depotmanager.Main

🎓 Información académica

Este proyecto fue desarrollado como Trabajo Práctico Final de la materia Laboratorio de Programación II, cumpliendo con los siguientes requisitos:

Aplicación funcional en Java.

Uso de Programación Orientada a Objetos.

Persistencia opcional en base de datos.

Trabajo en equipo con control de versiones (Git & GitHub).

👥 Autores

Emiliano Barrientos

Agustín Malagutti

Luciano Fraticelli

Tobías Miri

Julieta Gerez

📍 Universidad Tecnológica Nacional (UTN)
📚 Materia: Laboratorio de Programación II
👨‍🏫 Profesor: Sergio Garguir
📆 Fecha: 16/06/2024
