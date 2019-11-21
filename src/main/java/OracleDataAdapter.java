public class OracleDataAdapter implements IDataAdapter {
    public int connect(String path) {
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int id) {
        return null;
    }

    public int saveProduct(ProductModel model) {
        return SAVED_OK;
    }

    public CustomerModel loadCustomer(int id) {
        return null;
    }

    public int saveCustomer(CustomerModel model) {
        return SAVED_OK;
    }

    public int savePurchase(PurchaseModel model) {
        return SAVED_OK;
    }

    public PurchaseHistoryModel loadPurchaseHistory(int customerID) {
        return null;
    }

    public UserModel loadUser(String username) {
        return null;
    }
}