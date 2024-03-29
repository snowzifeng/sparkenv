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

    private SparkEnv sparkEnv;
    private SparkStaticModelManager modelManager;

    public ResetHandler(final SparkEnv sparkEnv, final SparkStaticModelManager modelManager) {
        this.sparkEnv = sparkEnv;
        this.modelManager = modelManager;
    }

    public JSONObject handle(JSONObject data) {
        final ResetArgument arguments = parseArguments(data);
        return resetEnvironment(arguments);
    }

    private JSONObject resetEnvironment(final ResetArgument resetArgument) {
        sparkEnv.init(0, QueueAdaptionUtil.convert(resetArgument.getQueueArguments()));
        sparkEnv.runEnv(JobAdaptionUtil.convert(resetArgument, modelManager));
        sparkEnv.setStepInterval(resetArgument.getStepInterval());
        return sparkEnv.doAction(sparkEnv.getStepInterval(), QueueAdaptionUtil.convert(resetArgument.getQueueArguments()));
    }

    private ResetArgument parseArguments(final JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("workloads");
        int stepInterval = data.getIntValue("interval");
        int numContainer = data.getIntValue("numContainer");
        List<QueueArgument> queueArguments = ArgumentParsingUtil.parseQueueArguments(data);

        List<Workload> workloadList = new ArrayList<>();
        for (int i = 0; i != jsonArray.size(); ++i) {
            JSONObject object = jsonArray.getJSONObject(i);
            final String appType = object.getString("name");
            final int dataSize = object.getIntValue("dataSize");
            final int submitInterval = object.getIntValue("interval") * 1000;
            final String queue = object.getString("queue");
            final String jobId = object.getString("id");

            Workload workload = new Workload(appType, dataSize, submitInterval, queue, jobId);
            workloadList.add(workload);
        }

        return new ResetArgument(queueArguments, workloadList, numContainer, stepInterval);
    }

}
