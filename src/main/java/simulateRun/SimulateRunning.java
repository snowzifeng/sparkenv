package simulateRun;

import job.Job;
import jobqueue.JobsQueue;
import scheduler.CapacityScheduler;

import java.util.*;

class RunTool {
    List<Job> jobList_run;
    List<Job> jobList_wait;
    int container;
    int initContainer;
    int maxContainer;
    int nowContainer;

    public RunTool() {
        jobList_wait = new LinkedList<>();
        jobList_run = new LinkedList<>();
        container = 0;
        initContainer = 0;
        maxContainer = 0;
        nowContainer = 0;

    }
}

public class SimulateRunning {
    static RunTool[] runTools;
    static List<Job> jobList_run;
    static List<Job> jobList_wait;
    static int container;
    static List<Job> finish = new LinkedList<>();
    static CapacityScheduler scheduler;

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

    private static void resettime(int time, int index) {
//        System.out.println("enter the resettime");
        int flag = 0;
//        if (runTools[index].container < 0) {
//            flag = 1;
//        }
        for (int i = 0; i < runTools[index].jobList_run.size() - flag; i++) {

            int temp = runTools[index].jobList_run.get(i).getWorktimeLeft() - time;
            if (temp == 0) {
                runTools[index].container += runTools[index].jobList_run.get(i).getContainer();
                runTools[index].jobList_run.get(i).setWorktimeLeft(0);
                finish.add(runTools[index].jobList_run.get(i));

                runTools[index].jobList_run.remove(i);


                i--;
            } else {
                runTools[index].jobList_run.get(i).setWorktimeLeft(temp);
            }
        }
        //        System.out.println("out of reset");

    }

