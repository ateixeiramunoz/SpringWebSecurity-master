package com.eoi.springwebsecurity.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * La anotación @Configuration indica que esta clase se utilizará como una fuente de definiciones de beans para el
 * contexto de la aplicación.
 * Los métodos anotados con @Bean dentro de una clase anotada con @Configuration se utilizan para definir beans de
 * Spring.
 * En este caso, se utiliza la anotación @Configuration para indicar que esta clase se encargará de la configuración de
 * seguridad de la aplicación.
 * <br>
 * La anotación @EnableWebSecurity se utiliza para habilitar la seguridad web en una aplicación Spring Boot.
 * Esta anotación importa automáticamente la clase WebSecurityConfiguration, que proporciona la configuración de
 * seguridad predeterminada.
 * Cuando se utiliza @EnableWebSecurity, también se recomienda definir una clase que extienda
 * WebSecurityConfigurerAdapter para personalizar la configuración de seguridad.
 * En este caso, @EnableWebSecurity se utiliza para habilitar la seguridad web en la aplicación y permitir la
 * personalización de la configuración de seguridad a través de la clase SecurityConfig.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * The User details service.
     */
    @Autowired
    public UserDetailsService userDetailsService;

    /**
     * Método que crea un proveedor de autenticación de DAO y lo configura con el
     * userDetailsService y el passwordEncoder que hemos creado como Beans
     * Primero, se crea una instancia de DaoAuthenticationProvider con new DaoAuthenticationProvider().
     * Luego, se configura el servicio que se utilizará para obtener los detalles del usuario
     * mediante setUserDetailsService(userDetailsService),
     * donde userDetailsService es una instancia de la interfaz UserDetailsService
     * que proporciona los detalles del usuario en función del nombre de usuario.
     * <br>
     * Finalmente, se configura el codificador de contraseñas
     * que se utilizará para verificar las contraseñas de los usuarios
     * al momento de la autenticación mediante setPasswordEncoder(passwordEncoder()),
     * donde passwordEncoder() es una función que devuelve una instancia del codificador
     * de contraseñas que se ha definido en la aplicación.
     * <br>
     * El objeto DaoAuthenticationProvider se devuelve para que se pueda utilizar
     * en la configuración de la autenticación en la aplicación.
     *
     * @return DaoAuthenticationProvider Proveedor de autenticación de DAO.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Método que crea un bean BCryptPasswordEncoder, una implementación de la interfaz PasswordEncoder que utiliza el
     * algoritmo de hashing BCrypt para codificar contraseñas. BCrypt es un algoritmo de hash de contraseñas de uso
     * común, que utiliza una función de derivación de clave adaptativa para generar una cadena de hash criptográfica.
     * Esto significa que el tiempo necesario para codificar la contraseña aumenta a medida que el nivel de seguridad
     * deseado aumenta, lo que dificulta su fuerza bruta.
     *
     * @return PasswordEncoder Bean de codificación de contraseñas con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Método que configura el SecurityFilterChain para el manejo de las peticiones HTTP.
     *
     * @param http Objeto HttpSecurity para configurar el filtro de seguridad.
     *
     * @return SecurityFilterChain Filtro de seguridad configurado.
     *
     * @throws Exception Excepción lanzada si hay problemas al configurar el filtro.
     */
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                /*
//                  Esta línea de código {@code .csrf().disable();} desactiva la protección contra ataques CSRF
//                  (Cross-Site Request Forgery) para todas las solicitudes en la aplicación web.
//                  CSRF es un tipo de ataque de seguridad en el que un atacante engaña al usuario para que ejecute
//                  una acción no deseada en un sitio web, utilizando para ello la sesión activa del usuario.
//                   La desactivación de la protección CSRF no es una práctica recomendada, pero en algunos casos
//                  puede ser necesario hacerlo,
//                  por ejemplo, para pruebas locales o en aplicaciones en las que la protección CSRF no es crítica.
//                  Si no desactivamos csrf, debemos tener en cuenta que funciona de manera automática con la librería
//                   thymeleaf-extras-springsecurity6
//                  Thymeleaf reconocerá los formularios que creemos con el atributo "th:action" y de este modo les
//                  incluirá de manera automática el campo hidden _csfr
//                  De lo contrario, deberíamos hacer dicha gestión a mano.
//                 */
//                //.csrf().disable();
//                .authorizeHttpRequests(authorize -> authorize
//                        // Peticiones permitidas para todos los usuarios
//                        .requestMatchers("/index", "/", "").permitAll()
//                        .requestMatchers("/webjars/**", "/js/**", "/css/**", "/img/**", "/fonts/**", "/favicon.ico").permitAll()
//                        .requestMatchers("*css", "*js").permitAll()
//                        .requestMatchers("/register/**", "/forgot_password", "/reset_password", "/signup", "/about","/error", "/login").permitAll()
//                        //Peticiones asociadas a las notificaciones y conexiones websocket
//                        .requestMatchers("/gs-guide-websocket/**").permitAll()
//
//                        //Peticiones asociadas al calendario
//                        .requestMatchers("/paginacion",
//                                "/filtrado",
//                                "/estructura/**",
//                                "/calendario/**",
//                                "/calendarios/**",
//                                "/eventos" +
//                                "/**").permitAll()
//                         //Peticiones permitidas sólo para usuarios con rol ADMIN
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/users").hasAuthority("ADMIN")
//
//                        // Peticiones permitidas sólo para usuarios autenticados
//                        .requestMatchers(
//                                "/calendarioHTML",
//                                "/chat",
//                                "/videos",
//                                "/numeroNotificaciones",
//                                "/notificaciones",
//                                "/leerNotificacion/**",
//                                "/files/**",
//                                "/upload",
//                                "/test/**",
//                                "/userFiles/**",
//                                "/databasefiles/**",
//                                "/uploadUserFileToDatabaseStoreInFileSystem",
//                                "/uploadUserFileToDatabase",
//                                "/uploadUserFileToFileSystem",
//                                "/uploadToFileSystem",
//                                "/uploadToDatabase",
//                                "/scheduling/**",
//                                "/scheduling/programar").authenticated()
//
//                        //Aceptar a todos los usuarios para stream de videos
//                        .requestMatchers("/stream/**").authenticated()
//                        .requestMatchers("/security/**").authenticated()
//                        // Peticiones permitidas sólo para usuarios autenticados con rol USER
//                        .requestMatchers("/user/**").hasRole("USER")
//
//
//                ).formLogin(
//                        form -> form
//                                .loginPage("/login") // Establece la ruta a la página de inicio de sesión
//                                .loginProcessingUrl("/login") // Establece la ruta de procesamiento del formulario de
//                                // inicio de sesión
//                                .defaultSuccessUrl("/") // Establece la ruta de redirección después de que el usuario
//                                // inicia sesión correctamente
//                                .permitAll() // Permite a cualquier usuario acceder a la página de inicio de sesión
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Establece la ruta para
//                                // procesar la petición de cierre de sesión
//                                .permitAll() // Permite a cualquier usuario acceder a la página de cierre de sesión
//                );
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
        );
        //cierre de sesión
        http.logout(logout -> logout
                .logoutUrl("/usuarios/logout")
                .logoutSuccessUrl("/")
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );



        // Autorización de Solicitudes
        http.authorizeHttpRequests()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/img/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/fonts/**").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                .requestMatchers("/chat/**").permitAll()
                .requestMatchers("/mensajes/").permitAll()
                .anyRequest().authenticated();
        return http.build();





    }








}
