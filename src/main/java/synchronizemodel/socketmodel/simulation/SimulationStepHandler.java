package synchronizemodel.socketmodel.simulation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sparkenv.SparkEnv;
import synchronizemodel.socketmodel.IHandler;
import util.ArgumentParsingUtil;
import util.QueueAdaptionUtil;
import util.TwoTuple;

import java.util.ArrayList;
import java.util.List;

public class SimulationStepHandler implements IHandler {

    private SparkEnv sparkEnv;

    public SimulationStepHandler(final SparkEnv sparkEnv) {
        this.sparkEnv = sparkEnv;
    }

    public JSONObject handle(JSONObject data) {
        List<QueueArgument> queueArguments = ArgumentParsingUtil.parseQueueArguments(data);
        return simulateStep(queueArguments);
    }

    private JSONObject simulateStep(List<QueueArgument> queueArguments) {
        return sparkEnv.doAction(30000, QueueAdaptionUtil.convert(queueArguments));
    }

}
