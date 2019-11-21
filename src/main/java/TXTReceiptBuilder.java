public class TXTReceiptBuilder implements IReceiptBuilder {

    StringBuilder sb = new StringBuilder();

    @Override
    public void appendHeader(String header) {
        sb.append(header).append("\n");
    }

    @Override
    public void appendCustomer(CustomerModel customer) {
        sb.append("Customer ID: ").append(customer.mCustomerID).append("\n");
        sb.append("Customer Name: ").append(customer.mName).append("\n");
        sb.append("Customer Address: ").append(customer.mAddress).append("\n");
        sb.append("Customer Phone: ").append(customer.mPhone).append("\n");
        sb.append("\n");
    }

    @Override
    public void appendPurchase(PurchaseModel purchase) {
        sb.append("Purchase Date: ").append(purchase.mDate).append("\n");
        sb.append("Product ID: ").append(purchase.mProductID).append("\n");
        sb.append("Purchase ID: ").append(purchase.mPurchaseID).append("\n");
        sb.append("Quantity: ").append(purchase.mQuantity).append("\n");
        sb.append("Tax: ").append(purchase.mTax).append("\n");
        sb.append("Total: ").append(purchase.mTotal).append("\n");
        sb.append("\n");
    }

    @Override
    public void appendFooter(String footer) {
        sb.append(footer).append("\n");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
