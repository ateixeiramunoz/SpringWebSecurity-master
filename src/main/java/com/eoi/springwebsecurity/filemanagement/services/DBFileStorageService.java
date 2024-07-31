package com.eoi.springwebsecurity.filemanagement.services;

import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.coreapp.repositories.UserRepository;
import com.eoi.springwebsecurity.filemanagement.entities.FileDB;
import com.eoi.springwebsecurity.filemanagement.models.FileInfo;
import com.eoi.springwebsecurity.filemanagement.repositories.FileDBRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Db file storage service.
 */
@Service
@Log4j2
public class DBFileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Store file db.
     *
     * @param file the file
     *
     * @return the file db
     */
    public FileDB store(MultipartFile file)  {

        //Obtenemos el nombre del fichero que se ha subido en el formulario
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //Creamos el objeto de tipo entidad FileDB para guardar la info en la BD
        FileDB fileDB;
        try {
            //Obtenemos el Content-Type y los Bytes del archivo
            fileDB = new FileDB(null,fileName, file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Guardamos en base de datos la instancia de entidad que hemos creado
        fileDB = fileDBRepository.save(fileDB);

        //Devolvemos el objeto grabado
        return fileDB;
    }

    /**
     * Método que guarda un fichero de un usuario en base de datos
     *
     * @param file the file
     * @param user the user
     *
     * @return the file db
     */
    public FileDB storeUserFile(MultipartFile file, User user)  {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB;
        try {

            fileDB = new FileDB(null,fileName, file.getContentType(), file.getBytes());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileDB = fileDBRepository.save(fileDB);
        user.getFilesDB().add(fileDB);
        userRepository.save(user);

        return fileDB;
    }

    /**
     * Método que guarda un fichero de un usuario en base de datos
     *
     * @param file the file
     * @param user the user
     *
     * @return the file db
     */
    public FileDB storeUserFileWithoutData(@Nullable MultipartFile file, User user)  {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB;
        fileDB = new FileDB(null,fileName, file.getContentType(), null);
        fileDB = fileDBRepository.save(fileDB);
        user.getFilesDB().add(fileDB);
        userRepository.save(user);

        return fileDB;
    }


    /**
     * Gets file.
     *
     * @param id the id
     *
     * @return the file
     */
    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    /**
     * Gets all files.
     *
     * @return the all files
     */
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    /**
     * Gets all file infos.
     *
     * @return the all file infos
     */
    public List<FileInfo> getAllFileInfos() {
        return fileDBRepository.findAll().stream().map(file -> {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getFileName());
            fileInfo.setId(file.getId());
            fileInfo.setUrl("/files/" + file.getId());
            fileInfo.setType(file.getType());
            if(file.getData() == null)
            {
                fileInfo.setSize(0);
            }
            else {
                fileInfo.setSize(file.getData().length);
            }
            return fileInfo;
        }).collect(Collectors.toList());
    }

    /**
     * Gets user file infos.
     *
     * @param user the user
     *
     * @return the user file infos
     */
    public List<FileInfo> getUserFileInfos(User user) {

        return user.getFilesDB().stream().map(file -> {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getFileName());
            fileInfo.setId(file.getId());
            fileInfo.setUrl("/files/" + file.getId());
            fileInfo.setType(file.getType());
            //Significa que el fichero esta guardado en la base de datos, pero que no se han guardado sus bytes o
            // contenido
            if(file.getData() == null)
            {
                //Si en efecto no hemos guardado el contenido en la base de datos como blob, informamos a 0 el tamaño
                fileInfo.setSize(0);
            }
            else
            {
                //Obtenemos el tamaño del fichero guardado en base de datos si el campo blob esta relleno
                fileInfo.setSize(file.getData().length);
            }
            return fileInfo;
        }).collect(Collectors.toList());

    }


    /**
     * Delete file.
     *
     * @param id the id
     */
    public void deleteFile(String id) {
        Optional<FileDB> file = fileDBRepository.findById(id);
        if(file.isPresent()) {
            FileDB fileOk =file.get();
            fileOk.getUsers().clear();
            fileOk = fileDBRepository.save(fileOk);
            fileDBRepository.delete(fileOk);
        }




    }


    /**
     * Método que guarda un fichero de un usuario en base de datos
     *
     * @param id   the id
     * @param user the user
     */
    public void desasociarUserFile(String id, User user)  {

        Optional<FileDB> file = fileDBRepository.findById(id);
        if(file.isPresent())
        {
            user.getFilesDB().remove(file.get());
            userRepository.save(user);
        }
        else
        {
            throw new RuntimeException("El fichero no existe");
        }



    }

}
