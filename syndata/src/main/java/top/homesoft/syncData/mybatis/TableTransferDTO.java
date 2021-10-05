package top.homesoft.syncData.mybatis;

import lombok.Data;

import java.util.List;

@Data
public class TableTransferDTO {
    private String tableName;

    private String routeKey;

    private List<FieldMappingDTO> fieldMappings;

}
