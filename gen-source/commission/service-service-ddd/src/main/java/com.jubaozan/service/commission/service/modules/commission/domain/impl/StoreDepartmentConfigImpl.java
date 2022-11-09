/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.modules.commission.domain.impl;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BusinessDistrictMember
 * @version 1.0
 * @author jjw
 */

@Slf4j
@RequiredArgsConstructor
public class StoreDepartmentConfigImpl implements StoreDepartmentConfig {
    private final StoreDepartmentConfigDO data;
    private final StoreDepartmentConfigRepository repository;

    public StoreDepartmentConfigDO data(){

        return this.data;
    }
     public StoreDepartmentConfigId getId(){
        return new StoreDepartmentConfigId(data.getId());
     }
}
