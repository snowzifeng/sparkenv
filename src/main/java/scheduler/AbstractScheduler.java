package scheduler;

import jobqueue.JobQueue;

import java.util.List;

import job.Job;

public abstract class AbstractScheduler {
    List<JobQueue> queueList;

    abstract String getState();

    abstract void doAction(String action);

    abstract void addJob(int index, Job job);

    public void addQueue(JobQueue queue) {
        queueList.add(queue);
    }

}
