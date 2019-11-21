import javax.swing.*;
import java.awt.*;

public class CancelPurchaseUI {
    CustomerModel customer;

    public JFrame view;

    public JButton cancelButton = new JButton("Cancel Purchase");
    public JButton closeButton = new JButton("Close");

    JTextField purchaseId = new JTextField(10);

    public CancelPurchaseUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Cancel Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel l1 = new JLabel("PurchaseID", JLabel.TRAILING);
        JPanel p1 = new JPanel(new FlowLayout());
        view.getContentPane().add(p1);
        p1.add(l1);
        l1.setLabelFor(purchaseId);
        p1.add(purchaseId);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(cancelButton);
        buttonPanel.add(closeButton);
        view.getContentPane().add(buttonPanel);

        closeButton.addActionListener((e) -> view.dispose());
    }

    public void run() {
        view.setVisible(true);
    }
}