<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>

package  ${grouppackage}.service.${basepackage}.modules.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ${className}Utils {
    public static class Conversion {
        @Named("statusName")
        public String statusName(Integer source) {
          return "";
        }
    }

    @Mapper(uses = {Conversion.class})
    public interface Converter {
        @Mappings({})
        ${className}DO creation2DO(${className}CreationDTO source);


        @Mappings({})
        ${className}DO update2DO(${className}UpdateDTO source);

        @Mappings({})
        ${className}VO do2VO(${className}DO source);

        @Mappings({})
        List<${className}VO> do2VOList(List<${className}DO> source);
    }

    private static final Converter CONVERTER = Mappers.getMapper(Converter.class);


    public static ${className}DO creation2DO(${className}CreationDTO source) {
        return CONVERTER.creation2DO(source);
    }

    public static ${className}DO update2DO(${className}UpdateDTO source) {
        return CONVERTER.update2DO(source);
    }

    public static  List<${className}VO> do2VOList(List<${className}DO> source) {
        return CONVERTER.do2VOList(source);
    }

    public static ${className}VO do2VO(${className}DO source) {
        return CONVERTER.do2VO(source);
    }

    public static IPage<${className}VO> do2Page(IPage<${className}DO> source) {
        Page<${className}VO> target = new Page<>(source.getCurrent(), source.getSize());
        target.setTotal(source.getTotal());
        target.setPages(source.getPages());
        target.setRecords(CONVERTER.do2VOList(source.getRecords()));
        return target;
    }


}
