package com.eoi.springwebsecurity.streaming.services;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

/**
 * The interface Streaming service.
 */
public interface StreamingService {

    /**
     * Gets video.
     *
     * @param title the title
     *
     * @return the video
     */
    Mono<Resource> getVideo(String title);

}
