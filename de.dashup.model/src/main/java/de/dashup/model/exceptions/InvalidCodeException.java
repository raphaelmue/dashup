package de.dashup.model.exceptions;

import de.dashup.shared.layout.Widget;

public class InvalidCodeException extends Exception {
    public InvalidCodeException(Widget widget) {
        super("Widget \"" + widget.getName() + "\" [" + widget.getId() + "] has invalid code!");
    }
}
