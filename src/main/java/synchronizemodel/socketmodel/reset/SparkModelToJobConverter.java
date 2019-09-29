package synchronizemodel.socketmodel.reset;

import job.Job;

public class SparkModelToJobConverter {

    private static final int STARTUP_TIME = 5;

    public Job convert(SparkModel sparkModel) {
        return new Job(sparkModel.getNumContainer(), STARTUP_TIME, sparkModel.getTimeCost());
    }

}
