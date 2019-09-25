package synchronizemodel;

import synchronizemodel.socketmodel.ResetHandler;
import synchronizemodel.socketmodel.SimulationStepHandler;
import synchronizemodel.socketmodel.SocketServer;

import java.io.IOException;

public class Controller {

    private static final int RESET_SERVER_PORT = 55533;
    private static final int SIMULATE_STEP_SERVER_PORT = 55532;

    private SocketServer resetServer;
    private SocketServer simulationStepServer;

    private Controller() throws IOException {
        resetServer = new SocketServer(new ResetHandler(), RESET_SERVER_PORT);
        simulationStepServer = new SocketServer(new SimulationStepHandler(), SIMULATE_STEP_SERVER_PORT);
    }

    private void start() {
        resetServer.start();
        simulationStepServer.start();
    }

    public static void main(String[] args) throws IOException {
        new Controller().start();
    }

}
