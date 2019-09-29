package testModel;

import job.Job;
import jobqueue.JobsQueue;
import scheduler.CapacityScheduler;
import simulateRun.SimulateRunning;

import java.util.Random;

public class TestForCapacity {
    public static void main(String[] args) {
        JobsQueue[] queues = new JobsQueue[3];
        queues[0] = new JobsQueue("queueA", 10, 20);
        queues[1] = new JobsQueue("queueB", 20, 20);
        queues[2] = new JobsQueue("queueC", 10, 20);
        CapacityScheduler scheduler = new CapacityScheduler(40, queues);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 6; i++) {
                Random random = new Random();
                Job job1 = new Job(4, 10, 20 + random.nextInt(10));
                scheduler.addJob(queues[j].getName(),job1);
                SimulateRunning.run(2,queues[j]);
                System.out.println("test  i-----:"+ i);

//                System.out.println(queues[j].getQueueRun().size()+" : "+queues[j].getLeftContainer());
            }
            System.out.println();
            System.out.println(queues[j].getQueueRun().size()+" : "+queues[j].getMaxContainer());
            System.out.println(queues[j].getQueueWait().size());
        }

    }
}
