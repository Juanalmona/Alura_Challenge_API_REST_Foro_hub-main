ForoHub API REST - Challenge Back-End G7 Alura
Este proyecto es parte de la Especialización en Back-End ofrecida por Alura - Oracle Next Education. El objetivo de este challenge es construir una API REST para un foro donde los participantes puedan gestionar preguntas relacionadas con diferentes tópicos.

🌟 Objetivo del Proyecto
El objetivo es desarrollar un sistema backend que permita realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre los tópicos. La API será implementada siguiendo las mejores prácticas del modelo REST e incluirá validaciones, autenticación y autorización para garantizar la seguridad y la funcionalidad del sistema.

🛠 Características Principales de la API
Gestión de Tópicos
Crear un nuevo tópico.
Consultar la lista de tópicos.
Obtener detalles de un tópico específico.
Actualizar un tópico existente.
Eliminar un tópico.
Seguridad
Implementación de autenticación basada en JWT (JSON Web Token).
Restricción de acceso según roles y permisos.
Base de Datos Relacional
Persistencia de datos utilizando MySQL.
Gestión de migraciones con Flyway.
Documentación Interactiva
Uso de Swagger para describir y probar la API.
🔧 Herramientas Necesarias
Antes de empezar, asegúrate de tener instaladas las siguientes herramientas:

Trello (Organización de tareas)
Git y GitHub (Control de versiones y repositorios)
IntelliJ IDEA Community Edition (IDE)
Java JDK 17+ (Entorno de ejecución)
Spring Initializr (Generador de proyectos Spring)
MySQL (Sistema de gestión de bases de datos)
MySQL Workbench (Herramienta de diseño de bases de datos)
Insomnia o Postman (Pruebas de API REST)
📦 Dependencias Necesarias
Las siguientes dependencias se deben seleccionar al generar el proyecto en Spring Initializr:

Spring Data JPA: Para interactuar con la base de datos relacional.
Lombok: Para reducir el código repetitivo (getters, setters, constructores).
Spring Web: Para construir servicios RESTful.
Spring Boot DevTools: Para acelerar el desarrollo con reinicio automático.
Flyway Migration: Para gestionar las migraciones de la base de datos.
MySQL Driver: Para conectar con la base de datos MySQL.
Validation: Para validar datos en las solicitudes.
Spring Security: Para implementar autenticación y autorización.
Springdoc OpenAPI (Swagger): Para generar y probar la documentación de la API.
📝 Pasos Principales del Challenge
1. Configuración Inicial del Entorno
Instala las herramientas necesarias.
Crea el proyecto inicial desde Spring Initializr seleccionando las dependencias necesarias.
Configura el archivo application.properties o application.yml con las credenciales de la base de datos MySQL.
2. Creación de la Base de Datos
Diseña y crea las tablas necesarias utilizando MySQL Workbench.
Implementa las migraciones con Flyway para gestionar los cambios en la base de datos.
3. Desarrollo de las Operaciones CRUD
POST: Crear un nuevo tópico.
GET: Consultar todos los tópicos o uno específico.
PUT: Actualizar un tópico existente.
DELETE: Eliminar un tópico.
4. Pruebas de la API
Utiliza Insomnia o Postman para probar las rutas de la API.
Realiza validaciones de los datos y verifica las respuestas esperadas.
5. Seguridad
Integra Spring Security para restringir el acceso a usuarios autenticados.
Implementa JWT para la autenticación y manejo de sesiones.
6. Documentación
Configura Swagger para documentar la API y permitir pruebas interactuar desde su interfaz.
