import javax.swing.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class StoreServer {
    static String dbfile = "store.db";

    public static void main(String[] args) {
        int port = 1000;

        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            dbfile = args[1];
        }

        try {
            ServerSocket server = new ServerSocket(port);

            System.out.println("Server is listening at port = " + port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                String command = in.nextLine();
                if (command.equals("GET PURCHASE")) {
                    String str = in.nextLine();
                    System.out.println("GET purchase with id = " + str);
                    int purchaseID = Integer.parseInt(str);

                    Connection conn = null;
                    try {
                        String url = "jdbc:sqlite:" + dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM purchases WHERE purchaseid = " + purchaseID;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            out.println(rs.getInt("customerid"));
                            out.println(rs.getInt("productid"));
                            out.println(rs.getString("date"));
                            out.println(rs.getInt("quantity"));
                            out.println(rs.getDouble("price"));
                            out.println(rs.getDouble("tax"));
                            out.println(rs.getDouble("total_cost"));
                        }
                        else out.println("null");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();
                }
                else if (command.equals("PUT PURCHASE")) {
                    String id = in.nextLine();  // read all information from client
                    String customerid = in.nextLine();
                    String productid = in.nextLine();
                    String date = in.nextLine();
                    String quantity = in.nextLine();
                    String price = in.nextLine();
                    String tax = in.nextLine();
                    String total_cost = in.nextLine();

                    System.out.println("PUT command with purchaseid = " + id);

                    Connection conn = null;
                    try {
                        String url = "jdbc:sqlite:" + dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM purchases WHERE purchaseid = " + id;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            rs.close();
                            stmt.execute("DELETE FROM purchases WHERE purchaseid = " + id);
                        }

                        sql = "INSERT INTO purchases VALUES (" + id + "," + customerid + "," + productid + ", \"" + date + "\"," + quantity + "," + price + "," + tax + "," + total_cost + ")";
                        System.out.println("SQL for PUT: " + sql);
                        if (!stmt.execute(sql)) out.println("Purchase added successfully!");
                        else out.println("Error adding purchase!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();
                }
                else if (command.equals("GET PRODUCT")) {
                    String str = in.nextLine();
                    System.out.println("GET product with id = " + str);
                    int productID = Integer.parseInt(str);

                    Connection conn = null;
                    try {
                        String url = "jdbc:sqlite:" + dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM products WHERE productid = " + productID;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            out.println(rs.getString("name")); // send back product name!
                            out.println(rs.getDouble("price")); // send back product price!
                            out.println(rs.getDouble("quantity")); // send back product quantity!
                        }
                        else
                            out.println("null");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();
                }
                else if (command.equals("PUT PRODUCT")) {
                    String id = in.nextLine();  // read all information from client
                    String name = in.nextLine();
                    String price = in.nextLine();
                    String quantity = in.nextLine();

                    System.out.println("PUT command with productid = " + id);

                    Connection conn = null;
                    try {
                        String url = "jdbc:sqlite:" + dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM products WHERE productid = " + id;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            rs.close();
                            stmt.execute("DELETE FROM products WHERE productid = " + id);
                        }

                        sql = "INSERT INTO products VALUES (" + id + ",\"" + name + "\"," + price + "," + quantity + ")";
                        System.out.println("SQL for PUT: " + sql);
                        if (!stmt.execute(sql)) out.println("Product added successfully!");
                        else out.println("Error adding product!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();
                }
                if (command.equals("GET CUSTOMER")) {
                    String str = in.nextLine();
                    System.out.println("GET Customer with id = " + str);
                    int customerID = Integer.parseInt(str);

                    Connection conn = null;
                    try {
                        String url = "jdbc:sqlite:" + dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM customers WHERE customerid = " + customerID;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            out.println(rs.getString("name")); // send back Customer name!
                            out.println(rs.getString("address")); // send back Customer price!
                            out.println(rs.getString("phone")); // send back Customer quantity!
                        }
                        else
                            out.println("null");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();
                }
                else if (command.equals("PUT CUSTOMER")) {
                    String id = in.nextLine();  // read all information from client
                    String name = in.nextLine();
                    String address = in.nextLine();
                    String phone = in.nextLine();

                    System.out.println("PUT command with customerid = " + id);

                    Connection conn = null;
                    try {
                        String url = "jdbc:sqlite:" + dbfile;
                        conn = DriverManager.getConnection(url);

                        String sql = "SELECT * FROM customers WHERE customerid = " + id;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                        if (rs.next()) {
                            rs.close();
                            stmt.execute("DELETE FROM customers WHERE customerid = " + id);
                        }
                        
                        sql = "INSERT INTO customers VALUES (" + id + ",\"" + name + "\", \"" + address + "\", \"" + phone + "\")";
                        System.out.println("SQL for PUT: " + sql);
                        if (!stmt.execute(sql)) out.println("Customer added successfully!");
                        else out.println("Error adding customer!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    conn.close();
                }
                else {
                    out.println(0); // logout unsuccessful!
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}