package com.jubaozan.service.commission.level;

import cn.hutool.core.util.ObjectUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @ClassName CommissionWeightEnum
 * @Description
 * @Author WuShuo
 * @Date 2020/11/3
 * @Version 1.0
 **/
public enum StoreUserWeightEnum {
    PLATFORM(255, "个体平台"),
    FANS(1, "粉丝"),
    LEADER(254, "团长");
    //    HIGH_LEADER(3, "高级团长"),
//    DIAMOND_LEADER(4, "钻石团长");
    private final int value;
    private final String name;
    private static final Map<Integer, StoreUserWeightEnum> map = new HashMap<>();

    static {
        for (StoreUserWeightEnum weightEnum : StoreUserWeightEnum.values()) {
            map.put(weightEnum.value, weightEnum);
        }
    }

    StoreUserWeightEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static StoreUserWeightEnum getInstance(int value) {
        StoreUserWeightEnum result = map.get(value);
        if (ObjectUtil.isNull(result)) {
            result = StoreUserWeightEnum.LEADER;
        }
        return result;
    }

    public boolean isCommonAgent() {
        return this.getValue() > StoreUserWeightEnum.FANS.getValue() && this.getValue() < StoreUserWeightEnum.PLATFORM.getValue();
    }

    public boolean isPlatForm() {
        return this.getValue() == StoreUserWeightEnum.PLATFORM.getValue();
    }

    public boolean isPlatForm(Function function) {
        if (this.getValue() == StoreUserWeightEnum.PLATFORM.getValue()) {
            function.apply(this);
            return true;
        }
        return false;
    }
}
