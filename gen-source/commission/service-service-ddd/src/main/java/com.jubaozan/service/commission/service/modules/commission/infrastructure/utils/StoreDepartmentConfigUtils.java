/*
 * Copyright(c) 2021 nolan All rights reserved.
 */

package  com.jubaozan.service.commission.service.modules.utils;

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
public class StoreDepartmentConfigUtils {
    public static class Conversion {
        @Named("statusName")
        public String statusName(Integer source) {
          return "";
        }
    }

    @Mapper(uses = {Conversion.class})
    public interface Converter {
        @Mappings({})
        StoreDepartmentConfigDO creation2DO(StoreDepartmentConfigCreationDTO source);

        StoreDepartmentConfigCreationDTO request2creation(StoreDepartmentConfigCreationRequest source);

        @Mappings({})
        StoreDepartmentConfigDO update2DO(StoreDepartmentConfigUpdateDTO source);

        @Mappings({})
        StoreDepartmentConfigVO do2VO(StoreDepartmentConfigDO source);

        @Mappings({})
        List<StoreDepartmentConfigVO> do2VOList(List<StoreDepartmentConfigDO> source);
    }

    private static final Converter CONVERTER = Mappers.getMapper(Converter.class);


    public static StoreDepartmentConfigDO creation2DO(StoreDepartmentConfigCreationDTO source) {
        return CONVERTER.creation2DO(source);
    }


    public static CommonResourceCreationDTO request2creation(CommonResourceCreationRequest source) {
        return CONVERTER.request2creation(source);
    }

    public static StoreDepartmentConfigDO update2DO(StoreDepartmentConfigUpdateDTO source) {
        return CONVERTER.update2DO(source);
    }

    public static  List<StoreDepartmentConfigVO> do2VOList(List<StoreDepartmentConfigDO> source) {
        return CONVERTER.do2VOList(source);
    }

    public static StoreDepartmentConfigVO do2VO(StoreDepartmentConfigDO source) {
        return CONVERTER.do2VO(source);
    }

    public static IPage<StoreDepartmentConfigVO> do2Page(IPage<StoreDepartmentConfigDO> source) {
        Page<StoreDepartmentConfigVO> target = new Page<>(source.getCurrent(), source.getSize());
        target.setTotal(source.getTotal());
        target.setPages(source.getPages());
        target.setRecords(CONVERTER.do2VOList(source.getRecords()));
        return target;
    }


}
