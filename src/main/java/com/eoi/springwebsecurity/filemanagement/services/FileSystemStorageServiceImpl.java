package com.eoi.springwebsecurity.filemanagement.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.eoi.springwebsecurity.filemanagement.controllers.FileController;
import com.eoi.springwebsecurity.filemanagement.models.FileInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

/**
 * Clase que implementa la interfaz StorageService y proporciona métodos para la gestión de archivos en un directorio de
 * almacenamiento.
 */
@Service
@Log4j2
public class FileSystemStorageServiceImpl implements FileSystemStorageService {


    private final Path root = Paths.get("uploads");


    /**
     * Inicializa el directorio de almacenamiento de archivos.
     * Crea el directorio si no existe.
     * Si se produce una excepción IOException, se lanza una RuntimeException.
     * cuando una clase anotada con @Service es creada por Spring, se llama automáticamente al método init() en el momento de la inicialización del objeto.
     * En este caso, el método init() se utiliza para asegurarse de que el directorio de almacenamiento de archivos existe, y si no existe, lo crea.
     * Al ser un @Service de Spring Boot, se encarga de administrar el ciclo de vida del objeto y llamar a los métodos de manera apropiada.
     * Por lo tanto, no es necesario llamar manualmente al método init()
     */
    @Override
    public void init() {
        try {
            Files.createDirectories(root);
            log.info("Se ha inicializado el directorio de almacenamiento de archivos en " +  root);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el directorio de almacenamiento de archivos!");
        }
    }



    /**
     * Guarda el archivo en el directorio de almacenamiento de archivos.
     * Si el archivo ya existe, se lanza una RuntimeException.
     * Si se produce una excepción, se lanza una RuntimeException con el mensaje de error.
     * @param file el archivo a guardar.
     */
    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Ya existe un archivo con ese nombre.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void saveUserFile(MultipartFile file, Long userID) {
        try {
            Path userPath = this.root.resolve(userID.toString());

            if(!Files.exists(userPath))
            {
                Files.createDirectories(userPath);
            }
            Files.copy(file.getInputStream(), this.root.resolve(userID  + "/" + file.getOriginalFilename()));

        } catch (Exception e) {

            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Ya existe un archivo con ese nombre.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Carga el archivo con el nombre de archivo proporcionado del directorio de almacenamiento de archivos.
     * Si el archivo no existe o no se puede leer, se lanza una RuntimeException.
     * Si se produce una excepción MalformedURLException, se lanza una RuntimeException con el mensaje de error.
     * @param filename el nombre del archivo a cargar.
     * @return Resource con el archivo cargado.
     */
    @Override
    public Resource load(String filename) {
        try {
            //Crea un objeto "Path" para controlar los directorios o ficheros. En este caso, el nombre del fichero a
            // recoger.
            Path file = root.resolve(filename);
            //Crea un objeto recurso, convirtiendo el objeto a data:URI
            Resource resource = new UrlResource(file.toUri());
            //Si el objeto recurso se ha creado correctamente y se puede leer, lo devuelve al servicio de Streaming
            // para ser servido como objeto Mono (objeto de Stream).
            if (resource.exists() &&  resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se pudo leer el archivo!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina todos los archivos y subdirectorios del directorio de almacenamiento de archivos.
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    /**
     * Carga todos los archivos del directorio de almacenamiento de archivos.
     * Se excluye el directorio raíz.
     * Si se produce una excepción IOException, se lanza una RuntimeException con el mensaje de error.
     *
     * @return Stream de Path con los archivos cargados.
     */
    @Override
    public List<FileInfo> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .filter(path -> !path.toFile().isDirectory())
                    .map(path -> {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(path.getFileName().toString());
                        fileInfo.setUrl(MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                        "serveFile", path.getFileName().toString())
                                .build().toUri().toString());
                        try {
                            fileInfo.setType(Files.probeContentType(path));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        fileInfo.setSize(path.toFile().length());
                        return fileInfo;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("No se pudieron cargar los archivos!", e);
        }
    }



    @Override
    public List<FileInfo> loadAllFromUser(Long userId) {

        try {

            Path userPath = this.root.resolve(String.valueOf(userId));
            if(!Files.exists(userPath))
            {
                Files.createDirectories(userPath);
            }

            return Files.walk(userPath, 1)
                    .filter(path -> !path.equals(userPath))
                    .filter(path -> !path.toFile().isDirectory())
                    .map(path -> {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(path.getFileName().toString());
                        fileInfo.setUrl(MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                        "serveFile", path.getFileName().toString())
                                .build().toUri().toString());
                        try {
                            fileInfo.setType(Files.probeContentType(path));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        fileInfo.setSize(path.toFile().length());
                        return fileInfo;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("No se pudieron cargar los archivos!", e);
        }
    }


    @Override
    public Resource loadAsResource(String filename) {
        Resource resource = load(filename);
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            return null;
        }
    }


    @Override
    public void deleteFile(String filename) {
            try {
                Path file = root.resolve(filename);
                Files.delete(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }


    @Override
    public List<FileInfo> loadAllByFileType(String fileType) {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .filter(path -> !path.toFile().isDirectory())
                    .filter(path -> {
                        try {
                            return Objects.nonNull(Files.probeContentType(path)) && Files.probeContentType(path).equals(fileType);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(path -> {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(path.getFileName().toString());
                        fileInfo.setUrl(MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                        "serveFile", path.getFileName().toString())
                                .build().toUri().toString());
                        try {
                            fileInfo.setType(Files.probeContentType(path));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        fileInfo.setSize(path.toFile().length());
                        return fileInfo;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("No se pudieron cargar los archivos!", e);
        }
    }

}
