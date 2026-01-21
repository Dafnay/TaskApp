# TaskApp

Aplicación web de gestión de tareas desarrollada con Spring Boot.

## Tecnologías

- **Backend:** Spring Boot 4.0.1, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, Bootstrap 5
- **Base de datos:** H2 (desarrollo), MySQL (producción)
- **Otros:** Lombok, Thymeleaf Layout Dialect

## Funcionalidades

### Usuarios
- Registro e inicio de sesión
- Roles: USER y ADMIN

### Tareas
- Crear, editar y eliminar tareas
- Marcar como completadas/pendientes
- Filtrar por estado (todas, completadas, pendientes)
- Asignar categorías y etiquetas

### Panel de Administración
- Gestión de usuarios (promover a admin)
- Gestión de categorías
- Ver y eliminar todas las tareas

## Requisitos

- Java 17+
- Maven 3.8+

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/Dafnay/TaskApp.git
cd TaskApp
```

2. Ejecutar la aplicación:
```bash
./mvnw spring-boot:run
```

3. Acceder a `http://localhost:8080`

## Usuarios de prueba

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| admin   | 123456     | ADMIN |
| user    | 123456     | USER |

## Estructura del proyecto

```
src/main/java/com/repos_alba/todo/
├── category/       # Gestión de categorías
├── task/           # Gestión de tareas
├── tag/            # Gestión de etiquetas
├── user/           # Gestión de usuarios
└── shared/         # Configuración, seguridad, etc.
```

## Configuración

### Desarrollo (H2)
El perfil por defecto usa H2 en memoria. No requiere configuración adicional.

### Producción (MySQL)
Crear `application-prod.properties` con:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskapp
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

Ejecutar con:
```bash
./mvnw spring-boot:run -Dspring.profiles.active=prod
```

## Autor

Alba Velasco González
