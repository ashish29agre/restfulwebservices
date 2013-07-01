/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.response;

/**
 *
 * @author ashish
 */
public class ResponseDTO {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
