package testModel;

import job.Job;
import jobqueue.JobsQueue;
import scheduler.CapacityScheduler;
import simulateRun.SimulateRunning;
import sparkenv.SparkEnv;

import java.util.Random;

public class TestForCapacity {
    public static void main(String[] args) {
        JobsQueue[] queues = new JobsQueue[2];
        queues[0] = new JobsQueue("queueA", 20, 40);
        queues[1] = new JobsQueue("queueB", 20, 40);
        SparkEnv env = new SparkEnv();

        CapacityScheduler scheduler = new CapacityScheduler(20, queues);
        for (int j = 0; j < 3000; j++) {
            for (int i = 0; i < 6000; i++) {
                Random random = new Random();
                Job job1 = new Job(8+random.nextInt(5), 10, 10 + random.nextInt(10),"1");
                scheduler.addJob(queues[0].getName(),job1);
                Job job2 = new Job(8+random.nextInt(3), 10, 20 + random.nextInt(10),"2");
                scheduler.addJob(queues[random.nextInt(2)].getName(),job2);

//                System.out.println("test  i-----:"+ i);
//                System.out.println(SimulateRunning.avgFinishTime());

//                System.out.println(queues[j].getQueueRun().size()+" : "+queues[j].getLeftContainer());
            }
            for(int i  = 0;i<4;i++){
                scheduler = SimulateRunning.run(18,scheduler);
                System.out.println(SimulateRunning.avgFinishTime());
            }

            System.out.println("\nstate---------");
            System.out.println("avg: "+scheduler.getAvgTime());


            for(JobsQueue job: scheduler.getQueueMap().values()){

                System.out.print(job.getName()+" \n" +job.getQueueRun().size()+" :"+job.getAllContainer()+" "+job.getLeftContainer());
                System.out.print("\n");
                for(Job k: job.getQueueRun()){
                    System.out.print(k.getContainer()+" ");
                }
                System.out.println("\n");
                System.out.println("get used container"+job.getUsedContainer());
                System.out.println("get left container"+job.getLeftContainer());
                System.out.println("get all container"+job.getAllContainer());


            }
        }

    }
}
