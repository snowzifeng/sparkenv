package synchronizemodel.socketmodel;

import com.alibaba.fastjson.JSONObject;
import util.TwoTuple;

public interface IHandler {

    JSONObject handle(final JSONObject data);

}
