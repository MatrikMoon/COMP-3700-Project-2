import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AddPurchaseUI {
    ProductModel product = new ProductModel();
    CustomerModel customer = new CustomerModel();
    PurchaseModel purchase = new PurchaseModel();

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                purchase.mPurchaseID = Integer.parseInt(purchaseId.getText());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid values");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("PUT PURCHASE");
                output.println(purchase.mPurchaseID);
                output.println(purchase.mCustomerID);
                output.println(purchase.mProductID);
                output.println(purchase.mDate);
                output.println(purchase.mQuantity);
                output.println(purchase.mPrice);
                output.println(purchase.mTax);
                output.println(purchase.mTotal);

                String message = input.nextLine();
                JOptionPane.showMessageDialog(null, message);

                if (message.equals("Purchase added successfully!")) {
                    TXTReceiptBuilder builder = new TXTReceiptBuilder();
                    builder.appendHeader("---THE STORE---");
                    builder.appendCustomer(customer);
                    builder.appendPurchase(purchase);
                    builder.appendFooter("---THANK YOU FOR SHOPPING WITH THE STORE---");
                    JOptionPane.showMessageDialog(null, builder.toString());

                    try {
                        File file = new File("receipt-" + purchaseId.getText() + ".txt");
                        file.createNewFile();
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter writer = new BufferedWriter(fw);
                        writer.write(builder.toString());

                        writer.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ProductIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = productId.getText();

            if (s.length() == 0) {
                labProductName.setText("Product Name: [not specified!]");
                return;
            }

            try {
                purchase.mProductID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("GET PRODUCT");
                output.println(purchase.mProductID);

                product.mProductID = purchase.mProductID;
                product.mName = input.nextLine();

                if (product.mName.equals("null")) {
                    return;
                }

                product.mPrice = input.nextDouble();
                product.mQuantity = input.nextDouble();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labProductName.setText("Product Name: ");

                return;
            }

            labProductName.setText("Product Name: " + product.mName);
            labPrice.setText("Product Price: " + product.mPrice);
        }
    }

    private class PurchaseIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();

            purchase.mDate = dateFormat.format(date);

            labDate.setText("Purchase Date: " + purchase.mDate);
        }
    }

    private class CustomerIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = customerId.getText();

            if (s.length() == 0) {
                labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            try {
                purchase.mCustomerID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("GET CUSTOMER");
                output.println(purchase.mCustomerID);

                customer.mCustomerID = purchase.mCustomerID;
                customer.mName = input.nextLine();

                if (customer.mName.equals("null")) {
                    return;
                }

                customer.mAddress = input.nextLine();
                customer.mPhone = input.nextLine();

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labCustomerName.setText("Customer Name: ");

                return;
            }

            labCustomerName.setText("Customer Name: " + customer.mName);
        }
    }

    private class QuantityChangeListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            process();
        }

        public void removeUpdate(DocumentEvent e) {
            process();
        }

        public void insertUpdate(DocumentEvent e) {
            process();
        }

        private void process() {
            String s = quantity.getText();

            try {
                purchase.mQuantity = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter a valid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter a valid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity > product.mQuantity) {
                JOptionPane.showMessageDialog(null,
                        "Not enough available products!", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            purchase.mPrice = purchase.mQuantity * product.mPrice;
            purchase.mTax = purchase.mPrice * 0.09;
            purchase.mTotal = purchase.mPrice + purchase.mTax;

            labCost.setText("Cost: $" + String.format("%8.2f", purchase.mPrice).trim());
            labTax.setText("Tax: $" + String.format("%8.2f", purchase.mTax).trim());
            labTotalCost.setText("Total: $" + String.format("%8.2f", purchase.mTotal).trim());
        }
    }

    public JFrame view;

    public JButton addButton = new JButton("Add");
    public JButton closeButton = new JButton("Close");

    JTextField purchaseId = new JTextField(10);
    JTextField productId = new JTextField(10);
    JTextField customerId = new JTextField(10);
    JTextField quantity = new JTextField(10);

    public JLabel labCost = new JLabel("Cost: $0.00 ");
    public JLabel labTax = new JLabel("Tax: $0.00");
    public JLabel labTotalCost = new JLabel("Total Cost: $0.00");

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Date of Purchase: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");

    public AddPurchaseUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel l1 = new JLabel("PurchaseID", JLabel.TRAILING);
        JPanel p1 = new JPanel(new FlowLayout());
        view.getContentPane().add(p1);
        p1.add(l1);
        l1.setLabelFor(purchaseId);
        p1.add(purchaseId);
        p1.add(labDate);

        JLabel l2 = new JLabel("ProductID", JLabel.TRAILING);
        JPanel p2 = new JPanel(new FlowLayout());
        view.getContentPane().add(p2);
        p2.add(l2);
        l2.setLabelFor(productId);
        p2.add(productId);
        p2.add(labProductName);

        JLabel l3 = new JLabel("CustomerID", JLabel.TRAILING);
        JPanel p3 = new JPanel(new FlowLayout());
        view.getContentPane().add(p3);
        p3.add(l3);
        l3.setLabelFor(customerId);
        p3.add(customerId);
        p3.add(labCustomerName);

        JLabel l4 = new JLabel("Quantity", JLabel.TRAILING);
        JPanel p4 = new JPanel(new FlowLayout());
        view.getContentPane().add(p4);
        p4.add(l4);
        l4.setLabelFor(quantity);
        p4.add(quantity);
        p4.add(labPrice);

        JPanel p5 = new JPanel(new FlowLayout());
        p5.add(labCost);
        p5.add(labTax);
        p5.add(labTotalCost);
        view.getContentPane().add(p5);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(closeButton);
        view.getContentPane().add(buttonPanel);

        purchaseId.addFocusListener(new PurchaseIDFocusListener());
        productId.addFocusListener(new ProductIDFocusListener());
        customerId.addFocusListener(new CustomerIDFocusListener());
        quantity.getDocument().addDocumentListener(new QuantityChangeListener());

        addButton.addActionListener(new AddButtonListener());

        closeButton.addActionListener((e) -> view.dispose());

    }

    public void run() {
        view.setVisible(true);
    }
}