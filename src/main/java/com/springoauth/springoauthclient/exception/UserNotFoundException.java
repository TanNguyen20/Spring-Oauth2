package com.springoauth.springoauthclient.exception;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {

    public UserNotFoundException(long userId) {
        super(String.format("User %d not found", userId));
    }


    public UserNotFoundException(String message) {
        super(message);
    }
}
