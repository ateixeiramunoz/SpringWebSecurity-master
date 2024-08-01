# Introducción al Proyecto de Ejemplo Spring Boot EOI

El Proyecto de Ejemplo Spring Boot EOI es una aplicación guía diseñada para facilitar el aprendizaje de [Spring Boot](https://spring.io/projects/spring-boot), un poderoso framework de desarrollo de aplicaciones Java. Spring Boot simplifica la creación de aplicaciones empresariales, proporcionando una configuración predefinida y una estructura de proyecto fácil de seguir.

Este proyecto de ejemplo utiliza [Spring Boot 3.3.0](https://spring.io/projects/spring-boot), una versión estable y confiable de Spring Boot en el momento de su desarrollo. Además, se emplean tecnologías y herramientas adicionales como [Maven](https://maven.apache.org/) para la gestión de dependencias y el empaquetado en formato JAR, y Java 20 como el lenguaje de programación principal.


Por defecto, el proyecto está configurado para lanzar el perfil "local", el cual utiliza una base de datos [H2](https://www.h2database.com/html/main.html) en memoria. Esto permite ejecutar la aplicación sin necesidad de configurar una base de datos externa.

Sin embargo, si se elige el perfil "dev", se seleccionará un sistema de conexión en el que deberemos informar las variables de entorno de la base de datos o conectar a una base de datos local de [MySQL](https://www.mysql.com/) por defecto. Esto proporciona flexibilidad para utilizar una base de datos más robusta y adecuada para entornos de desarrollo.

Para el desarrollo de esta aplicación, se recomienda el uso del entorno de desarrollo [IntelliJ IDEA](https://www.jetbrains.com/idea/), conocido por su amplio conjunto de características y facilidades para el desarrollo de aplicaciones Java. También se sugiere la instalación de algunos plugins, como [Docker](https://www.docker.com/) para la gestión de contenedores, [JPA Buddy](https://plugins.jetbrains.com/plugin/11325-jpa-buddy) para mejorar la experiencia de desarrollo con JPA y [Database Navigator](https://plugins.jetbrains.com/plugin/1800-database-navigator) para explorar y administrar bases de datos directamente desde el IDE.

La ejecución del proyecto se realiza mediante la configuración de ciertas variables de entorno, que permiten adaptar la aplicación a diferentes escenarios. Estas variables incluyen SPRING_PROFILE para definir el perfil de ejecución, DATABASE_TYPE para especificar el tipo de base de datos a utilizar, DATABASE_NAME para el nombre de la base de datos, DATABASE_USERNAME y DATABASE_PASSWORD para las credenciales de acceso, y DATABASE_HOST y DATABASE_PORT para la ubicación y el puerto del servidor de la base de datos. Además, DATABASE_DRIVER se utiliza para indicar el controlador de la base de datos.

Con el Proyecto de Ejemplo Spring Boot EOI, podrás familiarizarte con los conceptos fundamentales de Spring Boot, incluyendo la configuración automática, la gestión de dependencias, el acceso a bases de datos mediante JPA, entre otros. A través de su estructura organizada y su configuración predefinida, este proyecto te servirá como punto de partida para tus propias aplicaciones basadas en Spring Boot.



## Descargar el proyecto desde Github en IntelliJ

Para descargar el proyecto desde GitLab en IntelliJ, sigue estos pasos:

1. Copia la URL del repositorio de Github  que contiene el proyecto.
2. Abre IntelliJ IDEA y selecciona la opción "Check out from Version Control" en la pantalla de inicio.
3. En el menú desplegable, elige "Git" como el sistema de control de versiones.
4. Pega la URL del repositorio en el campo "URL".
5. Elige la ubicación en tu sistema donde deseas guardar el proyecto.
6. Haz clic en "Clone" para iniciar la descarga del proyecto.
7. Una vez completada la descarga, IntelliJ importará automáticamente el proyecto.
8. Ahora puedes explorar y trabajar en el proyecto utilizando todas las funciones y herramientas de IntelliJ IDEA.

Recuerda que es necesario tener Git instalado en tu sistema



## Ejecución del Proyecto en IntelliJ

Sigue estos pasos para ejecutar el proyecto `EjemplospringbootApplication` en IntelliJ:

1. Una vez que el proyecto se haya cargado correctamente, busca la clase `EjemplospringbootApplication`. Puedes encontrarla en la ruta `src/main/java/com/example/ejemplospringboot/EjemplospringbootApplication.java`.
2. Haz clic derecho en la clase `EjemplospringbootApplication` y selecciona "Run 'EjemplospringbootApplication'" para ejecutar la aplicación.
3. IntelliJ compilará y construirá el proyecto, y la aplicación se iniciará.

Si estás utilizando el perfil "local", la aplicación se ejecutará con una base de datos H2 en memoria por defecto. Si deseas utilizar el perfil "dev" con una base de datos MySQL, asegúrate de configurar las variables de entorno necesarias con los detalles de la base de datos, como se describe anteriormente.

Una vez que la aplicación se haya iniciado correctamente, puedes acceder a ella a través de un navegador web en la dirección `http://localhost:8080` (o el puerto especificado en la configuración).

¡Disfruta explorando y probando el proyecto en IntelliJ!




## Configurar el entorno de desarrollo local

Sigue estos pasos para configurar tu entorno de desarrollo local:

### 1. Instalar IntelliJ IDEA

- Descarga [IntelliJ IDEA](https://www.jetbrains.com/idea/) desde el sitio web oficial.
- Sigue las instrucciones de instalación para tu sistema operativo.
- Una vez instalado, ábrelo y configura tu entorno de trabajo según tus preferencias.

### 2. Instalar los plugins indicados

Para aprovechar al máximo el desarrollo en este proyecto, se recomienda instalar los siguientes plugins en IntelliJ IDEA:

- **Docker**: Este plugin te permite gestionar y administrar contenedores Docker directamente desde el IDE, lo que facilita la integración de tus aplicaciones con Docker.
- **JPA Buddy**: El plugin JPA Buddy mejora la experiencia de desarrollo al proporcionar características específicas para trabajar con JPA (Java Persistence API) en tu proyecto.
- **Database Navigator**: Este plugin te permite explorar y administrar bases de datos directamente desde el IDE, lo que facilita la interacción con tu base de datos durante el desarrollo.

### 3. Instalar Docker Desktop

- Descarga [Docker Desktop](https://www.docker.com/products/docker-desktop) según tu sistema operativo.
- Sigue las instrucciones de instalación para tu sistema operativo.
- Una vez instalado, asegúrate de que Docker esté en funcionamiento y configurado correctamente en tu entorno.

Asegúrate de seguir estos pasos para configurar tu entorno de desarrollo local antes de comenzar a trabajar en el proyecto. Esto te permitirá aprovechar al máximo las herramientas y los recursos necesarios para el desarrollo exitoso del proyecto.

¡Por supuesto! Aquí tienes un ejemplo con los valores proporcionados anteriormente para crear un modo de ejecución en IntelliJ:






## Crear un modo de ejecución en IntelliJ con variables de entorno

Sigue estos pasos para crear un modo de ejecución en IntelliJ con las variables de entorno proporcionadas:

1. Abre IntelliJ IDEA y carga el proyecto.
2. Haz clic en la configuración de ejecución en la parte superior derecha de la ventana de IntelliJ (generalmente junto al botón de ejecución verde).
3. Selecciona "Edit Configurations" en el menú desplegable.
4. En la ventana de configuraciones, haz clic en el botón "+" en la esquina superior izquierda y selecciona "Spring Boot" en la lista de opciones.
5. En la sección "Environment", agrega las siguientes variables de entorno con los valores correspondientes:

   - Variable: `SPRING_PROFILE`
     Valor: `dev`

   - Variable: `DATABASE_TYPE`
     Valor: `mysql`

   - Variable: `DATABASE_NAME`
     Valor: `springboot`

   - Variable: `DATABASE_USERNAME`
     Valor: `root`

   - Variable: `DATABASE_PASSWORD`
     Valor: `password`

   - Variable: `DATABASE_HOST`
     Valor: `localhost`

   - Variable: `DATABASE_PORT`
     Valor: `3306`

   - Variable: `DATABASE_DRIVER`
     Valor: `com.mysql.cj.jdbc.Driver`

6. En la parte inferior de la ventana de configuraciones, selecciona la clase principal del proyecto (por ejemplo, `EjemplospringbootApplication`) como el punto de entrada.
7. Haz clic en "Apply" y luego en "OK" para guardar la configuración.

Ahora puedes ejecutar el proyecto en IntelliJ utilizando este modo de ejecución con las variables de entorno especificadas. Asegúrate de seleccionar esta configuración antes de ejecutar el proyecto para utilizar los valores proporcionados.

Recuerda que estos valores son solo un ejemplo y puedes ajustarlos según tus necesidades y configuración específica.

Puedes copiar y pegar este código Markdown en tu archivo README.md para incluir el ejemplo de creación de un modo de ejecución en IntelliJ con las variables de entorno proporcionadas. Asegúrate de ajustar el formato según tus necesidades y convenciones del archivo README.md.



## Ejecutar la aplicación y la base de datos en diferentes contenedores de Docker

Si deseas ejecutar la aplicación y la base de datos en diferentes contenedores de Docker en el mismo sistema, es recomendable crear una red personalizada en el plugin de Docker en IntelliJ y unir el contenedor MySQL a esa red. Esto permite una comunicación fácil y segura entre los contenedores.

Puedes seguir estos pasos para crear una red personalizada y unir el contenedor MySQL a esa red utilizando el plugin de Docker en IntelliJ:

1. Abre IntelliJ IDEA y carga tu proyecto.

2. Haz clic en la pestaña "Services" en la parte inferior izquierda de la ventana de IntelliJ para abrir la ventana de servicios.

3. En la ventana de servicios, haz clic derecho en la sección "Networks" y selecciona la opción "Create Network".

4. En la ventana emergente, ingresa un nombre para la red en el campo "Name" y haz clic en el botón "OK" para crear la red personalizada.

5. Una vez creada la red, haz clic derecho en el contenedor MySQL que deseas unir a la red y selecciona la opción "Edit Container Settings".

6. En la configuración del contenedor, ve a la sección "Networks" y haz clic en el botón "+" para agregar una nueva red.

7. Selecciona la red personalizada que creaste en el paso anterior y haz clic en el botón "OK" para unir el contenedor MySQL a esa red.

8. Guarda los cambios en la configuración del contenedor.

9. Ahora, puedes crear un nuevo contenedor para ejecutar la aplicación y unirlo a la misma red personalizada.

10. Para crear el contenedor de la aplicación, sigue los pasos mencionados anteriormente para crear un contenedor, pero selecciona la imagen de la aplicación y configura los puertos y variables de entorno según sea necesario.

11. En la configuración del contenedor de la aplicación, ve a la sección "Networks" y selecciona la misma red personalizada que seleccionaste para el contenedor MySQL.

12. Guarda los cambios en la configuración del contenedor de la aplicación.

13. Ahora, tanto el contenedor de la aplicación como el contenedor MySQL están conectados a la misma red personalizada, lo que les permite comunicarse entre sí.

Recuerda que este es solo un ejemplo y puedes ajustar los valores y configuraciones según tus necesidades, como los nombres de la red, las imágenes de los contenedores y las variables de entorno.

¡Listo! Ahora puedes ejecutar la aplicación y la base de datos en diferentes contenedores de Docker en el mismo sistema utilizando una red personalizada en el plugin de Docker en IntelliJ.



## Perfiles de la Aplicación

Existen difetentes perfiles de la aplicación, que utilizamos para poder ejecutar la misma en diferentes entornos o con diferentes condiciones.


### Perfil `local`

Este perfil está diseñado para ejecutar la aplicación en un entorno local. No requiere una base de datos externa, ya que utiliza una base de datos H2 en memoria. Es útil durante el desarrollo y las pruebas locales.

### Perfil `desarrollo`

Este perfil está destinado a entornos de desarrollo. Utiliza una base de datos MySQL y se configura con parámetros específicos para este entorno. Puede requerir la configuración de variables de entorno o archivos de propiedades adicionales.

### Perfil `produccion`

Este perfil está dirigido a entornos de producción. También utiliza una base de datos MySQL, pero se configura con parámetros específicos para el entorno de producción. Puede requerir configuraciones adicionales y consideraciones de seguridad.


Recuerda que puedes personalizar aún más los perfiles y agregar tus propios perfiles según tus requisitos específicos.

¡Ahora puedes aprovechar los perfiles de la aplicación para adaptarla a diferentes entornos y configuraciones!


Durante la ejecución del proyecto, se utilizan ciertas variables de entorno para adaptar la aplicación a diferentes escenarios, especialmente cuando se activan perfiles específicos. A continuación se enumeran las variables de entorno utilizadas en este proyecto, algunas de las cuales pueden ser necesarias según el perfil activo:

- `SPRING_PROFILE`: Esta variable define el perfil de ejecución de la aplicación y puede ser necesaria para cargar la configuración correspondiente a un perfil específico.
- `DATABASE_TYPE`: Esta variable especifica el tipo de base de datos que se utilizará y puede ser necesaria para configurar correctamente la conexión a la base de datos según el perfil.
- `DATABASE_NAME`: Esta variable define el nombre de la base de datos y puede ser necesaria para establecer la base de datos adecuada según el perfil.
- `DATABASE_USERNAME`: Esta variable define el nombre de usuario para acceder a la base de datos y puede ser necesaria para autenticarse en la base de datos según el perfil.
- `DATABASE_PASSWORD`: Esta variable define la contraseña para acceder a la base de datos y puede ser necesaria para autenticarse en la base de datos según el perfil.
- `DATABASE_HOST`: Esta variable define la ubicación (host) del servidor de la base de datos y puede ser necesaria para establecer la conexión con el servidor de la base de datos según el perfil.
- `DATABASE_PORT`: Esta variable define el puerto del servidor de la base de datos y puede ser necesaria para establecer la conexión con el servidor de la base de datos según el perfil.
- `DATABASE_DRIVER`: Esta variable define el controlador de la base de datos a utilizar y puede ser necesaria para cargar el controlador adecuado según el perfil.

Asegúrate de configurar correctamente estas variables de entorno según tus necesidades y el perfil activo antes de ejecutar el proyecto.



### Ciclo de Vida de Maven

El ciclo de vida de Maven consta de una serie de fases que se ejecutan secuencialmente para construir y gestionar un proyecto. Cada fase realiza tareas específicas en el proceso de desarrollo y construcción del proyecto. A continuación se describen las principales fases del ciclo de vida de Maven:

#### clean
La fase `clean` se encarga de eliminar los archivos generados en compilaciones anteriores, como los directorios `target` y los archivos de compilación.

Comando Maven: `mvn clean`

#### validate
La fase `validate` valida la estructura y la sintaxis del proyecto para asegurarse de que es válido y que todas las dependencias necesarias están disponibles.

Comando Maven: `mvn validate`

#### compile
La fase `compile` compila los archivos fuente del proyecto y genera los archivos compilados, como los archivos `.class` para proyectos Java, en el directorio `target`.

Comando Maven: `mvn compile`

#### test
En la fase `test`, Maven ejecuta las pruebas unitarias del proyecto. Busca los archivos de prueba ubicados en el directorio `src/test` y los ejecuta utilizando un marco de pruebas como JUnit.

Comando Maven: `mvn test`

#### package
En la fase `package`, Maven empaca los archivos compilados y otros recursos necesarios en un formato específico, como un archivo JAR para proyectos Java.

Comando Maven: `mvn package`

#### verify
La fase `verify` realiza verificaciones adicionales sobre el proyecto y los resultados de las pruebas.

Comando Maven: `mvn verify`

#### install
En la fase `install`, Maven instala el artefacto en el repositorio local de Maven. Esto permite su reutilización en otros proyectos.

Comando Maven: `mvn install`

#### deploy
La fase `deploy` copia el artefacto en un repositorio remoto, como un servidor de artefactos Maven, para su distribución o uso por otros desarrolladores.

Comando Maven: `mvn deploy`

Estas son las fases principales del ciclo de vida de Maven. Cada una de ellas cumple una función específica en el proceso de construcción y gestión del proyecto. Maven permite ejecutar estas fases de forma individual o como parte del ciclo completo para llevar a cabo diversas tareas en el desarrollo de software.



# Limpiar y compilar el proyecto
`mvn clean compile`

# Ejecutar las pruebas unitarias
`mvn test`

# Empaquetar el proyecto en un archivo JAR
`mvn package`

# Ejecutar la aplicación Spring Boot
`mvn spring-boot:run`

# Realizar el deploy del artefacto en un repositorio remoto
`mvn deploy`


