package com.govideo.gerenciador.exceptions.handler;

import java.io.Serializable;
import java.time.Instant;

public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Instant timeStamp;
    private String message;
    private String path;

    public ExceptionResponse() {
    }

    public ExceptionResponse(Instant timeStamp, String message, String path) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.path = path;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
