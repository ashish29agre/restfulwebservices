/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.response;

/**
 *
 * @author ashish
 */
public class CallReportResponse {

    private boolean isBanned;
    private String phoneNumber;

    public boolean isIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
