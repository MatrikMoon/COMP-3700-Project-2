import javax.swing.*;
import java.awt.*;

public class MainUI {
    public JFrame view;

    public JButton btnAddProduct = new JButton("Add New Product");
    public JButton btnAddCustomer = new JButton("Add New Customer");
    public JButton btnAddPurchase = new JButton("Add New Purchase");
    public JButton btnUpdateProductUI = new JButton("Update Product Information");
    public JButton btnUpdateCustomerUI = new JButton("Update Customer Information");

    public MainUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont(title.getFont().deriveFont(24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAddProduct);
        panelButtons.add(btnAddCustomer);
        panelButtons.add(btnAddPurchase);
        panelButtons.add(btnUpdateProductUI);
        panelButtons.add(btnUpdateCustomerUI);
        view.getContentPane().add(panelButtons);

        btnAddProduct.addActionListener((e) -> {
            AddProductUI ap = new AddProductUI();
            ap.run();
        });

        btnAddCustomer.addActionListener((e) -> {
            AddCustomerUI ac = new AddCustomerUI();
            ac.run();
        });

        btnAddPurchase.addActionListener((e) -> {
            AddPurchaseUI ap = new AddPurchaseUI();
            ap.run();
        });

        btnUpdateProductUI.addActionListener((e) -> {
            UpdateProductUI ui = new UpdateProductUI();
            ui.run();
        });

        btnUpdateCustomerUI.addActionListener((e) -> {
            UpdateCustomerUI ui = new UpdateCustomerUI();
            ui.run();
        });
    }
}