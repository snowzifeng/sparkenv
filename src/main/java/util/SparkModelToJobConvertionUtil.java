package util;

import job.Job;
import synchronizemodel.socketmodel.reset.SparkModel;

public class SparkModelToJobConvertionUtil {

    private static final int STARTUP_TIME = 5;

    public static Job convert(SparkModel sparkModel, final String jobId) {
        return new Job(sparkModel.getNumContainer(), STARTUP_TIME, sparkModel.getTimeCost(), jobId);
    }

}
