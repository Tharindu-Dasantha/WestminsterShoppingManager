package main.GUI;

import main.Product.Product;
import main.User.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {

    private DefaultTableModel shoppingCartTableModel;
    private JTextArea totalTextArea;

    private User user;

    public ShoppingCartGUI() {
        setTitle("Shopping Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        CreateComponents();
    }

    private void CreateComponents() {
        // Table for cart items
        shoppingCartTableModel = new DefaultTableModel(new String[]{"Product", "Quantity", "Price", "Type"}, 0);
        JTable cartTable = new JTable(shoppingCartTableModel);
        JScrollPane tableScrollPane = new JScrollPane(cartTable);

        // TextArea for totals and discounts
        totalTextArea = new JTextArea(10, 20);
        totalTextArea.setEditable(false);
        totalTextArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JScrollPane textAreaScrollPane = new JScrollPane(totalTextArea);

        // Layout setup
        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);
        add(textAreaScrollPane, BorderLayout.SOUTH);

        Total();
    }

    public void addItemToCart(Product product, User user) {
        this.user = user;
        // Check if the product is already in the cart
        boolean productExists = false;
        for (int i = 0; i < shoppingCartTableModel.getRowCount(); i++) {
            String existingProductName = (String) shoppingCartTableModel.getValueAt(i, 0);
            if (existingProductName.equals(product.getProductID() + " " + product.getProductName())) {
                // Product exists, update quantity
                int existingQuantity = (Integer) shoppingCartTableModel.getValueAt(i, 1);
                shoppingCartTableModel.setValueAt(existingQuantity + 1, i, 1);
                double newPrice = (Double) shoppingCartTableModel.getValueAt(i,2) + product.getProductPrice();
                shoppingCartTableModel.setValueAt(newPrice, i, 2);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            // Add new product to cart
            shoppingCartTableModel.addRow(new Object[]{product.getProductID() + " " + product.getProductName(), 1, product.getProductPrice(), product.getType()});
        }

        Total();
    }

//    handling the total amount
    private void Total() {
        // Calculating the total amount
        double Total = CalculateTotal();

//        Adding the first user discount
        double fistTimeDiscount=0;
        if (user != null) {
            if (IsFirst(user)) {
                fistTimeDiscount = Total / 10;
            }
        } else {
            fistTimeDiscount = 0;
        }

//        Three times discount
        int elecItemCount = 0;
        int ClothingItemCount = 0;

        double elecItemTotal = 0;
        double clothingItemTotal = 0;

        double elecDiscount = 0;
        double clothingDiscount = 0;

        for (int i = 0; i < shoppingCartTableModel.getRowCount(); i++) {
            if (shoppingCartTableModel.getValueAt(i, 3) == "Elec") {
                elecItemCount += (Integer) shoppingCartTableModel.getValueAt(i, 1);
                elecItemTotal += (Double) shoppingCartTableModel.getValueAt(i, 2);
            } else {
                ClothingItemCount += (Integer) shoppingCartTableModel.getValueAt(i, 1);
                clothingItemTotal += (Double) shoppingCartTableModel.getValueAt(i, 2);
            }

        }

//        Checking for the discount
        if (elecItemCount >= 3) {
            elecDiscount = elecItemTotal * 0.2;
        }
        if (ClothingItemCount >= 3) {
            clothingDiscount = clothingItemTotal * 0.2;
        }

        double finalSameItemDiscount = elecDiscount + clothingDiscount;

        FinalizeTotal(Total, fistTimeDiscount, finalSameItemDiscount);
    }


    private double CalculateTotal() {
        double total = 0;
        for (int i = 0; i < shoppingCartTableModel.getRowCount(); i++) {
            int quantity = (Integer) shoppingCartTableModel.getValueAt(i, 1);
            double price = (Double) shoppingCartTableModel.getValueAt(i, 2);

            total += quantity * price;
        }
        return total;
    }

//    checking if the first purchase or not
    private boolean IsFirst(User user) {
        if (user.getNumberOfPurchases() == 0) {
            return true;
        }
        return false;
    }

    private void FinalizeTotal(double Total, double firstTimeDiscount, double sameItemDiscount) {
        double FinalTotal = Total - firstTimeDiscount - sameItemDiscount;
        totalTextArea.setText(
                "Total: " + String.format("%.2f", Total) +
                "\nFirst Purchase Discount (10%): " + String.format("%.2f", firstTimeDiscount) +
                        "\n Three items in same Category Discount(20%): " + String.format("%.2f", sameItemDiscount) +
                        "\n Final Total: " + String.format("%.2f", FinalTotal)
        );
    }

    // Additional methods for cart management (e.g., remove items, calculate discounts) can be added here
}
