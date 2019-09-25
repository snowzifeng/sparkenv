package jobqueue;

import java.util.List;

import job.Job;

public class jobQueue {
    List<Job> Queue;

    String Name;
    int All_container;
    int Used_container;
    int Left_container;


    public jobQueue(String name, int all_container) {
        this.Name = name;
        this.All_container = all_container;
    }

    public jobQueue(String name) {
        this.Name = name;
    }

    public void setAll_container(int all_container) {
        All_container = all_container;
    }

    public int getUsed_container() {
        return Used_container;
    }

    public int getAll_container() {
        return All_container;
    }

    public int getLeft_container() {
        return Left_container;
    }

    public List<Job> getQueue() {
        return Queue;
    }


}
