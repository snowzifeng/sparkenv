package synchronizemodel.socketmodel.simulation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import synchronizemodel.socketmodel.IHandler;
import util.TwoTuple;

import java.util.ArrayList;
import java.util.List;

public class SimulationStepHandler implements IHandler {

    public TwoTuple<JSONObject, Boolean> handle(JSONObject data) {
        List<QueueArgument> queueArguments = parseArguments(data);
        return simulateStep(queueArguments);
    }

    private TwoTuple<JSONObject, Boolean> simulateStep(List<QueueArgument> queueArguments) {
        return null;
    }

    private List<QueueArgument> parseArguments(final JSONObject data) {
        JSONArray queues = data.getJSONArray("queues");
        List<QueueArgument> queueArguments = new ArrayList<>();
        for (int i = 0; i != queues.size(); ++i) {
            JSONObject queue = queues.getJSONObject(i);
            final String name = queue.getString("name");
            final int capacity = queue.getIntValue("capacity");
            final int maxCapacity = queue.getIntValue("maxCapacity");
            QueueArgument argument = new QueueArgument(name, capacity, maxCapacity);
            queueArguments.add(argument);
        }
        return queueArguments;
    }

}
