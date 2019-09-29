package synchronizemodel.socketmodel.reset;

import java.util.List;

public class ResetArgument {

    private List<Workload> workloads;

    private int numContainer;

    private int stepInterval;

    public ResetArgument(List<Workload> workloads, int numContainer, int stepInterval) {
        this.workloads = workloads;
        this.numContainer = numContainer;
        this.stepInterval = stepInterval;
    }

    public List<Workload> getWorkloads() {
        return workloads;
    }

    public int getNumContainer() {
        return numContainer;
    }

    public int getStepInterval() {
        return stepInterval;
    }

}
