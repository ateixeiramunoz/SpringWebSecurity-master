package com.eoi.springwebsecurity;

import com.eoi.springwebsecurity.coreapp.repositories.UserRepository;
import com.eoi.springwebsecurity.filemanagement.services.FileSystemStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


/**
 * The type Spring web security application.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class SpringWebSecurityApplication {

    /**
     * The File system storage service.
     */
    @Autowired
    FileSystemStorageService fileSystemStorageService;

    /**
     * The Thread pool task scheduler.
     */
    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * The Simp messaging template.
     */
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserRepository userRepository;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringWebSecurityApplication.class, args);
    }


    /**
     * Método para iniciar los valores o configuraciones por defecto de la aplicación. La anotación @PostConstruct
     * indica a Spring que el método se debe ejecutar inmediatamente después de la construcción de la clase.
     */
    @PostConstruct
    public void init() {
        fileSystemStorageService.init();
    }
}
