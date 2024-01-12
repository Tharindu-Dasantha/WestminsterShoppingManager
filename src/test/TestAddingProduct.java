package test;

import main.Product.Electronic;
import main.Product.Product;
import main.ShoppingManager.ShoppingManager;
import main.ShoppingManager.WestminsterShoppingManager;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestAddingProduct {

    private WestminsterShoppingManager Shop;
    private List<Product> products;
    private int realLimit = 50;
    private int TestLimit = 5;

//    Setting up the project
    @Before
    public void setUp() {
        Shop = new WestminsterShoppingManager();
//        products = ShoppingManager.getProducts();
    }

    @Test
    public void testAddingElectronicProductSuccessfully() {
//        Simulate user input for product details
        String simulatedUserInput = "1\n001\nElectronic Product 1\n10\n499.99\nBrand X\n12\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        System.out.println("the byte array is completed.");

        // Create a new Scanner object to read from the ByteArrayInputStream
        Scanner scanner = new Scanner(System.in);

//        Call the method to test
        Shop.setScanner(scanner);
        Shop.addProduct();

//        Assert the storage limit to safety
        assertTrue(realLimit >= products.size());

        // Assert expected behavior
        assertEquals(1, products.size());
        Electronic addedProduct = (Electronic) products.getFirst();
        assertEquals(1, addedProduct.getProductID());
        assertEquals("Electronic Product 1", addedProduct.getProductName());
        assertEquals(10, addedProduct.getAvailableItems());
        assertEquals(499.99, addedProduct.getProductPrice(), 0.01);
        assertEquals("Brand X", addedProduct.getProductBrand());
        assertEquals(12, addedProduct.getWarrantyPeriod());

        // Reset System.in
        System.setIn(System.in);
    }
}
