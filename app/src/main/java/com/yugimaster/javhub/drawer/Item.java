package com.yugimaster.javhub.drawer;

/**
 * Created by yugimaster on 2018/8/29
 */

public class Item {

    private int iconId;
    private String iconName;
    private String link;

    public Item() {
    }

    public Item(int iconId, String iconName, String link) {
        this.iconId = iconId;
        this.iconName = iconName;
        this.link = link;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
