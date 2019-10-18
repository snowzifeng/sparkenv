package util;

import job.Job;
import synchronizemodel.SparkStaticModelManager;
import synchronizemodel.socketmodel.reset.ResetArgument;
import synchronizemodel.socketmodel.reset.SparkModel;
import synchronizemodel.socketmodel.reset.Workload;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobAdaptionUtil {

    public static List<TwoTuple<Integer, TwoTuple<Job, String>>> convert(ResetArgument resetArgument, SparkStaticModelManager modelManager) {
        List<TwoTuple<Integer, TwoTuple<Job, String>>> list = new ArrayList<>();
        for (Workload workload : resetArgument.getWorkloads()) {
            int interval = workload.getInterval();
            Optional<SparkModel> sparkModelOptional = modelManager.findModel(workload.getAppType(), workload.getDataSize());
            if (sparkModelOptional.isPresent()) {
                String jobId = workload.getId();
                Job job = SparkModelToJobConvertionUtil.convert(sparkModelOptional.get(), jobId);
                String queue = workload.getQueue();
                list.add(new TwoTuple<>(interval, new TwoTuple<>(job, queue)));
            }
        }
        return list;
    }

}
