package synchronizemodel.socketmodel.simulation;

public class QueueArgument {

    private String name;

    private int capacity;

    private int maxCapacity;

    public QueueArgument(String name, int capacity, int maxCapacity) {
        this.name = name;
        this.capacity = capacity;
        this.maxCapacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

}
