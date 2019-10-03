package synchronizemodel.socketmodel.timecost;

import com.alibaba.fastjson.JSONObject;
import simulateRun.SimulateRunning;
import sparkenv.SparkEnv;
import synchronizemodel.socketmodel.IHandler;

public class TimeCostHandler implements IHandler {

    private SparkEnv sparkEnv;

    public TimeCostHandler(final SparkEnv sparkEnv) {
        this.sparkEnv = sparkEnv;
    }

    @Override
    public JSONObject handle(JSONObject data) {
        JSONObject object = new JSONObject();
        object.put("timeCost", SimulateRunning.avgFinishTime());
        return object;
    }

}
