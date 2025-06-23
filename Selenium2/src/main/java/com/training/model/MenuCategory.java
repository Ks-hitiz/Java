package com.training.model;

import java.util.List;

public class MenuCategory {
    private String category;
    private List<MenuItems> items;

    public MenuCategory(String category, List<MenuItems> items) {
        this.category = category;
        this.items = items;
    }

    public String getCategory() {
        return category;
    }

    public List<MenuItems> getItems() {
        return items;
    }
}
