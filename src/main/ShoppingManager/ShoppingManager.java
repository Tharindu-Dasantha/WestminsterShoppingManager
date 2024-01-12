package main.ShoppingManager;

import main.Product.Product;

import java.io.FileNotFoundException;

public interface ShoppingManager {
//    Methods to managing products
    void addProduct();
    void removeProduct();
    void PrintProducts();
    void saveFile() throws FileNotFoundException;
    void loadFile();

//    Other methods
}
