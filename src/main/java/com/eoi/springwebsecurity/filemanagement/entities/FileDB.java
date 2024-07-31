package com.eoi.springwebsecurity.filemanagement.entities;

import com.eoi.springwebsecurity.coreapp.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;


/**
 * Clase que representa un archivo en la base de datos.
 */
@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDB {

    /**
     * Identificador único del archivo, generado automáticamente.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    /**
     * Nombre del archivo.
     */
    @NotEmpty
    private String fileName;

    /**
     * Tipo de archivo.
     */
    @NotEmpty
    private String type;

    /**
     * Datos del archivo almacenados como un array de bytes en la base de datos.
     */
    @Lob
    @Column(name="data", columnDefinition="LONGBLOB")
    private byte[] data;

    @ManyToMany(mappedBy="filesDB")
    private List<User> users;

    /**
     * Instantiates a new File db.
     *
     * @param id       the id
     * @param fileName the file name
     * @param type     the type
     * @param data     the data
     */
    public FileDB(String id, String fileName, String type, @NotEmpty byte[] data) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.data = data;
    }
}
