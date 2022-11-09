/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.modules.commission.domain.repository;

public interface StoreDepartmentConfigRepository {
    StoreDepartmentConfigVO ofStoreDepartmentConfigId(StoreDepartmentConfigId storeDepartmentConfigId);

    Long removeStoreDepartmentConfigCache(Long id);
}
