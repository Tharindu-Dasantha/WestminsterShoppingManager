package main.ShoppingCart;

//Importing the abstract Product class to help with the calculations
import main.Product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private HashMap <Product, Integer> Products;

    public ShoppingCart() {
        Products = new HashMap<>();
    }

//    Methods to add remove and calculate the total

//    Adding

//    Adding multiple amounts from the same product to the list
    public void add(Product product, int amount) {
//        if the product exist in the list
        if (Products.containsKey(product)) {
//            Increase the amount by the given amount
            Integer alreadyInAmount = Products.get(product);
            Integer newAmount = alreadyInAmount + amount;
//            Replace the old value
            Products.put(product, newAmount);
        }
//        if the product is new
        Products.put(product, amount);
    }

//    Adding single product to the list
    public void add(Product product) {
//        If the product exist in the list
        if (Products.containsKey(product)) {
//            increase the amount by 1
            Integer alreadyInAmount = Products.get(product);
            Integer newAmount = alreadyInAmount + 1;
//            Replace the new amount
            Products.put(product, newAmount);
        } else {
//            If this is a brand-new product
            Products.put(product, 1);
        }
    }

//    Removing

//    Remove a single item from a product
    public String removeOne(Product product) {
//        Check if the product is in the list or not
        if (Products.containsKey(product)) {
            Integer OldAmount = Products.get(product);
            Integer NewAmount = OldAmount - 1;
//            Update the hashmap
            Products.put(product, NewAmount);
            return ("Amount of " + product + "decreased by 1.");
        } else {
            return ("There is no " + product + "in the cart.");
        }
    }

//    Remove entire product from the cart
    public String removeAll(Product product) {
//        Check if the product is in the list
        if (Products.containsKey(product)) {
            Products.remove(product);
            return (product + " is removed from the cart.");
        } else {
            return ("There is no " + product + " in the cart.");
        }
    }

//    Calculate the Total for the cart
    public int calculateTotalCart() {
        int total = 0;
        for (Map.Entry<Product, Integer> entry : Products.entrySet()) {
            int itemTotal = calculateItemTotal(entry.getKey());
            total += itemTotal;
        }
        return total;
    }

//    Calculate the Total for each item
    public int calculateItemTotal(Product product) {
        double sum;
//        Check if the item is in the list or not
        if (Products.containsKey(product)) {
            double pricePerOne = product.getProductPrice();
            sum = pricePerOne * Products.get(product);
            return (int) sum;
        } else {
            return 0;
        }
    }
}
