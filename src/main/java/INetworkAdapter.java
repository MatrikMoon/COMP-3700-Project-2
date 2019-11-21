public interface INetworkAdapter {
    String exchange(String msg, String host, int port) throws Exception;

    MessageModel exchange(MessageModel msg, String host, int port) throws Exception;
}
