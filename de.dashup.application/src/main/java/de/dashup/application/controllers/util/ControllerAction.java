package de.dashup.application.controllers.util;

import java.sql.SQLException;

public interface ControllerAction<T> {
    void action(T user) throws SQLException;
}
