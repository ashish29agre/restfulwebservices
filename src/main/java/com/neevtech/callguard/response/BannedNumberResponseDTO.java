/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.response;

import java.util.List;

/**
 *
 * @author ashish
 */
public class BannedNumberResponseDTO {

    private String status;
    private String message;
    private List<ReportedNumberResp> list;

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

    public List<ReportedNumberResp> getList() {
        return list;
    }

    public void setList(List<ReportedNumberResp> list) {
        this.list = list;
    }
}
