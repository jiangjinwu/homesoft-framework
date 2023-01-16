/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package com.jubaozan.service.commission.modules.application;

@Component
public class StoreUserExtinfoImportFacade{
    @Autowired
    private StoreUserExtinfoImportService storeUserExtinfoImportService;

    public Boolean create(String tenantId,UserId userId,StoreUserExtinfoImportCreationRequest request){
        StoreUserExtinfoImportService storeUserExtinfoImportDO = StoreUserExtinfoImportUtils.creation2VO(request);
        storeUserExtinfoImportDO.setUserId(userId.getId());
        storeUserExtinfoImportDO.setTenantId(tenantId);
        storeUserExtinfoImportService.save(storeUserExtinfoImportDO);
        return true;
    }

}
