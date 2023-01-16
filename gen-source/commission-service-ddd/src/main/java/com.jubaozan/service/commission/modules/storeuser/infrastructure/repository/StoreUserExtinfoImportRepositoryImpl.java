/*
 * Copyright(c) 2021 nolan All rights reserved.
 */

package commission.modules.storeuser.infrastructure.repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.jubaozan.service.commission.modules.utils.storeUserExtinfoImportUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreUserExtinfoImportRepositoryImpl implements StoreUserExtinfoImportRepository {
    @Autowired StringRedisTemplate stringRedisTemplate;
    @Autowired StoreUserExtinfoImportService storeUserExtinfoImportService;


    public StoreUserExtinfoImportRepositoryImpl( RedissonClient redissonClient,
    JsonJacksonCodec jsonJacksonCodec){
        this.loststoreUserExtinfoImportIds = redissonClient.getSet("loststoreUserExtinfoImportIds", jsonJacksonCodec);
    }

    @Cacheable(value = "storeuser:storeUserExtinfoImport",key = "#storeUserExtinfoImportId.value",unless="#result == null")
    @Cacheable(value = "storeuser:storeUserExtinfoImport::lost",key = "#tokenInfoId.value",unless="#result != null")
    public StoreUserExtinfoImportVO ofStoreUserExtinfoImportId(StoreUserExtinfoImportId storeUserExtinfoImportId){
        StoreUserExtinfoImportDO storeUserExtinfoImportDO = storeUserExtinfoImportService.getById(storeUserExtinfoImportId.getValue());
        return StoreUserExtinfoImportUtils.do2VO(storeUserExtinfoImportDO);
    }

    public StoreUserExtinfoImportVO ofStoreUserExtinfoImportUserId(UserId userId){
        StoreUserExtinfoImportDO storeUserExtinfoImportDO = storeUserExtinfoImportService.getById(storeUserExtinfoImportId.getValue());
        return StoreUserExtinfoImportUtils.do2VO(storeUserExtinfoImportDO);
    }

    @CacheEvict(value="storeuser:storeUserExtinfoImport",key = "#storeUserExtinfoImportId.value")
    public Boolean save(StoreUserExtinfoImportDO storeUserExtinfoImportDO){
       Boolean saved =   storeUserExtinfoImportService.saveOrUpdate(storeUserExtinfoImportDO);
       if(Objests.nonNull(storeUserExtinfoImportDO.getId())){
           this.removeStoreUserExtinfoImportCache(storeUserExtinfoImportDO.getId());
        }
       return saved;
    }


   public Boolean removeStoreUserExtinfoImportCache(Long id){
       return stringRedisTemplate.delete("storeuser:storeUserExtinfoImport:lost:"+id)
    }
}
