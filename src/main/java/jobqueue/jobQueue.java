package jobqueue;

import java.util.List;
import job.job;
public class jobQueue {
    List<job> queue_run;
    List<job> queue_wait;
    String name;
    int all_container;
    int used_container;
    int left_container;


    public jobQueue(String name,int all_container){
        this.name = name;
        this.all_container = all_container;
    }
}
