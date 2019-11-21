import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AddCustomerUI {
    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            try {
                customer.mCustomerID = Integer.parseInt(customerID.getText());
                customer.mName = name.getText();
                customer.mPhone = phone.getText();
                customer.mAddress = address.getText();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers / values");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("PUT CUSTOMER");
                output.println(customer.mCustomerID);
                output.println(customer.mName);
                output.println(customer.mAddress);
                output.println(customer.mPhone);

                String message = input.nextLine();
                JOptionPane.showMessageDialog(null, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JFrame view;

    public JButton addButton = new JButton("Add");
    public JButton cancelButton = new JButton("Cancel");

    JTextField customerID = new JTextField(10);
    JTextField name = new JTextField(10);
    JTextField address = new JTextField(10);
    JTextField phone = new JTextField(10);

    public AddCustomerUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel l1 = new JLabel("CustomerID", JLabel.TRAILING);
        JPanel p1 = new JPanel(new FlowLayout());
        view.getContentPane().add(p1);
        p1.add(l1);
        l1.setLabelFor(customerID);
        p1.add(customerID);

        JLabel l2 = new JLabel("Name", JLabel.TRAILING);
        JPanel p2 = new JPanel(new FlowLayout());
        view.getContentPane().add(p2);
        p2.add(l2);
        l2.setLabelFor(name);
        p2.add(name);

        JLabel l3 = new JLabel("Address", JLabel.TRAILING);
        JPanel p3 = new JPanel(new FlowLayout());
        view.getContentPane().add(p3);
        p3.add(l3);
        l3.setLabelFor(address);
        p3.add(address);

        JLabel l4 = new JLabel("Phone", JLabel.TRAILING);
        JPanel p4 = new JPanel(new FlowLayout());
        view.getContentPane().add(p4);
        p4.add(l4);
        l4.setLabelFor(phone);
        p4.add(phone);

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