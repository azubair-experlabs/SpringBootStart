package com.experlabs.SpringBootStart.core.models;

import lombok.Data;

@Data
public class ResponseBody {
    private int status;
    private String message;

    public ResponseBody(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
