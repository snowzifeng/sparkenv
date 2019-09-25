package synchronizemodel.socketmodel;

import com.alibaba.fastjson.JSONObject;
import util.TwoTuple;

public interface IHandler {

    // <字符串类型的返回值，是否继续运行>
    TwoTuple<JSONObject, Boolean> handle(final JSONObject data);

}
