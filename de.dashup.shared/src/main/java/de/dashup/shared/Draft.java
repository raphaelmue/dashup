package de.dashup.shared;

public class Draft extends Widget {
    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public Draft fromDatabaseObject(DatabaseObject databaseObject) {
        return (Draft) super.fromDatabaseObject(databaseObject);
    }
}
