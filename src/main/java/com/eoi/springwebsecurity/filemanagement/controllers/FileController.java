package com.eoi.springwebsecurity.filemanagement.controllers;

import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.filemanagement.entities.FileDB;
import com.eoi.springwebsecurity.filemanagement.models.FileInfo;
import com.eoi.springwebsecurity.filemanagement.services.DBFileStorageService;
import com.eoi.springwebsecurity.filemanagement.services.FileSystemStorageService;
import com.eoi.springwebsecurity.security.service.UserService;
import com.eoi.springwebsecurity.websockets.service.MessagingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Controlador encargado de manejar la carga de archivos.
 * La clase utiliza las anotaciones @Controller y @CrossOrigin para definir que es un controlador de Spring y
 * permitir solicitudes CORS desde el servidor web localhost en el puerto 8080.
 * La clase utiliza la inyección de dependencias (@Autowired) para acceder a los servicios necesarios para la carga
 * y almacenamiento de archivos, así como para el acceso a la base de datos de archivos.
 */
@Controller
@Log4j2
@CrossOrigin("http://localhost:8080")
public class FileController {

    /**
     * Servicio de almacenamiento de archivos en FileSystem local utilizado por el controlador.
     */
    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private MessagingService messagingService;

    /**
     * Servicio de almacenamiento de archivos en la base de datos utilizado por el controlador.
     */
    @Autowired
    private DBFileStorageService dbFileStorageService;

    /**
     * Servicio de usuario utilizado por el controlador.
     */
    @Autowired
    private UserService userService;

    /**
     * Constructor de la clase que recibe el servicio de almacenamiento de archivos como parámetro.
     * La anotación @Autowired se utiliza para inyectar automáticamente el servicio necesario al crear una instancia de
     * la clase.
     *
     * @param fileSystemStorageService el servicio de almacenamiento de archivos a utilizar
     */
    @Autowired
    public FileController(FileSystemStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }


    /**
     * Método que se encarga de listar los archivos que han sido subidos al servidor.
     *
     * @param model          el modelo que se va a utilizar para pasar los datos a la vista
     * @param authentication the authentication
     *
     * @return el nombre de la vista a la que se va a redirigir
     *
     * @throws IOException si ocurre un error al cargar los archivos
     */
    @GetMapping("/files")
    public String listAllUploadedFiles(Model model,Authentication authentication) throws IOException {


        //Obtenemos el nombre de usuario del objeto de autenticacion
        String username = authentication.getName();
        // Buscamos al usuario correspondiente al nombre de usuario obtenido anteriormente.
        User user = userService.findUserByEmail(username);


        // Obtenemos todos los archivos almacenados en el servicio de almacenamiento predeterminado.
        // Para cada archivo, generamos una URL que permita descargar el archivo desde el servidor.
        List<FileInfo> files = fileSystemStorageService.loadAll();

        // Obtenemos todos los archivos almacenados en el servicio de almacenamiento de la base de datos.
        // Para cada archivo, generamos una URL que permita descargar el archivo desde el servidor.
        List<FileInfo> dbFiles = dbFileStorageService.getAllFileInfos();

        List<FileInfo> userFiles = fileSystemStorageService.loadAllFromUser(user.getId());


        // Obtenemos todos los archivos asociados al usuario y almacenados en la base de datos
        // Para cada archivo, generamos una URL que permita descargar el archivo desde el servidor.
        List<FileInfo> dbUserFiles = dbFileStorageService.getUserFileInfos(user);

        // Agregamos las URLs de los archivos del servicio de almacenamiento predeterminado al modelo.
        model.addAttribute("files", files);

        // Agregamos los objetos FileInfo del servicio de almacenamiento de la base de datos al modelo.
        model.addAttribute("DBfiles", dbFiles);
        model.addAttribute("userFiles", userFiles);
        model.addAttribute("dbUserFiles", dbUserFiles);


        // Devolvemos el nombre de la vista a la que se va a redirigir.
        return "listFicheros";
    }


