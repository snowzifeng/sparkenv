package sparkenv;

import job.Job;
import jobqueue.JobsQueue;
import scheduler.AbstractScheduler;
import scheduler.CapacityScheduler;
import util.TwoTuple;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

public class SparkEnv {
    AbstractScheduler scheduler;
    int fullContainer;

    public void init(int chooseScheduler, Map<String, Integer> queue) {
        List<String> queueName = new ArrayList<String>(queue.keySet());
        JobsQueue[] jobsQueues = new JobsQueue[queueName.size()];
        fullContainer = 0;

        for (int i = 0; i < queueName.size(); i++) {
            fullContainer += queue.get(queueName.get(i));
            jobsQueues[i] = new JobsQueue(queueName.get(i), queue.get(queueName.get(i)));
        }

        switch (chooseScheduler) {
            case 0:
                scheduler = new CapacityScheduler(fullContainer, jobsQueues);
                break;
            case 1:

            case 2:

            default:
                break;
        }
    }

    public void runEnv(int totalTime, int[] intervaltime, List<TwoTuple<Integer, TwoTuple<Job, String>>> jobInformation) {
        while (totalTime != 0) {


        }

    }

    public void reset() {

    }

    public void doAction() {

    }


}
