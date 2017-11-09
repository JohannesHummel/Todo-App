package com.example.johanneshummel.checklist;

import java.io.Serializable;

/**
 * Created by johanneshummel on 02.06.17.
 */

public class Element implements Serializable {
    private String title;
    private String description;

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
