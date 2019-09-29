package synchronizemodel.socketmodel.reset;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import synchronizemodel.SparkStaticModelManager;
import synchronizemodel.socketmodel.IHandler;
import util.TupleUtil;
import util.TwoTuple;

import java.util.ArrayList;
import java.util.List;

public class ResetHandler implements IHandler {

    private SparkStaticModelManager modelManager;
    private SparkModelToJobConverter converter = new SparkModelToJobConverter();

    public ResetHandler(final SparkStaticModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public TwoTuple<JSONObject, Boolean> handle(JSONObject data) {
        final ResetArgument arguments = parseArguments(data);
        resetEnvironment(arguments);
        return TupleUtil.emptyTwo();
    }

    private void resetEnvironment(final ResetArgument resetArgument) {
        // TODO: 此处添加reset的业务逻辑
    }

    private ResetArgument parseArguments(final JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("workloads");
        int stepInterval = data.getIntValue("interval");
        int numContainer = data.getIntValue("numContainer");

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
        return new ResetArgument(workloadList, numContainer, stepInterval);
    }

}
