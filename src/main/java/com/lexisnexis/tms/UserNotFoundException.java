package com.lexisnexis.tms;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message) {
        super(message);
    }
}
