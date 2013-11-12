package org.karolgurecki.autotask.ui.rows;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class ItemRow {
    private String title;
    private String description;

    public ItemRow(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return title + " " + description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
