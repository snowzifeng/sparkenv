package synchronizemodel.socketmodel;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
    static Socket socket = null;

    public SocketClient(String host, int port) throws Exception {
        if (socket == null) {
            socket = new Socket(host, port);
        }
    }

    public boolean sendMessage(String message) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            socket.getOutputStream().write(message.getBytes("UTF-8"));
            socket.shutdownOutput();
            outputStream.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public String getMessage() {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println("get message from server: " + sb);

            inputStream.close();
            return sb.toString();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public static void main(String args[]) throws Exception {
        String host = "127.0.0.1";
        int port = 55533;
        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        // 建立连接后获得输出流
        OutputStream outputStream = socket.getOutputStream();
        String message = "你好  yiwangzhibujian";
        socket.getOutputStream().write(message.getBytes("UTF-8"));
        //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        System.out.println("get message from server: " + sb);

        inputStream.close();
        outputStream.close();
        socket.close();

    }
}

