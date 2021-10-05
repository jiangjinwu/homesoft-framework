package top.homesoft.syncData.decorator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.homesoft.framework.utils.DateUtil;
import top.homesoft.syncData.config.TransferConfig;
import top.homesoft.syncData.mq.domain.DataMessage;
import top.homesoft.syncData.mq.producer.SyncDataProducer;
import top.homesoft.syncData.mybatis.FieldMappingDTO;
import top.homesoft.syncData.mybatis.TableTransferDTO;
import top.homesoft.syncData.vo.SyncDataMessage;

@Slf4j
@Service
public class SyncDataDecorator {
    private final TransferConfig transferConfig;

    private final SyncDataProducer syncDataProducer;

    public SyncDataDecorator(TransferConfig transferConfig, SyncDataProducer syncDataProducer) {
        this.transferConfig = transferConfig;
        this.syncDataProducer = syncDataProducer;
    }

    public void parseHandle(DataMessage message) {
        String tableName = String.format("%s.%s", new Object[]{message.getDatabase(), message.getTable()}).toUpperCase();
        logger.info(">>>>>>>监听处理{},{},{}<<<<<<<<", new Object[]{tableName, message.getType(), Boolean.valueOf(message.isDdl())});
        if (message.isDdl()) {
            logger.warn("暂不处理DDL语句{},{}", tableName, message.getType());
            return;
        }
        SyncDataMessage model = new SyncDataMessage();
        model.setType(message.getType().name());
        model.setSource(Integer.valueOf(1));
        model.setTableName(tableName);
        JSONArray list = message.getData();
        if (CollectionUtils.isEmpty((Collection) list))
            return;
        int len = list.size();
        for (int i = 0; i < len; i++) {
            JSONObject body = list.getJSONObject(i);
            int finalI = i;
            JSONObject updated = Optional.<JSONArray>ofNullable(message.getOld()).map(data -> data.getJSONObject(finalI)).orElse(new JSONObject());
            parseColumn(body, tableName);
            parseUpdatedColumn(body, updated, tableName);
            model.setBody(body);
            model.setUpdated(updated);
            String routeKey = Optional.<String>ofNullable(routeKey(tableName)).orElse(StringUtils.replaceAll(tableName, "_", ".").toLowerCase());
            this.syncDataProducer.sendSyncDataMessage(model, routeKey);
        }
    }

    public void parseUpdatedColumn(JSONObject body, JSONObject updated, String tableName) {
        parseColumn(updated, tableName);
        updated.forEach((k, v) -> {
            if (ObjectUtil.isEmpty(v))
                updated.put(k, body.get(k));
        });
    }

//    public void parseColumn(JSONObject columns, String tableName) {
//        if (CollectionUtils.isEmpty((Map) columns))
//            return;
//        List<FieldMappingDTO> fieldMappingDTOList = Optional.<Map>ofNullable(this.transferConfig.getTableField()).map(cfg -> matcherTableName(cfg, tableName)).map(TableTransferDTO::getFieldMappings).orElse(new ArrayList<>(0));
//        if (CollectionUtils.isEmpty(fieldMappingDTOList))
//            return;
//        fieldMappingDTOList.forEach(fieldMapping -> {
//            Object val = columns.get(fieldMapping.getSource());
//            if (StringUtils.isNotBlank(fieldMapping.getDefValue()))
//                val = processDefaultVal(fieldMapping.getDefValue());
//            if (ObjectUtil.isEmpty(val))
//                return;
//            columns.put(fieldMapping.getTarget(), val);
//            if (!StringUtils.equals(fieldMapping.getSource(), fieldMapping.getTarget()))
//                columns.remove(fieldMapping.getSource());
//        });
//    }

    public void parseColumn(JSONObject columns, String tableName) {
        if (!CollectionUtils.isEmpty(columns)) {
            List<FieldMappingDTO> fieldMappingDTOList = (List)Optional.ofNullable(this.transferConfig.getTableField()).map((cfg) -> {
                return this.matcherTableName(cfg, tableName);
            }).map(TableTransferDTO::getFieldMappings).orElse(new ArrayList(0));
            if (!CollectionUtils.isEmpty(fieldMappingDTOList)) {
                fieldMappingDTOList.forEach((fieldMapping) -> {
                    Object val = columns.get(fieldMapping.getSource());
                    if (StringUtils.isNotBlank(fieldMapping.getDefValue())) {
                        val = this.processDefaultVal(fieldMapping.getDefValue());
                    }

                    if (!ObjectUtil.isEmpty(val)) {
                        columns.put(fieldMapping.getTarget(), val);
                        if (!StringUtils.equals(fieldMapping.getSource(), fieldMapping.getTarget())) {
                            columns.remove(fieldMapping.getSource());
                        }

                    }
                });
            }
        }
    }

//    private String routeKey(String tableName) {
//        return Optional.<Map>ofNullable(this.transferConfig.getTableField())
//                .map(config -> matcherTableName(config, tableName))
//                .map(TableTransferDTO::getRouteKey)
//                .orElse(null);
//    }

    private String routeKey(String tableName) {
        return (String)Optional.ofNullable(this.transferConfig.getTableField()).map((config) -> {
            return this.matcherTableName(config, tableName);
        }).map(TableTransferDTO::getRouteKey).orElse(null);
    }

    private TableTransferDTO matcherTableName(Map<String, TableTransferDTO> tableField, String tableName) {
        TableTransferDTO dto = Optional.<TableTransferDTO>ofNullable(tableField.get(tableName.toLowerCase())).orElse(null);
        if (null != dto)
            return dto;
        for (Map.Entry<String, TableTransferDTO> entry : tableField.entrySet()) {
            String key = entry.getKey();
            Pattern pattern = Pattern.compile(key, 2);
            Matcher matcher = pattern.matcher(tableName);
            if (matcher.matches())
                return entry.getValue();
        }
        return null;
    }

    private Object processDefaultVal(String defValue) {
        if (StringUtils.equalsIgnoreCase(defValue, "$DATETIME$"))


            return DateUtil.getCurrentDatetimeStr();
        return defValue;
    }
}

