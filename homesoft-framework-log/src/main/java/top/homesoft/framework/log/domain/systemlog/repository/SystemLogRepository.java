package top.homesoft.framework.log.domain.systemlog.repository;

import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;

public interface SystemLogRepository {

    Boolean saveLog(SystemLogPO systemLogPO);


    Boolean updateLog(SystemLogPO systemLogPO);
}
