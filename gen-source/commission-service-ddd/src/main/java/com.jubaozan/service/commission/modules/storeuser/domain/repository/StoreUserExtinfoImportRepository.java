/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.modules.storeuser.domain.repository;

public interface StoreUserExtinfoImportRepository {
    StoreUserExtinfoImportVO ofStoreUserExtinfoImportId(StoreUserExtinfoImportId storeUserExtinfoImportId);

    Boolean removeStoreUserExtinfoImportCache(Long id);
}
