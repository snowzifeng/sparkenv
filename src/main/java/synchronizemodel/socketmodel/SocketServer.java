package synchronizemodel.socketmodel;

import com.alibaba.fastjson.JSONObject;
import util.TwoTuple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {

    private IHandler handler;
    private ServerSocket serverSocket;

    public SocketServer(IHandler handler, int port) throws IOException {
        this.handler = handler;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while (true) {
                runStep();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runStep() throws IOException {
        Socket socket = serverSocket.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data = br.readLine();

        String result = handle(data);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(result);
        bw.flush();

        socket.close();
        br.close();
        bw.close();
    }

    private String handle(final String data) {
        JSONObject input = JSONObject.parseObject(data);
        JSONObject results = handler.handle(input);
        return JSONObject.toJSONString(results);
    }

}
