package util;

import synchronizemodel.socketmodel.simulation.QueueArgument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueAdaptionUtil {

    public static Map<String, TwoTuple<Integer, Integer>> convert(List<QueueArgument> queueArguments) {
        Map<String, TwoTuple<Integer, Integer>> map = new HashMap<>();
        for (QueueArgument argument : queueArguments) {
            map.put(argument.getName(), new TwoTuple<>(argument.getCapacity(), argument.getMaxCapacity()));
        }
        return map;
    }

}
