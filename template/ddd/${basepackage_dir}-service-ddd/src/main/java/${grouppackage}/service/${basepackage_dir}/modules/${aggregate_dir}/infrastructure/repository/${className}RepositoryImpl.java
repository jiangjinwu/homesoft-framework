<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>

package ${basepackage}.modules.${aggregate}.infrastructure.repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ${grouppackage}.service.${basepackage}.modules.utils.${classNameLower}Utils;

@Service
@Slf4j
@RequiredArgsConstructor
public class ${className}RepositoryImpl implements ${className}Repository {
    @Autowired StringRedisTemplate stringRedisTemplate;
    @Autowired ${className}Service ${classNameLower}Service;


    public ${className}RepositoryImpl( RedissonClient redissonClient,
    JsonJacksonCodec jsonJacksonCodec){
        this.lost${classNameLower}Ids = redissonClient.getSet("lost${classNameLower}Ids", jsonJacksonCodec);
    }

    @Cacheable(value = "${aggregate}:${classNameLower}",key = "#${classNameLower}Id.value",unless="#result == null")
    @Cacheable(value = "${aggregate}:${classNameLower}::lost",key = "#tokenInfoId.value",unless="#result != null")
    public ${className}VO of${className}Id(${className}Id ${classNameLower}Id){
        ${className}DO ${classNameLower}DO = ${classNameLower}Service.getById(${classNameLower}Id.getValue());
        return ${className}Utils.do2VO(${classNameLower}DO);
    }

    public ${className}VO of${className}UserId(UserId userId){
        ${className}DO ${classNameLower}DO = ${classNameLower}Service.getById(${classNameLower}Id.getValue());
        return ${className}Utils.do2VO(${classNameLower}DO);
    }

    @CacheEvict(value="${aggregate}:${classNameLower}",key = "#${classNameLower}Id.value")
    public Boolean save(${className}DO ${classNameLower}DO){
       Boolean saved =   ${classNameLower}Service.saveOrUpdate(${classNameLower}DO);
       if(Objests.nonNull(${classNameLower}DO.getId())){
           this.remove${className}Cache(${classNameLower}DO.getId());
        }
       return saved;
    }


   public Boolean remove${className}Cache(Long id){
       return stringRedisTemplate.delete("${aggregate}:${classNameLower}:lost:"+id)
    }
}
