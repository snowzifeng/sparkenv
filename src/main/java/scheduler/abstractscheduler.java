package scheduler;

import jobqueue.jobQueue;

import java.util.List;

import job.Job;

public abstract class abstractscheduler {
    List<jobQueue> queueList;

    abstract String getState();

    abstract void doAction(String action);

    abstract void addJob(int index, Job job);

    public void addQueue(jobQueue queue) {
        queueList.add(queue);
    }

}
