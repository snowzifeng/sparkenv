package scheduler;

import jobqueue.JobsQueue;

import java.util.List;
import java.util.Map;

import job.Job;

public abstract class AbstractScheduler {
    List<JobsQueue> queueList;

    abstract String getState();

    abstract void doAction(String action);

    abstract void addJob(int index, Job job);

    public void addQueue(JobsQueue queue) {
        queueList.add(queue);
    }
    abstract public Map<String, JobsQueue> getQueueMap();
    abstract public void addJob(String name, Job job);
    abstract public boolean isEmpty() ;
    abstract public int getAvgTime();
    public void setQueueMap(Map<String, JobsQueue> queueMap){};

}
