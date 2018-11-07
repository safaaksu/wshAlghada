package com.example.abodi.wshalghada;

public class IngredientCategory {
    private String ID;
    private String Name;
    // object IngredientCategory
    public IngredientCategory(String ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
