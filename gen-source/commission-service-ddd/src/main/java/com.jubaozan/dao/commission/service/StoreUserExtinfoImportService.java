/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service;

import java.util.List;
import java.util.Map;


import  com.jubaozan.dao.commission.domain.StoreUserExtinfoImportDO;

/**
 * @version 1.0
 * @author jjw
 */
public interface StoreUserExtinfoImportService extends IService<StoreUserExtinfoImportDO> {

    IPage<StoreUserExtinfoImportDO> search(StoreUserExtinfoImportSearchRequest search);

     StoreUserExtinfoImportDO getByUserId(UserId userId);

}
