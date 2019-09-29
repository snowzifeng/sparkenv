package synchronizemodel.socketmodel.reset;

public class SparkModel {

    private String appType;

    private int dataSize;

    private int numContainer;

    private int timeCost;

    public SparkModel(String appType, int dataSize, int numContainer, int timeCost) {
        this.appType = appType;
        this.dataSize = dataSize;
        this.numContainer = numContainer;
        this.timeCost = timeCost;
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

    public int getNumContainer() {
        return numContainer;
    }

    public void setNumContainer(int numContainer) {
        this.numContainer = numContainer;
    }

    public int getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

}
