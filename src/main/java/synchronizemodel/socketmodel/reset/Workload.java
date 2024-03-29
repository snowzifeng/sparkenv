package synchronizemodel.socketmodel.reset;

public class Workload {

    private String appType;

    private int dataSize;

    private int interval;

    private String queue;

    private String id;

    public Workload(String appType, int dataSize, int interval, String queue, String id) {
        this.appType = appType;
        this.dataSize = dataSize;
        this.interval = interval;
        this.queue = queue;
        this.id = id;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getId() {
        return this.id;
    }

}
