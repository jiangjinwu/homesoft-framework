/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package com.jubaozan.service.commission.service.modules.application;

@Component
public class StoreDepartmentConfigFacade{
    @Autowired
    private StoreDepartmentConfigService storeDepartmentConfigService;

    public Boolean create(String tenantId,UserId userId,StoreDepartmentConfigCreationRequest request){
        StoreDepartmentConfigService storeDepartmentConfigDO = StoreDepartmentConfigUtils.creation2VO(request);
        storeDepartmentConfigDO.setUserId(userId.getId());
        storeDepartmentConfigDO.setTenantId(tenantId);
        storeDepartmentConfigService.save(storeDepartmentConfigDO);
        return true;
    }

}
