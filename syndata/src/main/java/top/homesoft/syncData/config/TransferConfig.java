package top.homesoft.syncData.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.homesoft.syncData.mybatis.TableTransferDTO;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j

@Configuration
@ConfigurationProperties(prefix = "transfer")
public class TransferConfig {
    private List<TableTransferDTO> tables;

    private Map<String, TableTransferDTO> tableField;

    private String blackTables;



    @PostConstruct
    public void init() {
        try {
            this.tableField = (Map<String, TableTransferDTO>)this.tables.stream().collect(Collectors.toMap(TableTransferDTO::getTableName, a -> a, (k1, k2) -> k1));
        } catch (Exception ex) {
            logger.error("Data Table Conversion Configuration Fail, Check Configuration");
            System.exit(-1);
        }
    }

    public List<String> loadTables() {
        return (List<String>)this.tables.stream().map(TableTransferDTO::getTableName)
                .collect(Collectors.toList());
    }

}
