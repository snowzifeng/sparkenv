package simulateRun;

import jobqueue.jobQueue;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import job.Job;

public class simulateRun {
    static List<Job> jobList_run;
    static List<Job> jobList_wait;
    static int container;

    private static void resettime(int time) {
        for (int i = 0; i < jobList_run.size(); i++) {
            int temp = jobList_run.get(i).getWorktime_left() - time;
            if (temp == 0) {
                jobList_run.remove(i);
                i--;
            } else {
                jobList_run.get(i).setWorktime_left(temp);
            }
        }

    }

    private static void dojob(int time) {
        int runtime = jobList_run.get(0).getWorktime_left();
        if (runtime >= time) {
            resettime(time);
        } else {
            container += jobList_run.get(0).getContainer();
            Job job = jobList_run.get(jobList_run.size() - 1);
            if (job.getMaxcontainer() == job.getContainer()) {
                if (container>=2){
                    Job temp;

                }
            } else {

            }

        }

    }

    public static void run(int time, jobQueue queue) {
        jobList_run = queue.getQueue_run();
        jobList_wait = queue.getQueue_wait();
        container = queue.getLeft_container();
        Collections.sort(jobList_run);

    }
}
