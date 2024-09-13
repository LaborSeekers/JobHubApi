## Introducción

**JobHub** es una innovadora plataforma en línea diseñada para transformar la búsqueda de empleo y simplificar el proceso de contratación. Permite a los usuarios crear perfiles, iniciar sesión y descubrir ofertas laborales alineadas con su experiencia y habilidades. Con una interfaz intuitiva y moderna, los postulantes pueden fácilmente encontrar y postularse a trabajos que se ajusten a sus intereses, mientras que las empresas pueden gestionar eficientemente sus procesos de selección de candidatos.

El propósito de **JobHub** es ofrecer una solución completa que optimice la manera en que se buscan y gestionan las oportunidades laborales. La plataforma busca mejorar la experiencia de búsqueda de empleo y contratación mediante una experiencia de usuario fluida y segura, facilitando el acceso a empleos y contribuyendo a una reducción efectiva del desempleo.

### Colaboradores del Proyecto

|Nombre|Rol|
|------|---|
| Anthony Hans Tarrillo Ayllón | Desarrollador |
| Fernando Samuel Paredes Espinoza | Desarrollador |
| Guido Yair Abel Jeri Saldaña | Desarrollador |
| José Giovanni Laura Silvera | Desarrollador |
| Karim Wagner Samanamud Mosquera | Líder del Proyecto |

## Revisa el Progreso del Proyecto JobHub
| Columna | Descripción |
|---------|-------------|
| Backlog | Incluye todas las historias de usuario pendientes a ser desarrolladas |
| To Do | Tareas priorizadas listas para comenzar el trabajo |
| In Progress | Tareas en desarrollo activo | 
| Done | Tareas completadas y aceptadas |

## Funcionalidades de la Aplicación JobHub
### Módulo de Gestión de Usuarios
- Inicio de sesión
- Crear cuenta de usuario
- Visualizar perfil de usuario
- Editar perfil de usuario
- Recuperar contraseña
### Módulo de Postulaciòn
- Aplicar a una oferta laboral
- Visualizar detalle de la oferta laboral
- Consultar/Filtrar ofertas laborales por categoría
### Módulo de Publicación de Ofertas Laborales
- Crear una oferta laboral
- Listar/Filtrar ofertas laborales
### Módulo de Reporte
- Reporte de oferta laboral con mayor número de aplicaciones.
### Módulo de Pagos
- Realizar un pago de subscripción
- Visualizar historial de pagos
### Módulo de Gestión de Contenido
- Gestión de categoría de oferta laboral
- Gestión modalidad de trabajo
- Gestión de empresas
- Gestión de país

## Diagramas de la Aplicación
Para una comprensión más clara de la estructura y el diseño de la aplicación "BookHub", revisa los siguientes diagramas:
### Diagrama de Clases
![Diagrama de clases](Diagrama_de_clases.png)
Este diagrama muestra las clases principales de la aplicación, sus atributos, métodos y las relaciones entre ellas.
### Diagrama de Base de Datos
![Diagrama de base de datos](Diagrama_de_base_de_datos.png)
Ilustra el esquema de la base de datos de la aplicación, incluyendo las tablas, columnas y relaciones entre las entidades.
## Descripción de Capas del Proyecto
|  Capa |  Descripción |
|---|---|
| api | Contiene los controladores REST que manejan las solicitudes HTTP y conecta la logica de negocio |
| entity | Define entidades del dominio, representando las tablas en la base de datos |
| repository | Interfaces que permiten acceder y manipular la base de datos mediante operaciones CRUD |
| service | Interfaces que definen la lógica de negocio y operaciones disponibles para la aplicación |
| service impl | Implementa la logica de los servicios |

### Recomendaciones
- Se sugiere actualizar y añadir historias de usuario a lo largo del proceso de desarrollo. Esta práctica permitirá identificar  nuevos posibles problemas y obtener retroalimentación continua de las historias, lo cual es esencial para asegurar la correcta creación de la aplicación web.

- Se recomienda seguir el orden de prioridad establecido en el product backlog al desarrollar funcionalidades. Además, es importante distribuir de manera equitativa las funcionalidades de alto nivel y el tiempo requerido para su implementación. Esto contribuirá a fomentar una participación más temprana y efectiva en la aplicación.
