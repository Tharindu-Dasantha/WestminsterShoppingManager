package main.Product;

public class Clothing extends Product{

//    Variables
    private String Size;
    private String Color;

//    Constructors

    public Clothing(String productID, String productName, int availableItems, double productPrice, String size, String color) {
        super(productID, productName, availableItems, productPrice, "Cloth");
        Size = size;
        Color = color;
    }

//    Getters and Setters

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

//    Methods


    @Override
    public void displayDetails() {
        System.out.println("Clothing");
    }
}
