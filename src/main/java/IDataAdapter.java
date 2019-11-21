interface IDataAdapter {
    int CONNECTION_OPEN_OK = 100;
    int CONNECTION_OPEN_FAILED = 101;

    int CONNECTION_CLOSE_OK = 200;
    int CONNECTION_CLOSE_FAILED = 201;

    int SAVED_OK = 0;
    int DUPLICATE_ERROR = 1;

    int connect(String dbfile);
    int disconnect();

    ProductModel loadProduct(int id);
    int saveProduct(ProductModel model);

    CustomerModel loadCustomer(int id);
    int saveCustomer(CustomerModel model);

    int savePurchase(PurchaseModel model);

    PurchaseHistoryModel loadPurchaseHistory(int customerID);
    UserModel loadUser(String username);
}
