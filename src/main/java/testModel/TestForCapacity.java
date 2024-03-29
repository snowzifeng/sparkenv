package testModel;

import job.Job;
import jobqueue.JobsQueue;
import scheduler.CapacityScheduler;
import simulateRun.SimulateRunning;
import sparkenv.SparkEnv;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestForCapacity {
    public static void main(String[] args) {
        JobsQueue[] queues = new JobsQueue[2];
        queues[0] = new JobsQueue("queueA", 10, 10);
        queues[1] = new JobsQueue("queueB", 10, 10);
        SparkEnv env = new SparkEnv();

        CapacityScheduler scheduler = new CapacityScheduler(20, queues);
        for (int j = 0; j < 1; j++) {
            SimulateRunning.setPredictTime(0);
            for (int i = 0; i < 100; i++) {
//                Random random = new Random();
                Job job1 = new Job(16, 5, 20, i + "");
                scheduler.addJob(queues[0].getName(), job1);
                Job job2 = new Job(20, 5, 20, -i + "");
                scheduler.addJob(queues[0].getName(), job2);

            }


            scheduler = SimulateRunning.run(18, scheduler, 1);
            SimulateRunning.printPredictTime();
            SimulateRunning.setPredictTime(0);
            int end = 0;
            for (int i = 0; i < 1000; i++) {
                scheduler = SimulateRunning.run(18, scheduler, 1);
//                System.out.println(SimulateRunning.avgFinishTime());
//                SimulateRunning.printPredictTime();
                end++;

                System.out.println("\nstate---------");
                System.out.println("avg: " + scheduler.getAvgTime());


                for (JobsQueue job : scheduler.getQueueMap().values()) {

                    System.out.print(job.getName() + " \n" + job.getQueueRun().size() + " :" + job.getAllContainer() + " " + job.getLeftContainer());
                    System.out.print("\njob information\n");
                    for (Job k : job.getQueueRun()) {
                        System.out.println(k.getDelay() + " " + k.getTotaltime());
                    }
                    System.out.println("\n");
                    System.out.println("get used container" + job.getUsedContainer());
                    System.out.println("get left container" + job.getLeftContainer());
                    System.out.println("get all container" + job.getAllContainer());
                    if (job.getUsedContainer() == 0) end = -end;
                    break;
                }
                if (end < 0) {
                    System.out.println("end: ----------------"+end);
                    break;
                }
            }
        }

    }
}
