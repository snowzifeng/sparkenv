package synchronizemodel.socketmodel.reset;

import synchronizemodel.socketmodel.simulation.QueueArgument;

import java.util.List;

public class ResetArgument {

    private List<QueueArgument> queueArguments;

    private List<Workload> workloads;

    private int numContainer;

    private int stepInterval;

    public ResetArgument(List<QueueArgument> queueArguments, List<Workload> workloads, int numContainer, int stepInterval) {
        this.queueArguments = queueArguments;
        this.workloads = workloads;
        this.numContainer = numContainer;
        this.stepInterval = stepInterval;
    }

    public List<QueueArgument> getQueueArguments() {
        return queueArguments;
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
