import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAdapter{
    public static final int SAVED_OK = 0;
    public static final int DUPLICATE_ERROR = 1;

    Connection conn = null;

    public int connect(String path) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT productid, name, price, quantity FROM products WHERE productid = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("productid");
            product.mName = rs.getString("name");
            product.mPrice = rs.getDouble("price");
            product.mQuantity = rs.getDouble("quantity");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public int saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO products(productid, name, price, quantity) VALUES " + product;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();

            String msg = e.getMessage();
            System.out.println(msg);

            if (msg.contains("UNIQUE constraint failed"))
                return DUPLICATE_ERROR;
        }
        return SAVED_OK;
    }

    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT customerid, name, phone, address FROM customers WHERE customerid = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("customerid");
            customer.mName = rs.getString("name");
            customer.mPhone = rs.getString("phone");
            customer.mAddress = rs.getString("address");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public int saveCustomer(CustomerModel customer) {
        try {
            String sql = "INSERT INTO customers(customerid, name, phone, address) VALUES " + customer;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();

            String msg = e.getMessage();
            System.out.println(msg);

            if (msg.contains("UNIQUE constraint failed"))
                return DUPLICATE_ERROR;
        }
        return SAVED_OK;
    }

    public int savePurchase(PurchaseModel purchase) {
        try {
            String sql = "INSERT INTO Purchases VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();

            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return DUPLICATE_ERROR;
        }

        return SAVED_OK;
    }

    @Override
    public PurchaseHistoryModel loadPurchaseHistory(int id) {
        PurchaseHistoryModel res = new PurchaseHistoryModel();
        try {
            String sql = "SELECT * FROM purchases WHERE customerid = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mCustomerID = id;
                purchase.mPurchaseID = rs.getInt("purchaseid");
                purchase.mProductID = rs.getInt("productid");
                purchase.mQuantity = rs.getDouble("quantity");
                purchase.mPrice = rs.getDouble("price");
                purchase.mTax = rs.getDouble("tax");
                purchase.mTotal = rs.getDouble("total_cost");
                purchase.mDate = rs.getString("date");

                res.purchases.add(purchase);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public UserModel loadUser(String username) {
        UserModel user = null;

        try {
            String sql = "SELECT * FROM users WHERE username = \"" + username + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = username;
                user.mPassword = rs.getString("password");
                user.mFullname = rs.getString("fullname");
                user.mUserType = rs.getInt("usertype");
                if (user.mUserType == UserModel.CUSTOMER)
                    user.mCustomerID = rs.getInt("customerid");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}