package top.homesoft.syncData.mybatis;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldMappingDTO   implements Serializable {
    private String source;

    private String target;

    private String type;

    private String defValue;
}
