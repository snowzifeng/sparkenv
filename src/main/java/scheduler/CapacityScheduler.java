package scheduler;

import job.Job;
import jobqueue.JobQueue;


public class CapacityScheduler extends AbstractScheduler {

    int Container;

    public CapacityScheduler(int containerNumber) {
        this.Container = containerNumber;
    }

    public void initQueue(String[] name, int[] container) {

        for (int i = 0; i < name.length; i++) {
            JobQueue queue = new JobQueue(name[i], container[i]);
        }
    }

    String getState() {
        return null;
    }

    public void doAction(String action) {

    }

    void addJob(int index, Job job) {
        JobQueue queue = queueList.get(index);
        queue.add(job);
    }
}
