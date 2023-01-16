/*
 * Copyright(c) 2021 nolan All rights reserved.
 */

package  com.jubaozan.service.commission.modules.utils;

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
public class StoreUserExtinfoImportUtils {
    public static class Conversion {
        @Named("statusName")
        public String statusName(Integer source) {
          return "";
        }
    }

    @Mapper(uses = {Conversion.class})
    public interface Converter {
        @Mappings({})
        StoreUserExtinfoImportDO creation2DO(StoreUserExtinfoImportCreationDTO source);

        StoreUserExtinfoImportCreationDTO request2creation(StoreUserExtinfoImportCreationRequest source);

        @Mappings({})
        StoreUserExtinfoImportDO update2DO(StoreUserExtinfoImportUpdateDTO source);

        @Mappings({})
        StoreUserExtinfoImportVO do2VO(StoreUserExtinfoImportDO source);

        @Mappings({})
        List<StoreUserExtinfoImportVO> do2VOList(List<StoreUserExtinfoImportDO> source);
    }

    private static final Converter CONVERTER = Mappers.getMapper(Converter.class);


    public static StoreUserExtinfoImportDO creation2DO(StoreUserExtinfoImportCreationDTO source) {
        return CONVERTER.creation2DO(source);
    }


    public static StoreUserExtinfoImportDO update2DO(StoreUserExtinfoImportUpdateDTO source) {
        return CONVERTER.update2DO(source);
    }

    public static  List<StoreUserExtinfoImportVO> do2VOList(List<StoreUserExtinfoImportDO> source) {
        return CONVERTER.do2VOList(source);
    }

    public static StoreUserExtinfoImportVO do2VO(StoreUserExtinfoImportDO source) {
        return CONVERTER.do2VO(source);
    }

    public static IPage<StoreUserExtinfoImportVO> do2Page(IPage<StoreUserExtinfoImportDO> source) {
        Page<StoreUserExtinfoImportVO> target = new Page<>(source.getCurrent(), source.getSize());
        target.setTotal(source.getTotal());
        target.setPages(source.getPages());
        target.setRecords(CONVERTER.do2VOList(source.getRecords()));
        return target;
    }


}
