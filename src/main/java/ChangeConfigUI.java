import javax.swing.*;
import java.awt.*;

public class ChangeConfigUI {
    public JFrame view;

    public JButton okButton = new JButton("OK");
    public JButton closeButton = new JButton("Close");

    public ChangeConfigUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Change System Config");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.add(closeButton);
        view.getContentPane().add(buttonPanel);

        closeButton.addActionListener((e) -> view.dispose());
    }

    public void run() {
        view.setVisible(true);
    }
}