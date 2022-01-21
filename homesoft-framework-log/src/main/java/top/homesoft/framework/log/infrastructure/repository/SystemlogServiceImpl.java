package top.homesoft.framework.log.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.homesoft.framework.log.domain.systemlog.entity.SystemLogPO;
import top.homesoft.framework.log.domain.systemlog.repository.mapper.SystemLogMapper;
import top.homesoft.framework.log.domain.systemlog.service.SystemLogService;

 @Service
public class SystemlogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLogPO> implements SystemLogService {
}
