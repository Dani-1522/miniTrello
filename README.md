#  miniTrello - Spring Boot

Aplicación web estilo Trello/Kanban colaborativo construida con **Java + Spring Boot**. Permite a los usuarios gestionar tableros, listas y tarjetas, trabajar en equipo, comentar, etiquetar, asignar colaboradores y recibir notificaciones en tiempo real.

##  Tecnologías usadas

- Java 17+
- Spring Boot
- Spring Security + JWT
- Spring Data JPA + Hibernate
- WebSocket (STOMP)
- MySQL
- Maven
- Lombok
  
- Postman (para pruebas)

---

##  Funcionalidades principales

###  Autenticación y Seguridad
- Registro y login de usuarios.
- Seguridad con JWT.
- Filtros personalizados y control de acceso basado en roles.

###  Gestión de Tableros
- Crear, editar, eliminar tableros.
- Compartir tableros con otros usuarios con roles: **owner**, **editor**, **viewer**.

###  Listas y Tarjetas
- Crear y reordenar listas dentro de tableros.
- CRUD completo de tarjetas.
- Reordenamiento persistente de tarjetas entre listas.
- Asignación de colaboradores a tarjetas.
- Campos como descripción, fecha de vencimiento y posición.

###  Etiquetas (Tags)
- Crear y asignar etiquetas de colores por tablero.
- Filtrado de tarjetas por etiquetas.

###  Fecha de vencimiento
- Tarjetas con `dueDate`.
- Vista de próximas tareas a vencer.

###  Comentarios
- Agregar comentarios a tarjetas.
- Cada comentario incluye autor y fecha.

###  Checklists
- Crear subtareas dentro de tarjetas.
- Marcar ítems como completados.

###  Notificaciones internas
- Recibir alertas por:
  - Asignación de tarjeta.
  - Comentario nuevo.
  - Vencimiento cercano.
- Vistas de notificaciones y marcar como leídas.
- Actualización en tiempo real vía WebSocket.

###  Filtros y Búsqueda
- Buscar tarjetas por:
  - Título
  - Etiquetas
  - Usuario asignado
  - Fecha de vencimiento

---

##  Instalación y ejecución local

### Prerrequisitos

- Java 17+
- Maven
- MySQL o Docker

### Clonar el proyecto

```bash
git clone https://github.com/Dani-1522/miniTrello

