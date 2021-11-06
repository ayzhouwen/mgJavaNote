package com.syntax.enumtest;

import lombok.Getter;

/**
 * 中心采集器主节点标识
 */
@Getter
public enum MasterFlagEnum {

    yes(77, "是"),

    no(78, "不是");
    private final Integer code;
    private final String name;

    MasterFlagEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (MasterFlagEnum alarmLevelEnum : MasterFlagEnum.values()) {
            if (code.equals(alarmLevelEnum.getCode())) {
                return alarmLevelEnum.getName();
            }
        }
        return null;
    }

}
