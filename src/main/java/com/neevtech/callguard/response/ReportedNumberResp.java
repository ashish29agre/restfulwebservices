/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.response;

/**
 *
 * @author ashish
 */
public class ReportedNumberResp {

    private boolean banned;
    private String phoneNumber;

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return this.phoneNumber;
    }
    
}
