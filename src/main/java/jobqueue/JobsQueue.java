package jobqueue;

import java.util.LinkedList;
import java.util.List;

import job.Job;

public class JobsQueue {
    List<Job> queueRun;
    List<Job> queueWait;
    String Name;
    int initContainer;
    int allContainer;
    int usedContainer;
    int leftContainer;
    int maxContainer;



    public JobsQueue(String name, int initContainer) {
        this.Name = name;
        this.allContainer = initContainer;
        this.initContainer = initContainer;

        this.usedContainer = 0;
        this.leftContainer = initContainer;
        this.queueWait = new LinkedList<Job>();
        this.queueRun = new LinkedList<Job>();
    }

    public JobsQueue(String name, int initContainer, int maxContainer){
        this.Name = name;
        this.allContainer = initContainer;
        this.initContainer = initContainer;

        this.usedContainer = 0;
        this.leftContainer = initContainer;
        this.queueWait = new LinkedList<Job>();
        this.queueRun = new LinkedList<Job>();
        this.maxContainer = maxContainer;
    }

    public int getInitContainer() {
        return initContainer;
    }

    public void setInitContainer(int initContainer) {
        this.initContainer = initContainer;
    }

    public int getMaxContainer() {
        return maxContainer;
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

    public void setMaxContainer(int maxContainer) {
        this.maxContainer = maxContainer;
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

    public String getName() {
        return Name;
    }

    public List<Job> getQueueRun() {
        return queueRun;
    }

    public List<Job> getQueueWait() {
        return queueWait;
    }

    public void setQueueRun(List<Job> queueRun) {
        this.queueRun = queueRun;
    }

    public void setQueueWait(List<Job> queueWait) {
        this.queueWait = queueWait;
    }
}
