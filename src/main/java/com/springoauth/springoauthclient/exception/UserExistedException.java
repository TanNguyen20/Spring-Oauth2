package com.springoauth.springoauthclient.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String username) {
        super("User" + username + " existed already");
    }
}
