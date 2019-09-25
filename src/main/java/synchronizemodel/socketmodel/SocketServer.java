package synchronizemodel.socketmodel;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    static ServerSocket server = null;
    static Socket socket = null;
    static InputStream inputStream =null;
    static OutputStream outputStream = null;

    public SocketServer(int port) throws Exception {
        if (server == null) {
            server = new ServerSocket(port);
            socket = server.accept();
        } else {
            System.out.println("server already existed");
        }

    }

    public String getInformation() {
        try {
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();

            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }


            return sb.toString();
        } catch (Exception e) {
            System.out.println("getinformation: "+e);
            return null;

        }

    }

    public boolean putInformation(String state) {

        try {
            outputStream = socket.getOutputStream();
            outputStream.write(state.getBytes("UTF-8"));
            outputStream.close();

        } catch (Exception e) {
            System.out.println("putinformation："+e);
            return false;
        }
        return true;
    }

    public boolean closeLink() {
        try {
            outputStream.close();
            inputStream.close();
            socket.close();
            server.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean getLink(int port) {
        try {
            if (socket == null) {
                server = new ServerSocket(port);
            }
            if (socket == null) {
                socket = server.accept();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws Exception {
        // 监听指定的端口
        while(true) {
            int port = 55533;
            ServerSocket server = new ServerSocket(port);

            // server将一直等待连接的到来
            System.out.println("server将一直等待连接的到来");
            Socket socket = server.accept();
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
            while ((len = inputStream.read(bytes)) != -1) {
                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println("get message from client: " + sb);

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Hello Client,I get the message.".getBytes("UTF-8"));

            inputStream.close();
            outputStream.close();
            socket.close();
            server.close();
        }
    }
}