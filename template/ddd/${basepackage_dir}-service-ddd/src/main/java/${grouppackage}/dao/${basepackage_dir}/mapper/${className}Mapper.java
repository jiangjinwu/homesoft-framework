<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${grouppackage}.dao.${basepackage}.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import ${grouppackage}.dao.${basepackage}.domain.${className};

@Repository
public interface ${className}Mapper extends BaseMapper<${className}DO>{

 public IPage<${className}DO>search(Page page,${className}SearchDTO request);
}
