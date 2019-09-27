package testModel;

import job.Job;
import jobqueue.JobQueue;
import simulateRun.SimulateRun;

import java.util.Random;

public class TestForSimulateRun {
    public static void main(String[] args) {
        JobQueue queueA = new JobQueue("queueA",10);
        for(int i = 0;i<6;i++){
            Random random = new Random();
            Job job1 = new Job(4,10,20+random.nextInt(10));
            queueA.add(job1);
        }
        System.out.println(queueA.getQueueWait().size());
        SimulateRun.run(100,queueA);


    }
}
