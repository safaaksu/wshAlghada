package com.example.abodi.wshalghada;

public class Ingredient {
    private String ID;
    private String Name;
    private String CategoryID;
    private double Number;
    private String Unit;

    public Ingredient() {
    }
    // object Ingredient to contain table

    public Ingredient(String ID, String categoryID, double number, String unit) {
        this.ID = ID;
        CategoryID = categoryID;
        Number = number;
        Unit = unit;
    }

    public Ingredient(String ID, String name, String categoryID) {
        this.ID = ID;
        Name = name;
        CategoryID = categoryID;
    }

    public double getNumber() {
        return Number;
    }

    public void setNumber(double number) {
        Number = number;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
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

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
