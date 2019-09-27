package jobqueue;

import java.util.LinkedList;
import java.util.List;

import job.Job;

public class JobQueue {
    List<Job> queueRun;
    List<Job> queueWait;
    String Name;
    int allContainer;
    int usedContainer;
    int leftContainer;

    public JobQueue(String name, int allContainer) {
        this.Name = name;
        this.allContainer = allContainer;
        this.usedContainer = 0;
        this.leftContainer = allContainer;
        this.queueWait = new LinkedList<Job>();
        this.queueRun = new LinkedList<Job>();
    }

    public JobQueue(String name) {
        this.Name = name;
    }

    public void add(Job job) {
        queueWait.add(job);
    }

    public void setUsedContainer(int usedContainer) {
        this.usedContainer = usedContainer;
    }

    public void setLeftContainer(int leftContainer) {
        this.leftContainer = leftContainer;
    }

    public void setAllContainer(int allContainer) {
        this.allContainer = allContainer;
    }

    public int getUsedContainer() {
        return usedContainer;
    }

    public int getAllContainer() {
        return allContainer;
    }

    public int getLeftContainer() {
        return leftContainer;
    }

    public List<Job> getQueueRun() {
        return queueRun;
    }

    public List<Job> getQueueWait() {
        return queueWait;
    }
}
