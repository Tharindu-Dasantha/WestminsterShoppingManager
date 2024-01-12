package main.Product;

public abstract class Product {

    //    Adding the variables to use in this class
    private String ProductID;
    private String ProductName;
    private int AvailableItems;
    private double ProductPrice;
    private String Type;

//    Constructor of the product class


    public Product(String productID, String productName, int availableItems, double productPrice, String Type) {
        ProductID = productID;
        ProductName = productName;
        AvailableItems = availableItems;
        ProductPrice = productPrice;
        this.Type = Type;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getAvailableItems() {
        return AvailableItems;
    }

    public void setAvailableItems(int availableItems) {
        AvailableItems = availableItems;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    //    Abstract Methods
    public abstract void displayDetails();

}
