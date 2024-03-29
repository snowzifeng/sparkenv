package sparkenv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    int stepInterval;

    public void init(int chooseScheduler, Map<String, TwoTuple<Integer, Integer>> queue) {
        List<String> queueName = new ArrayList<String>(queue.keySet());
        JobsQueue[] jobsQueues = new JobsQueue[queueName.size()];
        this.fullContainer = fullContainer;
        this.initmap = queue;
        SimulateRunning.resetFinish();
        SimulateRunning.resetFinshNumber();
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
                scheduler = new CapacityScheduler(fullContainer, jobsQueues);
                break;
        }
    }

    public void setStepInterval(int stepInterval) {
        this.stepInterval = stepInterval;
    }

    public int getStepInterval() {
        return stepInterval;
    }

    public void runEnv(List<TwoTuple<Integer, TwoTuple<Job, String>>> jobInformation) {
        this.jobInformation.clear();
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
    public JSONObject predictOnly(List<TwoTuple<Integer, TwoTuple<Job, String>>> jobInformation,Map<String, TwoTuple<Integer, Integer>> queue){
        runEnv(jobInformation);
        JSONObject object = doAction(0,queue);
        return object;

    }

    public JSONObject doAction(int interal, Map<String, TwoTuple<Integer, Integer>> queue) {
        Map<String, JobsQueue> map = scheduler.getQueueMap();
        List<String> queueName = new ArrayList<String>(initmap.keySet());

        if (!queue.isEmpty()) {
            for (int i = 0; i < queueName.size(); i++) {
                map.get(queueName.get(i)).setMaxContainer(queue.get(queueName.get(i)).getSecond());
                map.get(queueName.get(i)).setInitContainer(queue.get(queueName.get(i)).getFirst());
            }
        }



        int totaltime = interal;
        List<JobsQueue> queues = new ArrayList<JobsQueue>(map.values());
        int firstFlag = 0;
        SimulateRunning.setPredictTime(0);
        while (true) {
            if (0 < jobInformation.size() && totaltime > 0) {
                int time = jobInformation.get(0).getFirst();

                scheduler = SimulateRunning.run(time, (CapacityScheduler) scheduler, firstFlag);
                firstFlag++;

                Job job = jobInformation.get(0).getSecond().getFirst();
                scheduler.addJob(jobInformation.get(0).getSecond().getSecond(), job);
                jobInformation.remove(0);
                totaltime -= time;
                scheduler = SimulateRunning.run(0, (CapacityScheduler) scheduler, firstFlag);

            } else {
                if (jobInformation.isEmpty()) {
                    scheduler = SimulateRunning.run(totaltime, (CapacityScheduler) scheduler, firstFlag);
                    firstFlag++;

                }
                break;

            }

        }
        SimulateRunning.setPredictTime(0);
        map = scheduler.getQueueMap();
        List<Job> runJob = new LinkedList<Job>();
        List<Job> waitJob = new LinkedList<Job>();

        List<String> name = new LinkedList<String>();
        JSONObject answer = new JSONObject();
        JSONArray array = new JSONArray();
        JSONArray array1 = new JSONArray();
        JSONArray array2 = new JSONArray();

        for (JobsQueue q : map.values()) {
            for (Job j : q.getQueueRun()) runJob.add(j);
            for (Job j : q.getQueueWait()) waitJob.add(j);
            JSONObject temp = new JSONObject();
            temp.put("used", q.getAllContainer());
            temp.put("left", q.getLeftContainer());
            array1.add(temp);
            JSONObject temp2 = new JSONObject();
            temp2.put("common", q.getInitContainer());
            temp2.put("max", q.getMaxContainer());
            array2.add(temp2);

            name.add(q.getName());
        }
        answer.put("source", array1);
        answer.put("stricts", array2);
        answer.put("avg", scheduler.getAvgTime());

        array = new JSONArray();
        for (Job j : runJob) {
            JSONObject object = new JSONObject();
            object.put("id", j.getId());
            object.put("queue", j.getQueueName());
            object.put("worktime", j.getDelay());
            object.put("container", j.getMaxContainer() - j.getContainer());
            array.add(object);
        }
        answer.put("runJob", array);
        array = new JSONArray();
        for (Job j : waitJob) {
            JSONObject object = new JSONObject();
            object.put("id", j.getId());
            object.put("queue", j.getQueueName());
            object.put("worktime", j.getDelay());
            object.put("container", j.getMaxContainer() - j.getContainer());
            array.add(object);
        }
        answer.put("waitJob", array);

        JSONObject f = new JSONObject();
        if (jobInformation.size() == 0 && scheduler.isEmpty()) {
            f.put("done", true);
        } else {
            f.put("done", false);
        }
        f.put("state", answer);

        return f;

    }


}
