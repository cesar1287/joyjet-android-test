package com.joyjet.digitalspace.controller.domain;

import java.io.Serializable;

public class Article implements Serializable{

    private String category, title, description, descriptionMainActivity, url_banner;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDescriptionMainActivity() {
        return descriptionMainActivity;
    }

    public void setDescriptionMainActivity(String descriptionMainActivity) {
        this.descriptionMainActivity = descriptionMainActivity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlBanner() {
        return url_banner;
    }

    public void setUrlBanner(String url_banner) {
        this.url_banner = url_banner;
    }
}
