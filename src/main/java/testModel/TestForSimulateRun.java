package testModel;

import job.Job;
import jobqueue.JobsQueue;
import simulateRun.SimulateRunning;

import java.util.Random;

public class TestForSimulateRun {
    public static void main(String[] args) {
        JobsQueue queueA = new JobsQueue("queueA",10);
        for(int i = 0;i<6;i++){
            Random random = new Random();
            Job job1 = new Job(4,10,20+random.nextInt(10));
            queueA.add(job1);
        }
        System.out.println(queueA.getQueueWait().size());
        SimulateRunning.run(100,queueA);


    }
}