    /**
     * Método que se encarga de descargar un archivo desde el servidor.
     *
     * @param filename el nombre del archivo que se va a descargar
     *
     * @return una respuesta HTTP con el archivo a descargar en el cuerpo de la respuesta
     * '@GetMapping("/files/{filename:.+}")' es una anotación utilizada en un método dentro de un controlador de Spring
     * MVC que indica que el método manejará solicitudes GET para una URL que incluya una variable de ruta {filename}.
     * El . + en la variable de ruta indica que la variable puede contener un punto y cualquier otro carácter después de
     * él. Esto es necesario porque algunos nombres de archivo pueden contener puntos en su nombre y la expresión
     * regular predeterminada utilizada por Spring no permitiría estos caracteres. Por ejemplo, si la URL solicitada es
     * /files/myfile.txt, la variable de ruta {filename} será igual a myfile.txt. Si la URL solicitada es
     * /files/myfile.pdf, la variable de ruta {filename} también será igual a myfile.pdf.
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        // Cargamos el archivo como un recurso a través del servicio de almacenamiento predeterminado.
        Resource file = fileSystemStorageService.loadAsResource(filename);

        // Construimos una respuesta HTTP con el archivo a descargar en el cuerpo de la respuesta.
        // También establecemos el encabezado "Content-Disposition" con el nombre de archivo para indicar que se debe descargar.
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    /**
     * Método que se encarga de manejar la subida de un archivo al servidor.
     *
     * @param file               el archivo que se va a subir
     * @param redirectAttributes los atributos que se van a utilizar para pasar información entre solicitudes
     *
     * @return el nombre de la vista a la que se va a redirigir
     */
    @PostMapping("/uploadToFileSystem")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Principal principal) {

        // Guardamos el archivo en el servicio de almacenamiento predeterminado.
        fileSystemStorageService.save(file);


        messagingService.crearMensajeYNotificacionDeAdminCuandoOcurreAlgoYEnviarElMensaje("Se ha subido un fichero",
                principal.getName());

        // Agregamos un mensaje de éxito a los atributos de redirección.
        redirectAttributes.addFlashAttribute("message",
                "¡Se ha subido " + file.getOriginalFilename() + " correctamente!");

        // Redirigimos al usuario a la vista que lista los archivos subidos al servidor.
        return "redirect:/files";
    }


    /**
     * Método que recibe una solicitud POST para cargar un archivo a la base de datos.
     *
     * @param file               el archivo cargado en el formulario
     * @param redirectAttributes objeto utilizado para agregar atributos a la redirección
     * @param authentication     objeto que representa la información de autenticación del usuario que realiza la
     *                           solicitud
     *
     * @return una cadena de texto con la vista redirigida
     */
    @PostMapping("/uploadToDatabase")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes,
                             Authentication authentication) {

        String message = "";
        try {
            // Almacenar el archivo en la base de datos
            dbFileStorageService.store(file);

            // Agregar mensaje a los atributos de la redirección
            redirectAttributes.addFlashAttribute("message",
                    "¡Archivo " + file.getOriginalFilename() + " cargado exitosamente a la base de datos!");

            // Redirigir a la lista de archivos
            return "redirect:/files";

        } catch (Exception e) {
            // Agregar mensaje de error a los atributos de la redirección
            redirectAttributes.addFlashAttribute("errorMsg", e.getLocalizedMessage());

            // Redirigir a la página de error
            return "error";
        }
    }


    /**
     * Upload user file to database store in file system string.
     *
     * @param file               the file
     * @param redirectAttributes the redirect attributes
     * @param authentication     the authentication
     *
     * @return the string
     */
    @PostMapping("/uploadUserFileToDatabaseStoreInFileSystem")
    public String uploadUserFileToDatabaseStoreInFileSystem(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes,
                                           Authentication authentication) {
        String message = "";
        try {
            //Obtenemos el nombre de usuario del objeto de autenticacion
            String username = authentication.getName();
            // Buscamos al usuario correspondiente al nombre de usuario obtenido anteriormente.
            User user = userService.findUserByEmail(username);
            // Almacenamos el archivo del usuario en la base de datos pero sin guardar sus datos
            dbFileStorageService.storeUserFileWithoutData(file,user);
            // Guardamos el fichero en el filesystem
            fileSystemStorageService.saveUserFile(file,user.getId());
            // Agregar mensaje a los atributos de la redirección
            redirectAttributes.addFlashAttribute("message",
                    "¡Archivo " + file.getOriginalFilename() + " cargado exitosamente a la base de datos!");

            // Redirigir a la lista de archivos
            return "redirect:/files";

        } catch (Exception e) {

            log.error("ERROR EN LA APLICACION", e);
            // Agregar mensaje de error a los atributos de la redirección
            redirectAttributes.addFlashAttribute("errorMsg", e.getLocalizedMessage());

            // Redirigir a la página de error
            return "error";
        }
    }


    /**
     * Método que recibe una solicitud POST para cargar un archivo propio de un usuario a la base de datos.
     *
     * @param file               el archivo cargado en el formulario
     * @param redirectAttributes objeto utilizado para agregar atributos a la redirección
     * @param authentication     objeto que representa la información de autenticación del usuario que realiza la
     *                           solicitud
     *
     * @return una cadena de texto con la vista redirigida
     */
    @PostMapping("/uploadUserFileToDatabase")
    public String uploadUserFileToDatabase(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes,
                             Authentication authentication) {

        String message = "";
        try {
            //Obtenemos el nombre de usuario del objeto de autenticacion
            String username = authentication.getName();
            // Buscamos al usuario correspondiente al nombre de usuario obtenido anteriormente.
            User user = userService.findUserByEmail(username);

            // Almacenamos el archivo del usuario en la base de datos
            dbFileStorageService.storeUserFile(file,user);

            // Agregar mensaje a los atributos de la redirección
            redirectAttributes.addFlashAttribute("message",
                    "¡Archivo " + file.getOriginalFilename() + " cargado exitosamente a la base de datos!");

            // Redirigir a la lista de archivos
            return "redirect:/files";

        } catch (Exception e) {
            // Agregar mensaje de error a los atributos de la redirección
            redirectAttributes.addFlashAttribute("errorMsg", e.getLocalizedMessage());

            // Redirigir a la página de error
            return "error";
        }
    }


    /**
     * Método que se encarga de manejar la subida de un archivo de usuario al servidor.
     *
     * @param file               el archivo que se va a subir
     * @param redirectAttributes los atributos que se van a utilizar para pasar información entre solicitudes
     * @param authentication     la información de autenticación del usuario que está realizando la solicitud
     *
     * @return el nombre de la vista a la que se va a redirigir
     */
    @PostMapping("/uploadUserFileToFileSystem")
    public String handleUserFileUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes,
                                       Authentication authentication) {

        // Obtenemos el nombre de usuario del usuario autenticado.
        String username = authentication.getName();

        // Buscamos al usuario correspondiente al nombre de usuario obtenido anteriormente.
        User user = userService.findUserByEmail(username);

        // Obtenemos el ID del usuario.
        Long userId = user.getId();

        // Guardamos el archivo en el servicio de almacenamiento de archivos de usuario.
        fileSystemStorageService.saveUserFile(file, userId);

        // Agregamos un mensaje de éxito a los atributos de redirección.
        redirectAttributes.addFlashAttribute("message",
                "¡Se ha subido correctamente el archivo de usuario " + file.getOriginalFilename() + "!");

        // Redirigimos al usuario a la vista que lista los archivos subidos al servidor.
        return "redirect:/files";
    }


    /**
     * Método que se encarga de obtener el archivo de la base de datos con el id proporcionado.
     *
     * @param id El id del archivo a obtener de la base de datos.
     *
     * @return ResponseEntity con el archivo obtenido y las cabeceras necesarias para la descarga del archivo.
     */
    @GetMapping("/databasefiles/{id}")
    public ResponseEntity<byte[]> getDatabaseFile(@PathVariable String id) {
        // Obtiene el archivo de la base de datos utilizando el servicio correspondiente.
        FileDB fileDB = dbFileStorageService.getFile(id);

        // Retorna un ResponseEntity con el archivo obtenido y las cabeceras necesarias para la descarga del archivo.
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
                .body(fileDB.getData());
    }


    /**
     * Método que elimina un archivo de la base de datos a través de su identificador.
     *
     * @param id El identificador del archivo a eliminar.
     *
     * @return La página de archivos después de eliminar el archivo.
     */
    @GetMapping("/databasefiles/delete/{id}")
    public String deleteFileDB(@PathVariable String id) {
        dbFileStorageService.deleteFile(id);
        return "redirect:/files";
    }


    /**
     * Delete file from file system string.
     *
     * @param fileName the file name
     *
     * @return the string
     */
    @GetMapping("/files/delete/{fileName}")
    public String deleteFileFromFileSystem(@PathVariable String fileName) {
        fileSystemStorageService.deleteFile(fileName);
        return "redirect:/files";
    }

    /**
     * Delete file from file system string.
     *
     * @param id             the id
     * @param authentication the authentication
     *
     * @return the string
     */
    @GetMapping("/databasefiles/desasociarUserFile/{id}")
    public String deleteFileFromFileSystem(@PathVariable String id, Authentication authentication) {
        // Obtenemos el nombre de usuario del usuario autenticado.
        String username = authentication.getName();
        // Buscamos al usuario correspondiente al nombre de usuario obtenido anteriormente.
        User user = userService.findUserByEmail(username);

        dbFileStorageService.desasociarUserFile(id, user);
        return "redirect:/files";
    }


    /**
     * Controlador de excepción para la excepción FileNotFoundException.
     * Retorna una respuesta con un estado HTTP 404 (no encontrado).
     *
     * @param exc la excepción FileNotFoundException que se ha producido
     *
     * @return ResponseEntity con un estado HTTP 404 (no encontrado)
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}