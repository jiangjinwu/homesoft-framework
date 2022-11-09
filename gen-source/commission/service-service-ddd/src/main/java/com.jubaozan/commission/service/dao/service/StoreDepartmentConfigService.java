/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.service;

import java.util.List;
import java.util.Map;


import  com.jubaozan.commission.service.domain.StoreDepartmentConfigDO;

/**
 * @version 1.0
 * @author jjw
 */
public interface StoreDepartmentConfigService extends IService<StoreDepartmentConfigDO> {

    IPage<StoreDepartmentConfigDO> search(StoreDepartmentConfigSearchRequest search);

     StoreDepartmentConfigDO getByUserId(UserId userId);

}
