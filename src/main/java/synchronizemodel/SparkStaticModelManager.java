package synchronizemodel;

import synchronizemodel.socketmodel.reset.SparkModel;
import util.CsvUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SparkStaticModelManager {

    private static final String MODEL_CSV_FILENAME = "model-time-costs.csv";

    private List<SparkModel> sparkModels = new ArrayList<>();

    public void loadDataFromFile() throws IOException {
        List<List<String>> lists = CsvUtil.readCsv(MODEL_CSV_FILENAME);
        for (List<String> stringList : lists) {
            SparkModel model = buildFromCsv(stringList);
            sparkModels.add(model);
        }
    }

    public Optional<SparkModel> findModel(final String appType, final int dataSize) {
        return sparkModels.stream()
                .filter(model -> (model.getAppType().equals(appType) && model.getDataSize() == dataSize))
                .findFirst();
    }

    private SparkModel buildFromCsv(List<String> stringList) {
        String appType = stringList.get(0);
        int dataSize = Integer.parseInt(stringList.get(1));
        int numContainer = Integer.parseInt(stringList.get(2));
        int timeCost = Integer.parseInt(stringList.get(3));
        return new SparkModel(appType, dataSize, numContainer, timeCost);
    }

}
