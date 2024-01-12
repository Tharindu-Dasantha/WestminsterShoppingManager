package main.ShoppingManager;

import main.GUI.ShoppingManagerGui;
import main.Product.Clothing;
import main.Product.Electronic;
import main.Product.Product;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.FloatBuffer;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{

    private List<Product> products = new ArrayList<>();
    int limit = 50;

//    Getting the input
    Scanner scanner = new Scanner(System.in);

    public List<Product> getProductsData() {
        return products;
    }

    public static void main(String[] args) throws FileNotFoundException {
        WestminsterShoppingManager shop = new WestminsterShoppingManager();
        shop.loadFile();
        shop.startConsoleMenu();
    }

   public void startConsoleMenu() throws FileNotFoundException {
       while (true) {
           System.out.println("\nWestminster Shopping Manager Menu:");
           System.out.println("1. Add a new product");
           System.out.println("2. Delete a product");
           System.out.println("3. Print the list of products");
           System.out.println("4. Save product list to file");
           System.out.println("5. Open The GUI");
           System.out.println("6. Exit the Application");
           System.out.print("Enter your choice: ");

           int choice;

           while (true) {
               try {
                   choice = scanner.nextInt();
                   scanner.nextLine();
                   break;
               } catch (InputMismatchException e) {
                   System.out.println("Invalid Input. Please enter an integer.");
                   scanner.nextLine();
               }
           }

           switch (choice) {
               case 1:
                   addProduct();
                   break;
               case 2:
                   removeProduct();
                   break;
               case 3:
                   PrintProducts();
                   break;
               case 4:
                   saveFile();
                   break;
               case 5:
                   new Thread(() -> {
                       SwingUtilities.invokeLater(() -> {
                           ShoppingManagerGui.main(new String[]{});
                       });
                   }).start();
                   break;
               case 6:
                   System.out.println("Exiting...");
                   System.exit(0);
               default:
                   System.out.println("Invalid choice. Please try again.");
           }
       }
   }

    //    Methods for managing products
    @Override
    public void addProduct() {
//        Create the object
        int answer, AvailableItems, WarrantyPeriod;
        String ProductName, ProductID, ProductBrand, Color, Size;
        double ProductPrice;

            while (true) {
                System.out.println("Enter 1 for Electronics \nEnter 2 for Clothing.");
                System.out.print("Enter the choice: ");
                try{
                    answer = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input. Please enter an integer.");
                    scanner.nextLine();
                }
            }

            switch (answer) {
                case 1:
//                  Adding electronic product
                    System.out.println("Welcome to the Electronic Section.");
//                    Getting the information

                    while (true) {
                        System.out.println("Enter the Product ID");
                        ProductID = scanner.nextLine();

                        boolean idFound = false;

                        for (Product newproduct : products) {
                            if(Objects.equals(newproduct.getProductID(), ProductID)) {
                                idFound = true;
                                break;
                            }
                        }
                        if (!idFound) {
                            break;
                        } else {
                            System.out.println("A product with this id exist enter a different ID.");
                        }
                    }


                    System.out.println("Enter the Product Name");
                    ProductName = scanner.nextLine();

                    while (true) {
                        try {
                            System.out.println("Enter the Available amount");
                            AvailableItems = scanner.nextInt();
                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please enter a Number.");
                            scanner.nextLine();
                        }
                    }

                    while (true) {
                        try {
                            System.out.println("Enter the Product Price");
                            ProductPrice = scanner.nextDouble();
                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please enter a Number.");
                            scanner.nextLine();
                        }
                    }

                    System.out.println("Enter the Product Brand");
                    ProductBrand = scanner.nextLine();

                    while (true) {
                        try {
                            System.out.println("Enter the Warranty Period (in months)");
                            WarrantyPeriod = scanner.nextInt();
                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please enter a Number.");
                            scanner.nextLine();
                        }
                    }

//                    Creating the Electronic object
                    Electronic newProduct = new Electronic(ProductID, ProductName, AvailableItems, ProductPrice, ProductBrand, WarrantyPeriod);
                    if (products.size() <= limit) {
                        products.add(newProduct);
                        System.out.println("The " + ProductName + " is added to the storage.");
                    } else {
                        System.out.println("The storage Limit is reached. Please try removing few items.");
                    }

                    break;


                case 2:
//                  Adding clothing product
                    System.out.println("Welcome to the Clothing Section.");
//                    Gathering the Information.

                    System.out.println("Enter the Product ID");
                    ProductID = scanner.nextLine();

                    System.out.println("Enter the Product Name");
                    ProductName = scanner.nextLine();

                    while (true) {
                        try {
                            System.out.println("Enter the Available amount");
                            AvailableItems = scanner.nextInt();
                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please enter a Number.");
                            scanner.nextLine();
                        }
                    }

                    while (true) {
                        try {
                            System.out.println("Enter the Product Price");
                            ProductPrice = scanner.nextDouble();
                            scanner.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please enter a Number.");
                            scanner.nextLine();
                        }
                    }

                    System.out.println("Enter the Cloth Size");
                    Size = scanner.nextLine();

                    System.out.println("Enter the Cloth Color");
                    Color = scanner.nextLine();

//                    Creating the object of Clothing
                    Clothing newCloth = new Clothing(ProductID, ProductName, AvailableItems, ProductPrice, Size, Color);
//                    Adding the crated object to the list
                    if (products.size() <= limit) {
                        products.add(newCloth);
                        System.out.println("The " + ProductName + " is added to the storage.");
                    } else {
                        System.out.println("The storage Limit is reached. Please try removing few items.");
                    }

                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

    }


    @Override
    public void removeProduct() {
        System.out.print("Enter the ID of the product to remove: ");
        String idToRemove = scanner.next();
        boolean objectFount = false;
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product productnew = iterator.next();
            if (Objects.equals(productnew.getProductID(), idToRemove)) {
                System.out.println("The " + productnew.getType() + " product with the ID" + idToRemove + " has removed.");
                iterator.remove();
                objectFount = true;
                return;
            }
        }
        if (!objectFount){
            System.out.println("Product with ID " + idToRemove + "not found.");
        }
    }

    @Override
    public void PrintProducts() {

        products.sort(Comparator.comparing(Product::getProductID));
        for (Product product: products) {
            System.out.println("-------------------Product-------------------");
            System.out.println("ProductID: " + product.getProductID());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Product Price: " + product.getProductPrice());
            System.out.println("Available Amount: " + product.getAvailableItems());
            if (Objects.equals(product.getType(), "Elec")) {
                System.out.println("Product Type: Electric");
                System.out.println("Product Brand: " + ((Electronic) product).getProductBrand());
                System.out.println("Warranty Period: " + ((Electronic) product).getWarrantyPeriod());
            } else  {
                System.out.println("Product Type: Clothing");
                System.out.println("Product Size: " + ((Clothing) product).getSize());
                System.out.println("Product Color: " + ((Clothing) product).getColor());
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void saveFile() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter("products.csv")) {
            for (Product product : products) {
//                If its elect type
                if (Objects.equals(product.getType(), "Elec")) {
                    writer.println(product.getProductID() + "," + product.getType() + "," + product.getProductName() + "," + product.getProductPrice() + "," + product.getAvailableItems() + "," + ((Electronic) product).getProductBrand() + "," + ((Electronic) product).getWarrantyPeriod());
                } else {
//                    if it's a clothing type
                    writer.println(product.getProductID() + "," + product.getType() + "," + product.getProductName() + "," + product.getProductPrice() + "," + product.getAvailableItems() + "," + ((Clothing) product).getSize() + "," + ((Clothing) product).getColor());
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void loadFile() {
        Product tmp;
        try (Scanner reader = new Scanner(new File("products.csv"))) {
            reader.useDelimiter(",");
            while (reader.hasNext()) {
                String ID = reader.next().trim();
                String Type = reader.next();
                String Name = reader.next();
                double price = Double.parseDouble(reader.next());
                int amount = Integer.parseInt(reader.next());
                if (Objects.equals(Type, "Elec")) {
                    String Brand = reader.next();
                    int period = Integer.parseInt(reader.next().trim());
                    tmp = new Electronic(ID, Name, amount, price, Brand, period);
                } else {
                    String size = reader.next();
                    String color = reader.next();
                    tmp = new Clothing(ID, Name, amount, price, size, color);
                }

//                Adding the values to the list
                products.add(tmp);
                System.out.println("Loaded " + tmp.getProductName());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }


//    Adding other methods
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }


}
