public interface IReceiptBuilder {
    public void appendHeader(String header);
    public void appendCustomer(CustomerModel customer);
    public void appendPurchase(PurchaseModel purchase);
    public void appendFooter(String footer);

    public String toString();
}
