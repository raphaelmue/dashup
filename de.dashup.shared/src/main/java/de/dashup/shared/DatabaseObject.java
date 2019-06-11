package de.dashup.shared;

import java.io.Serializable;

/**
 * This interface helps abstract each Table as an Java Object.
 *
 * @author Raphael Müßeler
 */
public interface DatabaseObject extends Serializable {
    /**
     * Each database entry has an id, which is primary key and autoincrement. This method returns the respective id.
     *
     * @return id
     */
    int getId();

    default DatabaseObject fromDatabaseObject(DatabaseObject databaseObject) {
        return this;
    }
}
