package de.dashup.model.exceptions;

public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException(String email) {
        super("The Email '" + email + "' is already in use!");
    }
}
