package synchronizemodel;

import sparkenv.SparkEnv;
import synchronizemodel.socketmodel.finishedapplication.FinishedApplicationHandler;
import synchronizemodel.socketmodel.reset.ResetHandler;
import synchronizemodel.socketmodel.simulation.SimulationStepHandler;
import synchronizemodel.socketmodel.SocketServer;
import synchronizemodel.socketmodel.timecost.TimeCostHandler;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final int FINISHED_APPLICATION_SERVER_PORT = 55535;
    private static final int TIME_COST_SERVER_PORT = 55534;
    private static final int RESET_SERVER_PORT = 55533;
    private static final int SIMULATE_STEP_SERVER_PORT = 55532;

    private List<SocketServer> socketServerList = new ArrayList<>();

    private Controller() throws IOException {
        SparkStaticModelManager modelManager = new SparkStaticModelManager();
        modelManager.loadDataFromFile();
        SparkEnv sparkEnv = new SparkEnv();
        ResetHandler resetHandler = new ResetHandler(sparkEnv, modelManager);
        SocketServer resetServer = new SocketServer(resetHandler, RESET_SERVER_PORT);
        SocketServer simulationStepServer = new SocketServer(new SimulationStepHandler(sparkEnv), SIMULATE_STEP_SERVER_PORT);
        SocketServer timeCostServer = new SocketServer(new TimeCostHandler(sparkEnv), TIME_COST_SERVER_PORT);
        SocketServer finishedApplicationServer = new SocketServer(new FinishedApplicationHandler(), FINISHED_APPLICATION_SERVER_PORT);
        registerSocketServer(resetServer);
        registerSocketServer(simulationStepServer);
        registerSocketServer(timeCostServer);
        registerSocketServer(finishedApplicationServer);
    }

    private void start() {
        for (SocketServer server : socketServerList) {
            server.start();
        }
    }

    private void registerSocketServer(final SocketServer socketServer) {
        socketServerList.add(socketServer);
    }

    public static void main(String[] args) throws IOException {
        new Controller().start();
    }

}
