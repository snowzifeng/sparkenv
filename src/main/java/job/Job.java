package job;

public class Job {
    int container = 0;
    int maxcontainer = 0;
    int allocatedcontainer = 0;
    String queuename;
    int worktime;
    int worktime_left;

    public int getContainer() {
        return container;
    }

    public int getMaxcontainer() {
        return maxcontainer;
    }

    public int getAllocatedcontainer() {
        return allocatedcontainer;
    }

    public String getQueuename() {
        return queuename;
    }

    public int getWorktime() {
        return worktime;
    }

    public int getWorktime_left() {
        return worktime_left;
    }

    public void setContainer(int container) {
        this.container = container;
    }

    public void setMaxcontainer(int maxcontainer) {
        this.maxcontainer = maxcontainer;
    }

    public void setAllocatedcontainer(int allocatedcontainer) {
        this.allocatedcontainer = allocatedcontainer;
    }

    public void setQueuename(String queuename) {
        this.queuename = queuename;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    public void setWorktime_left(int worktime_left) {
        this.worktime_left = worktime_left;
    }
}
