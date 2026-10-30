# Spring Web Security: ejemplos para formación

Aplicación docente que reúne autenticación y gestión de usuarios con ejemplos de calendario, ficheros, paginación, correo, tareas programadas, streaming y WebSocket.

## Tecnologías y configuración

El [pom.xml](pom.xml) declara **Java 20 y Spring Boot 3.2.3**, con Spring Security, Thymeleaf, JPA y el conector MySQL.

La configuración presente en [application.properties](src/main/resources/application.properties) utiliza MySQL y el puerto 80. El proyecto no incluye una configuración H2 ni archivos de perfiles `local` y `dev`; esas opciones no deben darse por preparadas.

## Documentación por función

Dentro de [com.eoi.springwebsecurity](src/main/java/com/eoi/springwebsecurity/) encontrarás:

- [Aplicación principal y usuarios](src/main/java/com/eoi/springwebsecurity/coreapp/README.md).
- [Seguridad](src/main/java/com/eoi/springwebsecurity/security/README.md).
- [Calendario](src/main/java/com/eoi/springwebsecurity/calendario/controller/README.md).
- [Calendario HTML](src/main/java/com/eoi/springwebsecurity/calendarioHTML/README.md).
- [Correo](src/main/java/com/eoi/springwebsecurity/email/README.md).
- [Ficheros](src/main/java/com/eoi/springwebsecurity/filemanagement/README.md).
- [Filtrado](src/main/java/com/eoi/springwebsecurity/filtrado/README.md) y [paginación](src/main/java/com/eoi/springwebsecurity/paginacion/README.md).
- [Notificaciones](src/main/java/com/eoi/springwebsecurity/notificaciones/README.md) y [tareas programadas](src/main/java/com/eoi/springwebsecurity/scheduling/README.md).
- [Streaming](src/main/java/com/eoi/springwebsecurity/streaming/README.md) y [WebSocket](src/main/java/com/eoi/springwebsecurity/websockets/README.md).

## Preparación y ejecución

1. Selecciona JDK 20 e importa el proyecto como Maven.
2. Revisa la configuración de MySQL, correo y almacenamiento en `application.properties`.
3. Prepara una base de datos de pruebas y configura tus propios datos de conexión.
4. Ejecuta `SpringWebSecurityApplication` desde el IDE.

Para utilizar el puerto 8080 durante el desarrollo, pasa `--server.port=8080` como argumento de la aplicación. El puerto 80 configurado en el repositorio puede requerir permisos adicionales según el sistema.

## Alcance

Material de formación. El nombre del repositorio se conserva para no cambiar los enlaces existentes. Tiene configuración propia y no debe confundirse con una copia idéntica de `SpringWebSecurity`.
