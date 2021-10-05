package top.homesoft.syncData.vo;

import lombok.Data;
import top.homesoft.syncData.utils.UUIDUtils;

@Data
public class SyncDataMessage {
    private String id = UUIDUtils.getUUID();

    private String type;

    private Integer source;

    private String tableName;

    private Object body;

    private Object updated;
}
