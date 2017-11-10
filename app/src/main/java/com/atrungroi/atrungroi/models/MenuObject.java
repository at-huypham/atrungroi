package com.atrungroi.atrungroi.models;

/**
 * Created by huyphamna.
 */

public class MenuObject {
    private String name;
    private int icon;
    private boolean isChoose;

    public MenuObject(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose() {
        isChoose = !isChoose;
    }
}
