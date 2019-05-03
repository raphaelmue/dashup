package de.dashup.shared;

public class Draft extends Widget {
    @Override
    public boolean isVisible() {
        return true;
    }

    /**
     * Checks whether all attributes are set.
     *
     * @return true, if draft is valid
     */
    public boolean isValid() {
        return !(this.isNullOrEmpty(this.getName()) ||
                this.isNullOrEmpty(this.getDescription()) ||
                this.isNullOrEmpty(this.getShortDescription()) ||
                this.isNullOrEmpty(this.getCode(Size.SMALL)) ||
                this.isNullOrEmpty(this.getCode(Size.MEDIUM)) ||
                this.isNullOrEmpty(this.getCode(Size.LARGE)));
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    @Override
    public Draft fromDatabaseObject(DatabaseObject databaseObject) {
        return (Draft) super.fromDatabaseObject(databaseObject);
    }
}
