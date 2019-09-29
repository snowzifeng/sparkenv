package sparkenv;

import jdk.nashorn.internal.scripts.JO;
import job.Job;
import jobqueue.JobsQueue;
import scheduler.AbstractScheduler;
import scheduler.CapacityScheduler;
import simulateRun.SimulateRunning;
import util.TwoTuple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;

public class SparkEnv {
    AbstractScheduler scheduler;
    int fullContainer;
    Map<String, TwoTuple<Integer, Integer>> initmap;
    int chooseScheduler;
    List<TwoTuple<Integer, TwoTuple<Job, String>>> jobInformation = new LinkedList<TwoTuple<Integer, TwoTuple<Job, String>>>();

    public void init(int chooseScheduler, Map<String, TwoTuple<Integer, Integer>> queue) {
        List<String> queueName = new ArrayList<String>(queue.keySet());
        JobsQueue[] jobsQueues = new JobsQueue[queueName.size()];
        this.fullContainer = fullContainer;
        this.initmap = queue;
        this.chooseScheduler = chooseScheduler;

        for (int i = 0; i < queueName.size(); i++) {
            fullContainer += queue.get(queueName.get(i)).getFirst();
            jobsQueues[i] = new JobsQueue(queueName.get(i), queue.get(queueName.get(i)).getFirst(), queue.get(queueName.get(i)).getSecond());
        }

        switch (chooseScheduler) {
            case 0:
                scheduler = new CapacityScheduler(fullContainer, jobsQueues);
                break;
            case 1:

            case 2:

            default:
                break;
        }
    }

    public void runEnv(List<TwoTuple<Integer, TwoTuple<Job, String>>> jobInformation) {
        for (TwoTuple<Integer, TwoTuple<Job, String>> temp : jobInformation) {
            this.jobInformation.add(temp);
        }

//        Map<String, JobsQueue> map = scheduler.getQueueMap();
//        List<JobsQueue> queues = new ArrayList<JobsQueue>(map.values());
//        while (true) {
//            if (n < jobInformation.size()) {
//                int time = jobInformation.get(n).getFirst();
//                for (JobsQueue temp : queues) {
//                    SimulateRunning.run(time, temp);
//                }
//                Job job = jobInformation.get(n).getSecond().getFirst();
//                scheduler.addJob(jobInformation.get(n).getSecond().getSecond(), job);
//                n++;
//                totaltime-=time;
//            }else if (totaltime>0){
//                for (JobsQueue temp : queues) {
//                    SimulateRunning.run(totaltime, temp);
//                }
//                break;
//            }
//
//
//        }

    }


    public void reset() {
        List<String> queueName = new ArrayList<String>(initmap.keySet());
        JobsQueue[] jobsQueues = new JobsQueue[queueName.size()];
        this.fullContainer = fullContainer;

        for (int i = 0; i < queueName.size(); i++) {
            fullContainer += initmap.get(queueName.get(i)).getFirst();
            jobsQueues[i] = new JobsQueue(queueName.get(i), initmap.get(queueName.get(i)).getFirst(), initmap.get(queueName.get(i)).getSecond());
        }
        switch (chooseScheduler) {
            case 0:
                scheduler = new CapacityScheduler(fullContainer, jobsQueues);
                break;
            case 1:

            case 2:

            default:
                break;
        }

    }


    public String doAction(int interal, Map<String, TwoTuple<Integer, Integer>> queue) {
        Map<String, JobsQueue> map = scheduler.getQueueMap();
        List<String> queueName = new ArrayList<String>(initmap.keySet());

        for (int i = 0; i < queueName.size(); i++) {
            map.get(queueName.get(i)).setAllContainer(queue.get(queueName.get(i)).getFirst());
            map.get(queueName.get(i)).setMaxContainer(queue.get(queueName.get(i)).getSecond());
        }


        int totaltime = interal;
        List<JobsQueue> queues = new ArrayList<JobsQueue>(map.values());
        while (true) {
            if (0 < jobInformation.size()) {
                int time = jobInformation.get(0).getFirst();
                for (JobsQueue temp : queues) {
                    SimulateRunning.run(time, temp);
                }
                Job job = jobInformation.get(0).getSecond().getFirst();
                scheduler.addJob(jobInformation.get(0).getSecond().getSecond(), job);
                jobInformation.remove(0);
                totaltime -= time;
            } else if (totaltime > 0) {
                for (JobsQueue temp : queues) {
                    SimulateRunning.run(totaltime, temp);
                }
                break;
            }

        }

        List<Job> runJob = new LinkedList<Job>();
        List<Job> waitJob = new LinkedList<Job>();
        List<String> source = new LinkedList<String>();
        List<String> stricts = new LinkedList<String>();
        List<String> name = new LinkedList<String>();

        for (JobsQueue q : map.values()) {
            for (Job j : q.getQueueRun()) runJob.add(j);
            for (Job j : q.getQueueWait()) waitJob.add(j);
            source.add("used:" + q.getUsedContainer() + ",left:" + q.getLeftContainer());
            stricts.add("common:" + q.getInitContainer() + ",max:" + q.getMaxContainer());
            name.add(q.getName());
        }
        String state = "{workload:{run:[";
        String temp = "";
        for (Job j : runJob) {
            temp += "{queueName:" + j.getQueueName() + ",worktime:" + j.getWorktimeLeft() + ",container:" + (j.getMaxContainer() - j.getContainer()) + "},";
        }
        temp = temp.substring(0, temp.length() - 1);
        state += temp + "],wait:[";
        temp = "";
        for (Job j : waitJob) {
            temp += "{queueName:" + j.getQueueName() + ",worktime:" + j.getWorktimeLeft() + ",container:" + (j.getMaxContainer() - j.getContainer()) + "},";
        }
        temp = temp.substring(0, temp.length() - 1);
        state += temp + "],source:[";

        String sourceString = "";
        for (int i = 0; i < name.size(); i++) {
            sourceString += source.get(i);
        }


        return state;

    }


}
