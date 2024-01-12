package main.Product;

public class Electronic extends Product {

//    Variables
    private String productBrand;
    private int warrantyPeriod;

//    Constructor
    public Electronic(String productID, String productName, int availableItems, double productPrice, String productBrand, int warrantyPeriod) {
        super(productID, productName, availableItems, productPrice, "Elec");
        this.productBrand = productBrand;
        this.warrantyPeriod = warrantyPeriod;
    }

//    Getters and Setters
    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

//    Methods
    @Override
    public void displayDetails() {
        System.out.println("Electronic");
        System.out.println("Name: " + getProductName());
        System.out.println("Price: " + getProductPrice());
        System.out.println("Brand: " + productBrand);
    }
}
