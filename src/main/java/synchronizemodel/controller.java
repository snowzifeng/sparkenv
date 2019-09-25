package synchronizemodel;

import synchronizemodel.socketmodel.SocketServer;

public class controller {
    static SocketServer server;

    public static void main(String[] args) {
        try {
            server = new SocketServer(55533);

            while (true) {
                String action = server.getInformation();
                System.out.println(action);
                String state = "test";
                int time = 0;
                while (!server.putInformation(state)) {
                    time++;
                    Thread.sleep(1000);
                    if (time > 10) {
                        System.out.println("send message failed");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
