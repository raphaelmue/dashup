package de.dashup.model.exceptions;

import java.lang.reflect.Type;

public class MissingInformationException extends Exception {
    public MissingInformationException(Type type) {
        super("There are missing information on " + type.getTypeName());
    }
}
