package simulateRun;

import jobqueue.JobsQueue;

import java.util.Collections;
import java.util.List;

import job.Job;

public class SimulateRunning {
    static List<Job> jobList_run;
    static List<Job> jobList_wait;
    static int container;

    private static void resettime(int time) {
//        System.out.println("enter the resettime");

        for (int i = 0; i < jobList_run.size(); i++) {
            int temp = jobList_run.get(i).getWorktimeLeft() - time;
            if (temp == 0) {
                container += jobList_run.get(i).getContainer();

                jobList_run.remove(i);

                i--;
            } else {
                jobList_run.get(i).setWorktimeLeft(temp);
            }
        }
        //        System.out.println("out of reset");

    }

    private static void wait2run() {
        if (jobList_wait.isEmpty()) {
            return;
        } else {
            Job temp = jobList_wait.get(0);
            while (container >= temp.getMaxContainer()) {
                temp.setContainer(temp.getMaxContainer());
                container -= temp.getMaxContainer();
                jobList_run.add(temp);
                jobList_wait.remove(0);
                if (!jobList_wait.isEmpty()) {
                    temp = jobList_wait.get(0);
                } else break;
            }
            if (container >= 2) {
                if (!jobList_wait.isEmpty()) {
                    temp = jobList_wait.get(0);
                    temp.setContainer(container);
                    jobList_run.add(temp);

                    jobList_wait.remove(0);
                    container = 0;
                }
            }

            System.out.println();
            Collections.sort(jobList_run);
        }
        return;
    }

    private static int dojob(int time) {


        if (jobList_run.isEmpty()) {
            if (jobList_wait.isEmpty()) return 0;
            else wait2run();

        }
        if (jobList_run.isEmpty()) {
            return time;
        }

        int runtime = jobList_run.get(0).getWorktimeLeft();
        if (runtime >= time) {

            resettime(time);
            return time;
        } else {

            resettime(runtime);

            if (jobList_run.size() == 0) {
                wait2run();
            }
            if (jobList_run.size() == 0) {
                return time;
            }

            Job job = jobList_run.get(jobList_run.size() - 1);

            if (job.getMaxContainer() == job.getContainer()) {

                wait2run();


            } else {

                if (job.getContainer() + container < job.getMaxContainer()) {

                    job.setContainer(job.getContainer() + container);

                } else {
                    container -= (job.getMaxContainer() - job.getContainer());
                    job.setContainer(job.getMaxContainer());
                    wait2run();

                }
            }
            Collections.sort(jobList_run);
            return runtime;
        }

    }

    public static void run(int time, JobsQueue queue) {
        jobList_run = queue.getQueueRun();
        jobList_wait = queue.getQueueWait();
        container = queue.getLeftContainer();
        Collections.sort(jobList_run);
//        for (Job job : jobList_run) {
//            System.out.print(job.getWorktimeLeft() + " ");
//        }
//        System.out.println();
//        for (Job job : jobList_wait) {
//            System.out.print(job.getWorktimeLeft() + " ");
//        }
//        System.out.println();
        while (time > 0) {

            time -= dojob(time);
//            System.out.println("runlist: ");
//            for (Job job : jobList_run) {
//                System.out.print(job.getWorktimeLeft() + " container:" + job.getContainer() + " ");
//            }
//            System.out.println();
//            System.out.println("waitlist: ");
//            for (Job job : jobList_wait) {
//                System.out.print(job.getWorktimeLeft() + " ");
//            }

        }
        queue.setLeftContainer(container);
    }
}
