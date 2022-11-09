/*
 * Copyright(c) 2021 nolan All rights reserved.
 */

package commission.service.modules.commission.infrastructure.repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.jubaozan.service.commission.service.modules.utils.storeDepartmentConfigUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreDepartmentConfigRepositoryImpl implements StoreDepartmentConfigRepository {
    @Autowired
    RedisTemplate redisTemplate;
    final StoreDepartmentConfigService storeDepartmentConfigService;
    private final RSet<Long> loststoreDepartmentConfigIds;

    public StoreDepartmentConfigRepositoryImpl( RedissonClient redissonClient,
    JsonJacksonCodec jsonJacksonCodec){
        this.loststoreDepartmentConfigIds = redissonClient.getSet("loststoreDepartmentConfigIds", jsonJacksonCodec);
    }

    @Cacheable(value = "pay:storeDepartmentConfig",key = "#storeDepartmentConfigId.value",unless="#result == null")
    public StoreDepartmentConfigVO ofStoreDepartmentConfigId(StoreDepartmentConfigId storeDepartmentConfigId){
        if(Objects.isNull(loststoreDepartmentConfigIds.get(storeDepartmentConfigId.getValue()))){
            return null;
        }
        StoreDepartmentConfigDO storeDepartmentConfigDO = storeDepartmentConfigService.getById(storeDepartmentConfigId.getValue());
        if(Objects.isNull(storeDepartmentConfigDO)){
           loststoreDepartmentConfigIds.add(storeDepartmentConfigId.getValue());
        }

        return StoreDepartmentConfigUtils.do2VO(storeDepartmentConfigDO);
    }

    public StoreDepartmentConfigVO ofStoreDepartmentConfigUserId(UserId userId){
        StoreDepartmentConfigDO storeDepartmentConfigDO = storeDepartmentConfigService.getById(storeDepartmentConfigId.getValue());
        return StoreDepartmentConfigUtils.do2VO(storeDepartmentConfigDO);
    }

    @CacheEvict(value="pay:storeDepartmentConfig",key = "#storeDepartmentConfigId.value")
    public Boolean save(StoreDepartmentConfigDO storeDepartmentConfigDO){
       Boolean saved =   storeDepartmentConfigService.saveOrUpdate(storeDepartmentConfigDO);
       if(Objests.nonNull(storeDepartmentConfigDO.getId())){
           this.removeStoreDepartmentConfigCache(storeDepartmentConfigDO.getId());
        }
       return saved;
    }


   public Long removeStoreDepartmentConfigCache(Long id){
       return stringRedisTemplate.delete("pay:storeDepartmentConfig:"+id)
    }
}
