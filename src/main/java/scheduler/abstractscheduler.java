package scheduler;

import jobqueue.jobQueue;

import java.util.List;

public abstract class abstractscheduler {
    List<jobQueue> queueList;


    abstract String getState();
    abstract boolean doAction(String action);

}
