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

    private SparkEnv sparkEnv = new SparkEnv();

    public TwoTuple<JSONObject, Boolean> handle(JSONObject data) {
        List<QueueArgument> queueArguments = ArgumentParsingUtil.parseQueueArguments(data);
        return simulateStep(queueArguments);
    }

    private TwoTuple<JSONObject, Boolean> simulateStep(List<QueueArgument> queueArguments) {
        return new TwoTuple<JSONObject, Boolean>(
                sparkEnv.doAction(30, QueueAdaptionUtil.convert(queueArguments)), true
        );
    }

}
