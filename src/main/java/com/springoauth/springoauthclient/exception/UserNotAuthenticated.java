package com.springoauth.springoauthclient.exception;

public class UserNotAuthenticated extends RuntimeException {
    public UserNotAuthenticated() {
        super("User is not authenticated");
    }
}
