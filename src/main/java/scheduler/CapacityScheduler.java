package scheduler;

import job.Job;
import jobqueue.JobsQueue;

import java.util.*;


public class CapacityScheduler extends AbstractScheduler {

    int containerSize;

    Map<String, JobsQueue> queueMap = new HashMap<String, JobsQueue>();
    Set<String> maxQueue = new HashSet<String>();
    Set<String> minQueue = new HashSet<String>();

    public CapacityScheduler(int containerNumber, JobsQueue[] queues) {
        this.containerSize = containerNumber;
        for (JobsQueue temp : queues) {
            this.queueMap.put(temp.getName(), temp);
        }
        this.containerSize = containerNumber;
    }

    public void initQueue(String[] name, int[] container) {

        for (int i = 0; i < name.length; i++) {
            JobsQueue queue = new JobsQueue(name[i], container[i]);
        }
    }

    String getState() {

        return null;
    }

    public void doAction(String action) {

    }

    void addJob(int index, Job job) {

    }

    void remove(String name) {
        JobsQueue queue = queueMap.get(name);

    }

    void addJob(String name, Job job) {
        JobsQueue queue = queueMap.get(name);
        queue.add(job);
        if (queue.getLeftContainer() < job.getMaxContainer()) {
            if (queue.getAllContainer() + 1 >= queue.getMaxContainer()) {
                return;
            }
            List<String> queues = new ArrayList<String>(queueMap.keySet());
            Set<JobsQueue> leftqueue = new HashSet<JobsQueue>();
            int leftTotal = 0;
            for (String temp : queues) {
                JobsQueue q = queueMap.get(temp);
                if (q.getLeftContainer() > 0) {
                    leftTotal += q.getLeftContainer();
                    leftqueue.add(q);
                }
            }
            // 有剩余container 将其他队列的资源减少，分配到需要到队列上
            if (leftTotal >= 2) {
                int before = queue.getAllContainer();
                int nowContainer = Math.min(leftTotal + queue.getAllContainer(), Math.min(queue.getMaxContainer(), queue.getUsedContainer() + job.getMaxContainer()));
                queue.setLeftContainer(nowContainer - queue.getUsedContainer());
                queue.setAllContainer(nowContainer);
                before -= nowContainer;

                if (nowContainer > queue.getInitContainer() && queue.getMaxContainer() != queue.getInitContainer()) {
                    maxQueue.add(name);
                }
                for (String temp : queues) {
                    if (before <= 0) break;
                    JobsQueue q = queueMap.get(temp);
                    if (q.getLeftContainer() > 0) {
                        if (before < q.getLeftContainer()) {
                            q.setAllContainer(q.getAllContainer() - before);
                            before = 0;
                        } else {
                            before -= q.getLeftContainer();
                            q.setAllContainer(q.getAllContainer() - q.getLeftContainer());
                        }

                    }
                }


                return;

            //  如果没有剩余的container，看是否有超额分配的queue，将他们的资源减少，分配给资源少于初始值的队列（如等于初始值，则资源配置不变）
            } else {
                if (maxQueue.isEmpty()) {
                    return;
                } else {
                    if (queue.getInitContainer() <= queue.getAllContainer()) {

                    } else {
                        int changeContainer = queue.getInitContainer() - queue.getAllContainer();
                        queue.setAllContainer(queue.getInitContainer());
                        queue.setLeftContainer(queue.getLeftContainer() + changeContainer);

                        List<String> max = new ArrayList<String>(maxQueue);
                        for (String qname : max) {
                            JobsQueue q = queueMap.get(qname);
                            if (changeContainer >= q.getAllContainer() - q.getInitContainer()) {
                                changeContainer -= q.getAllContainer() - q.getInitContainer();
                                q.setAllContainer(q.getInitContainer());
                            } else {
                                q.setAllContainer(q.getAllContainer() - changeContainer);
                                changeContainer = 0;
                            }
                            if(q.getAllContainer() == q.getInitContainer()){
                                maxQueue.remove(q.getName());
                            }

                            if (changeContainer <= 0) break;
                        }

                    }

                }

            }

        } else {
            return;
        }

    }
}
