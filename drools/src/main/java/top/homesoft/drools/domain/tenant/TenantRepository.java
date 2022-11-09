package top.homesoft.drools.domain.tenant;



public interface TenantRepository {
    Long getOfficialStoreId(TenantId tenantId);
}
