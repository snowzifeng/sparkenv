package synchronizemodel.socketmodel.reset;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sparkenv.SparkEnv;
import synchronizemodel.SparkStaticModelManager;
import synchronizemodel.socketmodel.IHandler;
import synchronizemodel.socketmodel.simulation.QueueArgument;
import util.ArgumentParsingUtil;
import util.JobAdaptionUtil;
import util.QueueAdaptionUtil;
import util.TwoTuple;

import java.util.ArrayList;
import java.util.List;

public class ResetHandler implements IHandler {

    private SparkStaticModelManager modelManager;
    private SparkEnv sparkEnv = new SparkEnv();

    public ResetHandler(final SparkStaticModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public TwoTuple<JSONObject, Boolean> handle(JSONObject data) {
        final ResetArgument arguments = parseArguments(data);
        JSONObject state = resetEnvironment(arguments);
        return new TwoTuple<>(state, true);
    }

    private JSONObject resetEnvironment(final ResetArgument resetArgument) {
        sparkEnv.init(0, QueueAdaptionUtil.convert(resetArgument.getQueueArguments()));
        sparkEnv.runEnv(JobAdaptionUtil.convert(resetArgument));
        return sparkEnv.doAction(resetArgument.getStepInterval(), QueueAdaptionUtil.convert(resetArgument.getQueueArguments()));
    }

    private ResetArgument parseArguments(final JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("workloads");
        int stepInterval = data.getIntValue("interval");
        int numContainer = data.getIntValue("numContainer");
        List<QueueArgument> queueArguments = ArgumentParsingUtil.parseQueueArguments(data);

        List<Workload> workloadList = new ArrayList<>();
        for (int i = 0; i != jsonArray.size(); ++i) {
            JSONObject object = jsonArray.getJSONObject(i);
            final String appType = object.getString("appType");
            final int dataSize = object.getIntValue("dataSize");
            final int submitInterval = object.getIntValue("interval");
            final String queue = object.getString("queue");

            Workload workload = new Workload(appType, dataSize, submitInterval, queue);
            workloadList.add(workload);
        }

        return new ResetArgument(queueArguments, workloadList, numContainer, stepInterval);
    }

}
