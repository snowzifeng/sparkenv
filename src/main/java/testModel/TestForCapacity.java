package testModel;

import job.Job;
import jobqueue.JobsQueue;
import scheduler.CapacityScheduler;
import simulateRun.SimulateRunning;

import java.util.Random;

public class TestForCapacity {
    public static void main(String[] args) {
        JobsQueue[] queues = new JobsQueue[2];
        queues[0] = new JobsQueue("queueA", 10, 20);
        queues[1] = new JobsQueue("queueB", 10, 20);

        CapacityScheduler scheduler = new CapacityScheduler(20, queues);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 6; i++) {
                Random random = new Random();
                Job job1 = new Job(4, 10, 20 + random.nextInt(10));
                scheduler.addJob(queues[random.nextInt(2)].getName(),job1);
                scheduler = SimulateRunning.run(2,scheduler);
//                System.out.println("test  i-----:"+ i);

//                System.out.println(queues[j].getQueueRun().size()+" : "+queues[j].getLeftContainer());
            }

            for(JobsQueue job: scheduler.getQueueMap().values()){
                System.out.println("state---------");
                System.out.print(job.getName()+" " +job.getQueueRun().size()+" :"+job.getAllContainer()+" "+" -left: "+job.getLeftContainer()+" ===");
                System.out.println();
                for(Job k: job.getQueueRun()){
                    System.out.print(k.getContainer()+" ");
                }
                System.out.println();

            }
        }



    }
}
