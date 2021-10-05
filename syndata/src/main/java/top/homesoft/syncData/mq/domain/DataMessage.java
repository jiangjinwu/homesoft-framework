package top.homesoft.syncData.mq.domain;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import top.homesoft.syncData.constants.DMLType;

@Data
public class DataMessage {
    private JSONArray data;

    private JSONArray old;

    private String database;

    private String table;

    @JSONField(name = "type")
    private DMLType type;

    private boolean ddl;
}
