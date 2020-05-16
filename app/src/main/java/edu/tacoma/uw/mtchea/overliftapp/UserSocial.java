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
