package de.dashup.shared;

/**
 * This interface helps abstract each Table as an Java Object.
 *
 * @author Raphael Müßeler
 */
public interface DatabaseObject {
    /**
     * Each database entry has an id, which is primary key and autoincrement. This method returns the repective id.
     *
     * @return id
     */
    int getId();

    default DatabaseObject fromDatabaseObject(DatabaseObject databaseObject) {
        return this;
    }
}
