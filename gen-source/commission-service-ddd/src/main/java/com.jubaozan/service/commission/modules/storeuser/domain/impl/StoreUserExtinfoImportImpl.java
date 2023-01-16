/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.modules.storeuser.domain.impl;
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
public class StoreUserExtinfoImportImpl implements StoreUserExtinfoImport {
    private final StoreUserExtinfoImportDO data;
    private final StoreUserExtinfoImportRepository repository;

    public StoreUserExtinfoImportDO data(){

        return this.data;
    }
     public StoreUserExtinfoImportId getId(){
        return new StoreUserExtinfoImportId(data.getId());
     }
}
