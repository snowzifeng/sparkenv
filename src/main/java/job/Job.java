package job;

public class Job implements Comparable<Job> {
    int container = 0;
    int maxContainer = 0;
    int allocatedContainer = 0;
    String queueName;
    int worktime;
    int worktimeLeft;
    int fullWorkload;
    int basetime;
    boolean finishStartPart;
    int totaltime = 0;
    int delay = 0;
    String id;

    public Job(int maxContainer, int basetime, int worktime, String id) {
        this.basetime = basetime;
        this.maxContainer = maxContainer;
        this.basetime = basetime;
        this.fullWorkload = maxContainer * worktime;
        this.worktime = worktime + basetime;
        this.worktimeLeft = worktime + basetime;
        this.finishStartPart = false;
        this.container = 0;
        this.delay = 0;
        this.id = id;


    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public String getId() {
        return id;
    }

    public int getContainer() {
        return container;
    }

    public int compareTo(Job o) {
        return this.worktimeLeft > o.worktimeLeft ? 1 : (this.worktimeLeft == o.worktimeLeft ? 0 : -1);

    }

    public int getMaxContainer() {
        return maxContainer;
    }

    public int getAllocatedContainer() {
        return allocatedContainer;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getWorktime() {
        return worktime;
    }

    public int getWorktimeLeft() {
        return worktimeLeft;
    }

    public int getBasetime() {
        return basetime;
    }

    public void setContainer(int container) {

//        System.out.println("start : "+this.getWorktimeLeft());
        if (this.finishStartPart) {
            this.fullWorkload = this.worktimeLeft * this.container;
            this.container = container;
            this.worktimeLeft = (this.fullWorkload + this.container - 1) / this.container;

        } else {
            if (this.worktime - this.worktimeLeft >= this.basetime) {
                if (this.container == 0) {
                    this.finishStartPart = true;
                    this.worktimeLeft = this.worktimeLeft * this.maxContainer / container;
                    this.container = container;
                    return;
                }
//                System.out.println("basetime: "+this.basetime+" worktime: "+this.worktime+" worktimeleft: "+this.worktimeLeft);
                this.finishStartPart = true;

                this.worktimeLeft = this.worktimeLeft * this.container / container;

                this.container = container;
//                System.out.println("end : "+ this.getWorktimeLeft());

            } else {
                int passTime = this.basetime - (this.worktime - this.worktimeLeft);
                if (this.container == 0) {

                    this.worktimeLeft = (this.worktime - this.basetime) * this.maxContainer / container + this.worktimeLeft - (this.worktime - this.basetime);
                    this.container = container;

                } else {
                    this.worktimeLeft = (this.worktime - this.basetime) * this.container / container + this.worktimeLeft - (this.worktime - this.basetime);
                    this.container = container;
                }
                this.basetime = passTime;
//                System.out.println("passtime : "+passTime);
                this.worktime = this.worktimeLeft;

            }
        }



    }

    public void setMaxContainer(int maxContainer) {
        this.maxContainer = maxContainer;
    }

    public void setAllocatedcontainer(int allocatedcontainer) {
        this.allocatedContainer = allocatedcontainer;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    public void setWorktimeLeft(int worktimeLeft) {
//        if (this.worktimeLeft < worktimeLeft) {
//            System.err.println("small----------");
////            System.exit(0);
//        }
        this.totaltime += (this.worktimeLeft - worktimeLeft);
        this.worktimeLeft = worktimeLeft;
//        System.out.println("totaltime: " + this.totaltime);
    }

    public int getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(int totaltime) {
        this.totaltime = totaltime;
    }

    @Override
    public Job clone() {
        Job job = new Job(this.maxContainer,this.basetime,this.worktime,this.id);
        job.setAllocatedcontainer(this.getAllocatedContainer());
        job.container= this.container;
        job.setQueueName(this.getQueueName());
        job.worktimeLeft= this.worktimeLeft;
        job.setTotaltime(this.getTotaltime());
        job.setDelay(this.getDelay());
        return job;
    }
}
