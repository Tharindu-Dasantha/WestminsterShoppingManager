package main.GUI;

import main.Product.Clothing;
import main.Product.Electronic;
import main.Product.Product;
import main.ShoppingManager.WestminsterShoppingManager;
import main.User.User;
import main.User.UserManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Objects;


public class ShoppingManagerGui  extends JFrame{

//    Variables for the GUI
    private WestminsterShoppingManager Shop = new WestminsterShoppingManager();
    private DefaultTableModel tableModel;
    private JTextArea detailsTextArea;
    private ShoppingCartGUI shoppingCartGUI;
    private JTable productsTable;
    private UserManager userManager = new UserManager();
    String user_name;
    User user;
    private boolean userRegisterd = false;

    private List<Product> dataBase = Shop.getProductsData();

//    Main method
public ShoppingManagerGui() {
    setTitle("Westminster Shopping Manager");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    CreateStructure();
    shoppingCartGUI = new ShoppingCartGUI();
}

    private void CreateStructure() {
//    Dropdown list
        JComboBox<String> productTypeComboBox = new JComboBox<>(new String[]{"All Products", "Electronic Products Only", "Clothing Products Only"});
//        Buttons for the functionalities
        JButton cartButton = new JButton("Shopping Cart");
        JButton addToCartButton = new JButton("Add to Cart");

//        Create a table to showcase the products
        productsTable = new JTable();
        CreateTable(productsTable);

//        Add a text area to show the details of the selected items.
        detailsTextArea = new JTextArea(10, 50);
        detailsTextArea.setEditable(false);

//        Top panel with buttons and selection (Dropdown)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
//        Adding the dropdown selection
        JLabel productCategory = new JLabel("Select Product Category");
        topPanel.add(productCategory);
        topPanel.add(productTypeComboBox);
//        Adding the buttons
        topPanel.add(cartButton);

//        Creating a scrolling pane for the table
        JScrollPane tableScrollPane = new JScrollPane(productsTable);

//        Adding the table to the center of the application with borders on the side
        JPanel tablePanel = new JPanel(new BorderLayout());
        // For visual spacing on the sides:
        tablePanel.add(Box.createHorizontalStrut(50), BorderLayout.WEST);
        tablePanel.add(Box.createHorizontalStrut(50), BorderLayout.EAST);
//        Add the table to the center
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

//        Create a panel to add the detail list and the add to cart button
        JPanel bottomPanel = new JPanel();
        JScrollPane textAreaScrollPane = new JScrollPane(detailsTextArea);
        textAreaScrollPane.setColumnHeaderView(new JLabel("Product Detail"));
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(textAreaScrollPane, BorderLayout.CENTER);
//        Adding spacing to the sides to make it look nice
        bottomPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        bottomPanel.add(Box.createHorizontalStrut(50), BorderLayout.WEST);
        bottomPanel.add(Box.createHorizontalStrut(50), BorderLayout.EAST);

//        Creating a panel to center the add to cart button
        JPanel addToCartButtonPanel = new JPanel(new BorderLayout());
        addToCartButtonPanel.add(addToCartButton, BorderLayout.CENTER);
//        Creating spaces
        addToCartButtonPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        addToCartButtonPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        addToCartButtonPanel.add(Box.createHorizontalStrut(300), BorderLayout.WEST);
        addToCartButtonPanel.add(Box.createHorizontalStrut(300), BorderLayout.EAST);

        bottomPanel.add(addToCartButtonPanel, BorderLayout.SOUTH);


        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);



//        Setting the functionalities

//        Displaying the selected items details
        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    displayItems(selectedRow);
                }
            }
        });

//        Adding an item to cart
        //            Checking for user registrations
        addToCartButton.addActionListener(e -> {
                        String UserName = user_name;
                        System.out.println(UserName);

                        if (!userManager.isRegistered(UserName)) {
                            registerUser();
                        }

        //                Add the item to the cart
                        int selectedRow = productsTable.getSelectedRow();
                        if (selectedRow >= 0) {
                            Product selectedProduct = Shop.getProducts().get(selectedRow);
        //                    Add the item to the cart and handle the database
                            AddingtotheCart(selectedProduct);

                        }
                    });

        cartButton.addActionListener(e -> shoppingCartGUI.setVisible(true));