    public static int avgFinishTime(){
        int avg = 0;
        for(Job job:finish){
            avg += job.getTotaltime();
        }
        if (finish.isEmpty()){
            scheduler.setAvgTime(0);
        }else {
            scheduler.setAvgTime(avg / finish.size());
        }
        finish.clear();
        return avg;
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

            Collections.sort(jobList_run);
        }
        return;
    }

    private static int dojob(int time) {

        if (jobList_run.isEmpty()) {

            if (jobList_wait.isEmpty()) {
                return time;
            } else wait2run();

            if (jobList_wait.isEmpty()) return time;
            else wait2run();


        }
        if (jobList_run.isEmpty()) {
            return time;
        }

        int runtime = jobList_run.get(0).getWorktimeLeft();
        if (runtime >= time) {

            resettime(time);
            wait2run();
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

    public static JobsQueue run(int time, JobsQueue queue) {
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
        return queue;
    }


//    static List<TwoTuple<Integer, TwoTuple<Job, String>>> jobInformation = new LinkedList<TwoTuple<Integer, TwoTuple<Job, String>>>();

    public static CapacityScheduler run(int time, CapacityScheduler s) {
        scheduler = s;
        container = s.getContainerSize();
//        jobInformation = jobs;
        List<JobsQueue> queue = new ArrayList<>(scheduler.getQueueMap().values());
        runTools = new RunTool[queue.size()];

        for (int i = 0; i < queue.size(); i++) {
            runTools[i] = new RunTool();
            runTools[i].container = queue.get(i).getLeftContainer();
            runTools[i].maxContainer = queue.get(i).getMaxContainer();
            runTools[i].nowContainer = queue.get(i).getAllContainer();
            runTools[i].initContainer = queue.get(i).getInitContainer();
            runTools[i].jobList_run = queue.get(i).getQueueRun();
            runTools[i].jobList_wait = queue.get(i).getQueueWait();
            Collections.sort(runTools[i].jobList_run);

        }

//        System.out.println("time is :"+time);
        while (time > 0) {
            int minJob = minJob();
            if (minJob == -1) {
                return scheduler;
            } else
                wait2runMin();

//            System.out.println("pass time:-----"+time);
            time -= dojob(time, 1);

            if (runTools[0].jobList_run.isEmpty() && runTools[1].jobList_run.isEmpty()) {
                for (int i = 0; i < queue.size(); i++) {
                    runTools[i].nowContainer = queue.get(i).getInitContainer();
                    runTools[i].container = runTools[i].nowContainer;
                    Collections.sort(runTools[i].jobList_run);
                }

            }
        }
        Map<String, JobsQueue> queueMap = new HashMap<>();
        for (int i = 0; i < queue.size(); i++) {
            queue.get(i).setLeftContainer(runTools[i].container);
            queue.get(i).setMaxContainer(runTools[i].maxContainer);
            queue.get(i).setAllContainer(runTools[i].nowContainer);
//            queue.get(i).setUsedContainer(runTools[i].nowContainer);
            queue.get(i).setQueueRun(runTools[i].jobList_run);
            queue.get(i).setQueueWait(runTools[i].jobList_wait);

        }

        for (int i = 0; i < 2; i++) {
            int sum = 0;
            for(Job job:runTools[i].jobList_run){
                sum+=job.getContainer();
            }
            queue.get(i).setUsedContainer(sum);
            queueMap.put(queue.get(i).getName(), queue.get(i));
        }

        int avg = 0;
        for(Job job:finish){
            avg += job.getTotaltime();
        }
        if (finish.isEmpty()){
            scheduler.setAvgTime(0);
        }else {
            scheduler.setAvgTime(avg/finish.size());
        }
        finish.clear();

        scheduler.setQueueMap(queueMap);


        return scheduler;
    }

    private static void checkRun() {

        for (int i = 0; i < 2; i++) {
            int container = runTools[i].nowContainer;
            for (Job job : runTools[i].jobList_run) {
                container -= job.getContainer();
            }
            if (container != runTools[i].container) {
                runTools[i].container = container;
            }
        }
    }

    private static int dojob(int time, int length) {
        int times[] = new int[2];

        for (int i = 0; i < 2; i++) {
//            checkRun();

            if (runTools[i].jobList_run.isEmpty()) {
                if (runTools[i].jobList_wait.isEmpty()) {
                    times[i] = time;
                    continue;
                } else {

                    if (runTools[i].container > 0)
                        wait2runMin();
                }

            }
            if (runTools[i].jobList_run.isEmpty()) {
                times[i] = time;
                continue;
            }
            Collections.sort(runTools[i].jobList_run);
            int runtime = runTools[i].jobList_run.get(0).getWorktimeLeft();
            if (runtime >= time) {

                resettime(time, i);
                if (runTools[i].container > 0)
                    wait2runMin();
                times[i] = time;
                continue;
            } else {

                resettime(runtime, i);

                if (runTools[i].jobList_run.size() == 0) {

                    if (runTools[i].container > 0)
                        wait2runMin();
                }
                if (runTools[i].jobList_run.size() == 0) {
                    times[i] = time;
                    continue;
                }

                Job job = runTools[i].jobList_run.get(runTools[i].jobList_run.size() - 1);

                if (job.getMaxContainer() == job.getContainer()) {

                    if (runTools[i].container > 0)
                        wait2runMin();


                } else {

                    if (runTools[i].container > 0 && job.getContainer() + runTools[i].container < job.getMaxContainer()) {

                        job.setContainer(job.getContainer() + runTools[i].container);

                    } else if (runTools[i].container > 0) {

                        runTools[i].container -= (job.getMaxContainer() - job.getContainer());
                        job.setContainer(job.getMaxContainer());

                        if (runTools[i].container > 0)
                            wait2runMin();

                    }
                }
                Collections.sort(runTools[i].jobList_run);
                times[i] = runtime;
                continue;
            }


        }
        return Math.min(times[0], times[1]);
//        if (jobList_run.isEmpty()) {
//            if (jobList_wait.isEmpty()) {
//                return time;
//            } else wait2run();
//
//        }
//        if (jobList_run.isEmpty()) {
//            return time;
//        }
//
//        int runtime = jobList_run.get(0).getWorktimeLeft();
//        if (runtime >= time) {
//
//            resettime(time);
//            wait2run();
//            return time;
//        } else {
//
//            resettime(runtime);
//
//            if (jobList_run.size() == 0) {
//                wait2run();
//            }
//            if (jobList_run.size() == 0) {
//                return time;
//            }
//
//            Job job = jobList_run.get(jobList_run.size() - 1);
//
//            if (job.getMaxContainer() == job.getContainer()) {
//
//                wait2run();
//
//
//            } else {
//
//                if (job.getContainer() + container < job.getMaxContainer()) {
//
//                    job.setContainer(job.getContainer() + container);
//
//                } else {
//                    container -= (job.getMaxContainer() - job.getContainer());
//                    job.setContainer(job.getMaxContainer());
//                    wait2run();
//
//                }
//            }
//            Collections.sort(jobList_run);
//            return runtime;
//        }

    }


    private static void wait2runMin() {
        System.out.print("");
        if (runTools[0].container < 0) {
            if (runTools[1].container > 0 && runTools[0].nowContainer < runTools[0].initContainer) {
                runTools[0].container += runTools[1].container;
                runTools[0].nowContainer += runTools[1].container;
                runTools[1].nowContainer -= runTools[1].container;
                runTools[1].container = 0;

            }
            return;

        } else if (runTools[1].container < 0) {
            if (runTools[0].container > 0 && runTools[1].nowContainer < runTools[1].initContainer) {
                runTools[1].container += runTools[0].container;
                runTools[1].nowContainer += runTools[0].container;
                runTools[0].nowContainer -= runTools[0].container;
                runTools[0].container = 0;

            }
            return;
        }

        if (runTools[0].nowContainer == runTools[0].initContainer && runTools[1].nowContainer == runTools[1].initContainer) {
            for (int i = 0; i < 2; i++) {
                if (runTools[i].jobList_wait.isEmpty()) continue;
                Job temp = runTools[i].jobList_wait.get(0);
                while (runTools[i].container >= temp.getMaxContainer()) {
                    temp.setContainer(temp.getMaxContainer());
                    runTools[i].container -= temp.getMaxContainer();
                    runTools[i].jobList_run.add(temp);
                    runTools[i].jobList_wait.remove(0);
                    if (!runTools[i].jobList_wait.isEmpty()) {
                        temp = runTools[i].jobList_wait.get(0);
                    } else break;
                }
            }
//            没有作业，都有作业，一个有作业
            if (runTools[0].jobList_wait.isEmpty() && runTools[1].jobList_wait.isEmpty()) {
                return;
            } else if (!runTools[0].jobList_wait.isEmpty() && !runTools[1].jobList_wait.isEmpty()) {
                for (int i = 0; i < 2; i++) {
                    Job temp;
                    if (runTools[i].container >= 2) {
                        if (!runTools[i].jobList_wait.isEmpty()) {
                            temp = runTools[i].jobList_wait.get(0);
                            temp.setContainer(runTools[i].container);
                            runTools[i].jobList_run.add(temp);

                            runTools[i].jobList_wait.remove(0);
                            runTools[i].container = 0;
                        }
                    }
                }
            } else {
                int max;
                int min;
                if (runTools[0].jobList_wait.isEmpty()) {
                    max = 1;
                    min = 0;
                } else {
                    max = 0;
                    min = 1;
                }
                int leftContainer = Math.min(runTools[min].container + runTools[max].container, runTools[max].maxContainer - runTools[max].nowContainer + runTools[max].container);
                runTools[max].nowContainer = runTools[max].nowContainer - runTools[max].container + leftContainer;
                runTools[min].container = runTools[min].container - leftContainer + runTools[max].container;
                runTools[min].nowContainer = runTools[min].nowContainer - (leftContainer - runTools[max].container);
                Job temp = runTools[max].jobList_wait.get(0);
                while (leftContainer >= temp.getMaxContainer()) {
                    temp.setContainer(temp.getMaxContainer());
                    leftContainer -= temp.getMaxContainer();
                    runTools[max].jobList_run.add(temp);
                    runTools[max].jobList_wait.remove(0);
                    if (!runTools[max].jobList_wait.isEmpty()) {
                        temp = runTools[max].jobList_wait.get(0);
                    } else break;
                }
                if (leftContainer >= 2) {
                    if (!runTools[max].jobList_wait.isEmpty()) {
                        temp = runTools[max].jobList_wait.get(0);
                        temp.setContainer(leftContainer);
                        runTools[max].jobList_run.add(temp);
                        runTools[max].jobList_wait.remove(0);
                        leftContainer = 0;
                    }

                }
                runTools[max].container = 0;
                runTools[max].nowContainer -= leftContainer;
                runTools[min].container += leftContainer;
                runTools[min].nowContainer += leftContainer;
                System.out.print("");

            }


        } else {
            int min;
            int max;
            if (runTools[0].nowContainer < runTools[0].initContainer) {
                max = 1;
                min = 0;
            } else {
                max = 0;
                min = 1;
            }

            if (!runTools[min].jobList_wait.isEmpty()) {
                Job temp = runTools[min].jobList_wait.get(0);
                int maxContainer = runTools[min].initContainer - runTools[min].nowContainer + runTools[min].container;
                int base = 0;

                while (maxContainer >= temp.getMaxContainer()) {
                    temp.setContainer(temp.getMaxContainer());
                    maxContainer -= temp.getMaxContainer();
                    base += temp.getMaxContainer();
                    runTools[min].jobList_run.add(temp);
                    runTools[min].jobList_wait.remove(0);
                    if (!runTools[min].jobList_wait.isEmpty()) {
                        temp = runTools[min].jobList_wait.get(0);
                    } else break;
                }

                if (!runTools[min].jobList_wait.isEmpty()) {
                    int t = runTools[max].container - (runTools[min].initContainer - runTools[min].nowContainer);
                    if (t > 0) {
                        maxContainer += t;
                    } else {
                        t = 0;
                    }

//                    runTools[min].nowContainer = runTools[min].initContainer + t;
//                    runTools[max].nowContainer = runTools[max].initContainer - t;
//                    runTools[max].container = 0;

                    while (maxContainer >= temp.getMaxContainer()) {
                        base += temp.getMaxContainer();
                        temp.setContainer(temp.getMaxContainer());
                        maxContainer -= temp.getMaxContainer();
                        runTools[min].jobList_run.add(temp);
                        runTools[min].jobList_wait.remove(0);
                        if (!runTools[min].jobList_wait.isEmpty()) {
                            temp = runTools[min].jobList_wait.get(0);
                        } else break;
                    }
                    if (maxContainer >= 2) {
                        if (!runTools[min].jobList_wait.isEmpty()) {
                            temp = runTools[min].jobList_wait.get(0);
                            temp.setContainer(maxContainer);
                            runTools[min].jobList_run.add(temp);
                            base += maxContainer;
                            runTools[min].jobList_wait.remove(0);

                        }
                    }
                    System.out.print("");

//
//                    runTools[min].container = maxContainer;
//                    runTools[max].container += maxContainer;
//                    runTools[max].nowContainer += maxContainer;
                }
//                else {
//
//                    runTools[min].container = maxContainer;
//                    runTools[max].container -= runTools[min].initContainer - runTools[min].nowContainer;
//                    runTools[max].nowContainer = runTools[max].initContainer;
//                    runTools[min].nowContainer = runTools[min].initContainer;
////                    runTools[min].container = maxContainer;
////                    runTools[max].container -= runTools[min].initContainer - runTools[min].nowContainer;
////                    runTools[max].nowContainer = runTools[max].initContainer;
////                    runTools[min].nowContainer = runTools[min].initContainer;
//                }

                if (base > runTools[min].container) {
                    runTools[min].nowContainer += (base - runTools[min].container);
                    runTools[max].nowContainer -= (base - runTools[min].container);
                    runTools[max].container -= (base - runTools[min].container);
                    runTools[min].container = 0;
                } else {

                    runTools[min].container = runTools[min].container - base;
                }
//                runTools[min].nowContainer += base;
//                runTools[min].container = base > runTools[min].container ? 0 : runTools[min].container - base;
//                runTools[max].nowContainer -= base;
//                runTools[max].container -= base;
                System.out.print("");
            }
            if (runTools[max].jobList_wait.isEmpty()) return;

            int addContainer = runTools[max].container;

            if (runTools[min].container > 0) {
                addContainer += runTools[min].container;
            }
            int orgin = addContainer;

            Job temp = runTools[max].jobList_wait.get(0);
            while (addContainer >= temp.getMaxContainer()) {
                temp.setContainer(temp.getMaxContainer());
                addContainer -= temp.getMaxContainer();
                runTools[max].jobList_run.add(temp);
                runTools[max].jobList_wait.remove(0);
                if (!runTools[max].jobList_wait.isEmpty()) {
                    temp = runTools[max].jobList_wait.get(0);
                } else break;
            }

            if (!runTools[max].jobList_wait.isEmpty()) {

                if (addContainer >= 2) {
                    temp = runTools[max].jobList_wait.get(0);
                    temp.setContainer(addContainer);
                    runTools[max].jobList_run.add(temp);

                    runTools[max].jobList_wait.remove(0);
                    addContainer = 0;
                }
            }

            addContainer = orgin - addContainer;
            if (addContainer > runTools[max].container) {
                runTools[max].nowContainer += addContainer - runTools[max].container;
                runTools[min].nowContainer -= addContainer - runTools[max].container;
                runTools[min].container -= addContainer - runTools[max].container;
                runTools[max].container = 0;
            } else {
                runTools[max].container = runTools[max].container - addContainer;
            }
            System.out.print("");


        }
//        if (runTools[0].container < 0) {
//            if (runTools[1].container > 0) {
//                runTools[0].container += runTools[1].container;
//                runTools[0].nowContainer += runTools[1].container;
//                runTools[1].nowContainer -= runTools[1].container;
//                runTools[1].container = 0;
//
//            }
//
//        } else if (runTools[1].container < 0) {
//            if (runTools[0].container > 0) {
//                runTools[1].container += runTools[0].container;
//                runTools[1].nowContainer += runTools[0].container;
//                runTools[0].nowContainer -= runTools[0].container;
//                runTools[0].container = 0;
//
//            }
//        }


        System.out.print("");

    }

    private static void wait2run(int index) {

//        多队列，未完成。
        int changedContainer = 0;
        int beyondContainer = 0;
        int needMore = 0;
        Set<Integer> maxQueueSet = new HashSet<>();
        for (int i = 0; i < runTools.length; i++) {
            if (runTools[i].nowContainer > runTools[i].initContainer) {
                maxQueueSet.add(i);
                beyondContainer += (runTools[i].nowContainer - runTools[i].initContainer);
            } else {
                changedContainer += runTools[i].container;
            }
        }

        for (int i = 0; i < runTools.length; i++) {
            if (maxQueueSet.contains(i)) continue;
            if (runTools[i].jobList_wait.isEmpty()) continue;
            Job temp = runTools[i].jobList_wait.get(0);

            while (runTools[i].container >= temp.getMaxContainer()) {
                temp.setContainer(temp.getMaxContainer());
                runTools[i].container -= temp.getMaxContainer();
                runTools[i].jobList_run.add(temp);
                runTools[i].jobList_wait.remove(0);
                if (!runTools[i].jobList_wait.isEmpty()) {
                    temp = runTools[i].jobList_wait.get(0);
                } else break;
            }

            if (runTools[i].nowContainer < runTools[i].initContainer && !runTools[i].jobList_wait.isEmpty()) {
                int addContainer = runTools[i].initContainer - runTools[i].nowContainer;

                while (addContainer >= temp.getMaxContainer()) {
                    temp.setContainer(temp.getMaxContainer());
                    addContainer -= temp.getMaxContainer();
                    runTools[i].jobList_run.add(temp);
                    runTools[i].jobList_wait.remove(0);
                    if (!runTools[i].jobList_wait.isEmpty()) {
                        temp = runTools[i].jobList_wait.get(0);
                    } else break;
                }

                if (addContainer >= 2) {
                    if (!runTools[i].jobList_wait.isEmpty()) {
                        needMore += runTools[i].initContainer - runTools[i].nowContainer;
                        runTools[i].nowContainer = runTools[i].initContainer;
                        runTools[i].container = 0;
                    } else {
                        needMore += runTools[i].initContainer - addContainer - runTools[i].nowContainer;
                        runTools[i].nowContainer = runTools[i].initContainer - addContainer;
                        runTools[i].container = 0;
                    }
                }

            } else {

                if (runTools[i].container >= 2) {
                    if (!runTools[i].jobList_wait.isEmpty()) {
                        temp = runTools[i].jobList_wait.get(0);
                        temp.setContainer(runTools[i].container);
                        runTools[i].jobList_run.add(temp);

                        runTools[i].jobList_wait.remove(0);
                        runTools[i].container = 0;
                    }
                }
            }


        }

        for (int i : maxQueueSet) {
            if (needMore > 0) {
                if (needMore > runTools[i].nowContainer - runTools[i].initContainer) {
                    int temp = runTools[i].nowContainer - runTools[i].initContainer;
                    runTools[i].container -= temp;
                    runTools[i].nowContainer = runTools[i].initContainer;
                    needMore -= temp;
                } else {

                }
            }
        }


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

            Collections.sort(jobList_run);
        }
        return;
    }

    private static int minJob() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < runTools[i].jobList_run.size(); j++) {
                min = Math.min(min, runTools[i].jobList_run.get(j).getWorktimeLeft());
            }
        }

        if (min == Integer.MAX_VALUE) {
            int flag = 0;
            for (int i = 0; i < 2; i++) {
                if (!runTools[i].jobList_wait.isEmpty()) flag++;
            }
            if (flag == 0) {
                return -1;
            }
        }

        return min;
    }
}
