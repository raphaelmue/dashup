package de.dashup.shared;

import java.util.Objects;

public class Tag extends DatabaseTag {

    public Tag(int id, String text) {
        super(id, text);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DatabaseTag && ((DatabaseTag) obj).getText().equals(this.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getText());
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
