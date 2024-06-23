package com.dejavu.utopia.bean;

public class BillType {
    private String name;
    private int iconNormal;
    private int iconSelected;

    public BillType(String name, int iconNormal, int iconSelected) {
        this.name = name;
        this.iconNormal = iconNormal;
        this.iconSelected = iconSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconNormal() {
        return iconNormal;
    }

    public void setIconNormal(int iconNormal) {
        this.iconNormal = iconNormal;
    }

    public int getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(int iconSelected) {
        this.iconSelected = iconSelected;
    }
}
