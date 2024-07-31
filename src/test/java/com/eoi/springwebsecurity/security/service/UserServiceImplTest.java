package com.eoi.springwebsecurity.security.service;

import com.eoi.springwebsecurity.coreapp.dto.UserDto;
import com.eoi.springwebsecurity.coreapp.entities.Role;
import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.coreapp.repositories.RoleRepository;
import com.eoi.springwebsecurity.coreapp.repositories.UserRepository;
import com.eoi.springwebsecurity.security.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type User service impl test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { SecurityConfig.class })
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Value("${default.user.role}") // (from application.properties
    private String defaultUserRole;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * Save user test.
     */
    @Test
    void saveUserTest() {
        // Given
        // Creamos un nuevo objeto UserDto y le asignamos algunos valores
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("johndoe@example.com");
        userDto.setPassword("password123");
        Role role = new Role();
        role.setName(defaultUserRole);


        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleRepository.findByName(any(String.class))).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Llamamos al método saveUser del servicio de usuarios (userService) pasando el UserDto creado como parámetro.
        // Esto es lo que estamos probando: que al llamar a este método se cree y se guarde un usuario correctamente en la base de datos.
        userService.saveUser(userDto);

// Creamos un capturador de argumentos (ArgumentCaptor) que nos permitirá capturar el objeto User que se pasa al método save del repositorio de usuarios (userRepository)
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);



// Verificamos que el método save del repositorio de usuarios (userRepository) haya sido llamado una vez (times(1)) y capturamos el objeto User que se le pasa como argumento.
        verify(userRepository, times(1)).save(userCaptor.capture());



// Obtenemos el objeto User capturado por el ArgumentCaptor
        User savedUser = userCaptor.getValue();

// Comprobamos que los valores del objeto User son los que esperamos.
        assertEquals("John Doe", savedUser.getName()); // Comprobamos que el nombre del usuario sea "John Doe"
        assertEquals("johndoe@example.com", savedUser.getEmail()); // Comprobamos que el email del usuario sea "johndoe@example.com"
        assertTrue(passwordEncoder.matches("password123", savedUser.getPassword())); // Comprobamos que la contraseña
        // del usuario sea "password123" y esté correctamente encriptada
        assertNotNull(savedUser.getRoles()); // Comprobamos que el usuario tenga asignado al menos un rol
        assertFalse(savedUser.getRoles().isEmpty()); // Comprobamos que el usuario tenga asignado al menos un rol (otra forma de comprobar lo mismo que la línea anterior)

    }
}