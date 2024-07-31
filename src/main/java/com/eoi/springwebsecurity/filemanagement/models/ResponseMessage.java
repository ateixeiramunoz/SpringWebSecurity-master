package com.eoi.springwebsecurity.filemanagement.models;

/**
 * The type Response message.
 */
public class ResponseMessage {
    private String message;

    /**
     * Instantiates a new Response message.
     *
     * @param message the message
     */
    public ResponseMessage(String message) {
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
