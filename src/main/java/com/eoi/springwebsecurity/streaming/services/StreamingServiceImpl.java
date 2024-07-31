package com.eoi.springwebsecurity.streaming.services;

import com.eoi.springwebsecurity.filemanagement.services.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * The type Streaming service.
 */
@Service
public class StreamingServiceImpl implements StreamingService{


    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private ResourceLoader resourceLoader;



    public Mono<Resource> getVideo(String fileName) {

        return Mono.fromSupplier(() -> fileSystemStorageService.load(fileName));
    }






}
