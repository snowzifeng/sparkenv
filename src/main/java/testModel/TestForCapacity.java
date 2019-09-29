package testModel;

import jobqueue.JobsQueue;
import scheduler.CapacityScheduler;

public class TestForCapacity {
    public static void main(String[]args){
        JobsQueue[] queues = new JobsQueue[3];
        queues[0] = new JobsQueue("queueA",10,20);
        queues[1] = new JobsQueue("queueB",20,20);
        queues[2] = new JobsQueue("queueC",10,20);
        CapacityScheduler scheduler = new CapacityScheduler(40,queues);
    }
}
