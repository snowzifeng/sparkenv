package synchronizemodel;

import sparkenv.SparkEnv;
import synchronizemodel.socketmodel.reset.ResetHandler;
import synchronizemodel.socketmodel.simulation.SimulationStepHandler;
import synchronizemodel.socketmodel.SocketServer;
import synchronizemodel.socketmodel.timecost.TimeCostHandler;

import java.io.IOException;
import java.sql.Time;

public class Controller {

    private static final int TIME_COST_SERVER_PORT = 55534;
    private static final int RESET_SERVER_PORT = 55533;
    private static final int SIMULATE_STEP_SERVER_PORT = 55532;

    private SocketServer resetServer;
    private SocketServer simulationStepServer;
    private SocketServer timeCostServer;

    private Controller() throws IOException {
        SparkStaticModelManager modelManager = new SparkStaticModelManager();
        modelManager.loadDataFromFile();
        SparkEnv sparkEnv = new SparkEnv();
        ResetHandler resetHandler = new ResetHandler(sparkEnv, modelManager);
        resetServer = new SocketServer(resetHandler, RESET_SERVER_PORT);
        simulationStepServer = new SocketServer(new SimulationStepHandler(sparkEnv), SIMULATE_STEP_SERVER_PORT);
        timeCostServer = new SocketServer(new TimeCostHandler(sparkEnv), TIME_COST_SERVER_PORT);
    }

    private void start() {
        resetServer.start();
        simulationStepServer.start();
        timeCostServer.start();
    }

    public static void main(String[] args) throws IOException {
        new Controller().start();
    }

}
