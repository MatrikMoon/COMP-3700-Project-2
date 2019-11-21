import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UpdateUserUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load User");
    public JButton btnDelete = new JButton("Delete User");
    public JButton btnSave = new JButton("Save User");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtFullName = new JTextField(20);
    public JTextField txtUserType = new JTextField(20);
    public JTextField txtCustomerId = new JTextField(20);


    public UpdateUserUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update User Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnDelete);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username "));
        line1.add(txtUsername);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Full Name "));
        line2.add(txtFullName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("User Type "));
        line3.add(txtUserType);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("CustomerID "));
        line4.add(txtCustomerId);
        view.getContentPane().add(line4);


        btnLoad.addActionListener(new LoadButtonListener());

        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            String username = txtUsername.getText();
            user.mUsername = username;

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "username cannot be null!");
                return;
            }

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("GET");
                output.println(user.mUsername);

                user.mFullname = input.nextLine();

                if (user.mFullname.equals("null")) {
                    JOptionPane.showMessageDialog(null, "User does not exist!");
                    return;
                }

                txtFullName.setText(user.mFullname);

                user.mUserType = Integer.parseInt(input.nextLine());
                txtUserType.setText(Integer.toString(user.mUserType));

                user.mCustomerID = Integer.parseInt(input.nextLine());
                txtCustomerId.setText(Integer.toString(user.mCustomerID));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            String username = txtUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "username cannot be null!");
                return;
            }

            String name = txtUsername.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
                return;
            }

            user.mUsername = name;
            user.mFullname = txtFullName.getText();
            user.mCustomerID = Integer.parseInt(txtCustomerId.getText());
            user.mUserType = Integer.parseInt(txtUserType.getText());

            // all Customer info is ready! Send to Server!

            try {
                Socket link = new Socket("localhost", 1000);
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                output.println("PUT");
                output.println(user.mUsername);
                output.println(user.mFullname);
                output.println(user.mCustomerID);
                output.println(user.mUserType);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
