package top.homesoft.drools.infrastructure.persistence.tenant;

import top.homesoft.drools.domain.tenant.TenantId;
import top.homesoft.drools.domain.tenant.TenantRepository;

public class MyBatisTenantRepository implements TenantRepository {
    public Long getOfficialStoreId(TenantId tenantId){
        return 100L;
    }
}
