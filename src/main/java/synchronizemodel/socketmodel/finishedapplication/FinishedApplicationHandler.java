package synchronizemodel.socketmodel.finishedapplication;

import com.alibaba.fastjson.JSONObject;
import simulateRun.SimulateRunning;
import synchronizemodel.socketmodel.IHandler;

public class FinishedApplicationHandler implements IHandler {

    @Override
    public JSONObject handle(JSONObject data) {
        JSONObject object = new JSONObject();
        object.put("timeCost", SimulateRunning.avgFinishTime());
        return object;
    }

}
