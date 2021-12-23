package top.homesoft.framework.log.domain.systemlog.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;
import top.homesoft.framework.log.domain.systemlog.repository.mapper.SystemLogMapper;

@Repository
public class SystemLogRepositoryImpl implements SystemLogRepository {
    @Autowired
    SystemLogMapper systemLogMapper;
    @Override
    public Boolean saveLog(SystemLogPO systemLogPO) {
        return systemLogMapper.insert(systemLogPO)>0;
    }

    @Override
    public Boolean updateLog(SystemLogPO systemLogPO) {
        return systemLogMapper.updateById(systemLogPO)>0;
    }
}
