/**
 * Overlift App
 * Ross, Roman, Ilya, Mercedes
 * Group 2
 * TCSS 450
 */


package edu.tacoma.uw.mtchea.overliftapp;

import java.io.Serializable;

public class UserSocial implements Serializable {
    public String email;
    public String token;

    public UserSocial() {

    }

    public UserSocial(String email, String token)  {
        this.email = email;
        this.token = token;
    }
}