//        Adding an event listener to identify when the dropdown changes
        productTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    int selectedIndex = productTypeComboBox.getSelectedIndex();
                    Object selectedItem = productTypeComboBox.getSelectedItem();

                    if (selectedIndex == 0) {
                        for (int i = 0; i < productsTable.getRowCount(); i++) {
                            tableModel.removeRow(i);
                        }
                        addProductsToTable();
                    } else if (selectedIndex == 1) {
                        tableModel.setRowCount(0);
                        addElectricProductsToTable();
                    } else {
                        tableModel.setRowCount(0);
                        addClothingProductsToTable();
                    }
                }
            }
        });

        addProductsToTable();
    }

    private void addClothingProductsToTable() {
        List<Product> products = dataBase;
        tableModel.setRowCount(0);

        for (Product product: products) {
            Object[] row = {
                    product.getProductID(),
                    product.getProductName(),
                    product.getAvailableItems(),
                    product.getProductPrice(),
                    product.getType()
            };
            if (Objects.equals(product.getType(), "Cloth")) {
                tableModel.addRow(row);
            }
        }
    }

    private void addElectricProductsToTable() {
        List<Product> products = dataBase;
        tableModel.setRowCount(0);

        for (Product product: products) {
            Object[] row = {
                    product.getProductID(),
                    product.getProductName(),
                    product.getAvailableItems(),
                    product.getProductPrice(),
                    product.getType()
            };
            if (Objects.equals(product.getType(), "Elec")) {
                tableModel.addRow(row);
            }
        }
    }

    private void CheckTheTable() {
//    Checking the row in table and changing the color of the column
    }

    private void AddingtotheCart(Product productToAdd) {


//        Changing the table
//        Getting the row number to change
        int rowNumber = RowNumber(productToAdd.getProductID());
        if (rowNumber == -1) {
            System.out.println("No element in this name cant add");
            return;
        } else {
            if (productToAdd.getAvailableItems() == 0) {
                return;
            }
//            Change the new value
//        Reduce the amount of available items in the selected type
            int newAmount = productToAdd.getAvailableItems() - 1;
            productToAdd.setAvailableItems(newAmount);
            productsTable.getModel().setValueAt(newAmount,rowNumber,2);
            shoppingCartGUI.addItemToCart(productToAdd, user);
            displayItems(rowNumber);
        }
    }

//    Getting the row number of the removing item
    private int RowNumber(String ID) {
        int rowCount = tableModel.getRowCount();
        for (int rowIndex=0; rowIndex < rowCount; rowIndex++) {
            Object cellValue = productsTable.getModel().getValueAt(rowIndex, 0);
            if (cellValue.equals(ID)) {
                return rowIndex;
            }
        }
        return -1;
    }

    //    Add items to the table
    private void addProductsToTable() {
        List<Product> products = Shop.getProducts();
        tableModel.setRowCount(0);

        for (Product product: products) {
            Object[] row = {
                    product.getProductID(),
                    product.getProductName(),
                    product.getAvailableItems(),
                    product.getProductPrice(),
                    product.getType()
            };
            tableModel.addRow(row);
        }

//        Change the table based on the dropdown menu item selected.
    }

    //    Creating the Table
    public void CreateTable(JTable productsTable) {
        String[] Headers = {"Product ID", "Product Name", "Available Items", "Price", "Product Type"};
        tableModel = new DefaultTableModel(Headers, 0);
        productsTable.setModel(tableModel);
    }

//    Detailed Box
    private void displayItems(int Index) {
        Product selectedProduct = Shop.getProducts().get(Index); // if not check and get the alphabetical order of the ID Check
        if (Objects.equals(selectedProduct.getType(), "Elec")) {
            detailsTextArea.setText(
                    "Product ID: " + selectedProduct.getProductID() +
                            "\nProduct Name: " + selectedProduct.getProductName() +
                            "\nAvailable Amount: " + selectedProduct.getAvailableItems() +
                            "\nItem Price: " + selectedProduct.getProductPrice() +
                            "\nProduct Type: " + selectedProduct.getType() +
                            "\nProduct Brand: " + ((Electronic) selectedProduct).getProductBrand() +
                            "\nWarranty Period: " + ((Electronic) selectedProduct).getWarrantyPeriod() + " Months."
            );
        } else {
            detailsTextArea.setText(
                    "Product ID: " + selectedProduct.getProductID() +
                            "\nProduct Name: " + selectedProduct.getProductName() +
                            "\nAvailable Amount: " + selectedProduct.getAvailableItems() +
                            "\nItem Price: " + selectedProduct.getProductPrice() +
                            "\nProduct Type: " + selectedProduct.getType() +
                            "\nProduct Size: " + ((Clothing) selectedProduct).getSize() +
                            "\nProduct Color: " + ((Clothing) selectedProduct).getColor()
            );
        }
    }

//    Registering users
    public void registerUser() {
        String UserName = user_name;

        // Check if the user is already registered
        if (!userManager.isRegistered(UserName)) {

            JTextField usernameField = new JTextField(5);
            JPasswordField passwordField = new JPasswordField(5);

            JPanel registeringForm = new JPanel();
            registeringForm.add(new JLabel("Username:"));
            registeringForm.add(usernameField);
            registeringForm.add(Box.createHorizontalStrut(15));
            registeringForm.add(new JLabel("Password:"));
            registeringForm.add(passwordField);

            int result = JOptionPane.showConfirmDialog(null, registeringForm,
                    "Please Enter Username and Password", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                user = new User(usernameField.getText(), new String(passwordField.getPassword()));
                userManager.registeringUser(user);
                user_name = usernameField.getText();
            }
        } else {
            // User is already registered, no need to show the registration form
            userRegisterd = true;
        }
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ShoppingManagerGui().setVisible(true));
    }
}
