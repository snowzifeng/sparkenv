package scheduler;

import job.Job;
import jobqueue.jobQueue;


public class capacityscheduler extends abstractscheduler {

    int Container;

    public capacityscheduler(int containerNumber) {
        this.Container = containerNumber;
    }

    public void initQueue(String[] name, int[] container) {

        for (int i = 0; i < name.length; i++) {
            jobQueue queue = new jobQueue(name[i], container[i]);
        }
    }

    String getState() {
        return null;
    }

    public void doAction(String action) {

    }

    void addJob(int index, Job job) {
        jobQueue queue = queueList.get(index);
        queue.add(job);
    }
}
