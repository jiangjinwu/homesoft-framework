package top.homesoft.framework.log.domain.systemlog.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;
import top.homesoft.framework.log.domain.systemlog.repository.mapper.SystemLogMapper;
import top.homesoft.framework.log.domain.systemlog.service.SystemLogService;

import java.util.Objects;

@Repository
public class SystemLogRepositoryImpl implements SystemLogRepository {
    @Autowired
    SystemLogMapper systemLogMapper;

	@Autowired
	SystemLogService systemLogService;
    @Override
    public Boolean saveLog(SystemLogPO systemLogPO) {
//		if(Objects.nonNull(systemLogPO)){
//			if( Objects.nonNull(systemLogPO.getLogId())){
//				return systemLogMapper.updateById(systemLogPO)>0;
//			}else{
//				return systemLogMapper.insert(systemLogPO)>0;
//			}
//		}
		return systemLogService.saveOrUpdate(systemLogPO);
    }

    @Override
    public Boolean updateLog(SystemLogPO systemLogPO) {
        return systemLogMapper.updateById(systemLogPO)>0;
    }
}
