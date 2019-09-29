package scheduler;

import jobqueue.JobsQueue;

import java.util.List;

import job.Job;

public abstract class AbstractScheduler {
    List<JobsQueue> queueList;

    abstract String getState();

    abstract void doAction(String action);

    abstract void addJob(int index, Job job);

    public void addQueue(JobsQueue queue) {
        queueList.add(queue);
    }

}
