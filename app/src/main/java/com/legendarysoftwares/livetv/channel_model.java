package com.legendarysoftwares.livetv;

public class channel_model {
    String category,name,icon,link;


    channel_model(){}

    public channel_model(String category, String name, String icon, String link) {
        this.category = category;
        this.name = name;
        this.icon = icon;
        this.link = link;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }
    public void setM3u_link(String link) {
        this.link = link;
    }



}
