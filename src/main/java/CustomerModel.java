public class CustomerModel {
    public int mCustomerID;
    public String mName;
    public String mAddress;
    public String mPhone;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(mCustomerID).append(",");
        sb.append("\"").append(mName).append("\"").append(",");
        sb.append("\"").append(mAddress).append("\",");
        sb.append("\"").append(mPhone).append("\"");
        sb.append(")");
        return sb.toString();
    }
}
