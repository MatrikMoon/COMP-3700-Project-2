import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AddProductUI {
    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            try {
                product.mProductID = Integer.parseInt(productID.getText());
                product.mName = name.getText();
                product.mQuantity = Float.parseFloat(quantity.getText());
                product.mPrice = Integer.parseInt(price.getText());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers / values");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("PUT PRODUCT");
                output.println(product.mProductID);
                output.println(product.mName);
                output.println(product.mPrice);
                output.println(product.mQuantity);

                String message = input.nextLine();
                JOptionPane.showMessageDialog(null, message);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            switch (StoreManager.getInstance().getDataAdapter().saveProduct(product)) {
//                case SQLiteDataAdapter.DUPLICATE_ERROR:
//                    JOptionPane.showMessageDialog(null, "Product NOT added successfully! Duplicate product ID!");
//                    break;
//                default:
//                    JOptionPane.showMessageDialog(null, "Product added successfully!" + product);
//            }
        }
    }

    public JFrame view;

    public JButton addButton = new JButton("Add");
    public JButton cancelButton = new JButton("Cancel");

    public JTextField productID = new JTextField(20);
    public JTextField name = new JTextField(20);
    public JTextField price = new JTextField(20);
    public JTextField quantity = new JTextField(20);

    public AddProductUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Product");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel l1 = new JLabel("ProductID", JLabel.TRAILING);
        JPanel p1 = new JPanel(new FlowLayout());
        view.getContentPane().add(p1);
        p1.add(l1);
        l1.setLabelFor(productID);
        p1.add(productID);

        JLabel l2 = new JLabel("Name", JLabel.TRAILING);
        JPanel p2 = new JPanel(new FlowLayout());
        view.getContentPane().add(p2);
        p2.add(l2);
        l2.setLabelFor(name);
        p2.add(name);

        JLabel l3 = new JLabel("Price", JLabel.TRAILING);
        JPanel p3 = new JPanel(new FlowLayout());
        view.getContentPane().add(p3);
        p3.add(l3);
        l3.setLabelFor(price);
        p3.add(price);

        JLabel l4 = new JLabel("Quantity", JLabel.TRAILING);
        JPanel p4 = new JPanel(new FlowLayout());
        view.getContentPane().add(p4);
        p4.add(l4);
        l4.setLabelFor(quantity);
        p4.add(quantity);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        view.getContentPane().add(buttonPanel);

        addButton.addActionListener(new AddButtonListener());

        cancelButton.addActionListener((e) -> view.dispose());

    }

    public void run() {
        view.setVisible(true);
    }
}