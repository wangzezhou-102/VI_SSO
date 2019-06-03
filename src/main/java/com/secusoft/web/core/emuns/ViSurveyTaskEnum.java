package com.secusoft.web.core.emuns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 布控类型 1-人员 2-车辆 3-事件 4-物品
 *
 * @author chjiang
 * @date 2019/6/3
 */
public enum ViSurveyTaskEnum {

    PEOPLE(1,"人"),
    CAR(2,"车"),
    THING(3,"事件"),
    GOODS(4,"物品");

    private int type;
    private String desc;

    ViSurveyTaskEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByType(int type) {
        if (type == 1) {
            return PEOPLE.desc;
        } else if (type == 2) {
            return CAR.desc;
        } else if (type == 3) {
            return THING.desc;
        } else if (type == 4) {
            return GOODS.desc;
        }
        return null;
    }

    public static final List<Map<String, Object>> list = new ArrayList<>();
    static {
        for (ViSurveyTaskEnum e : ViSurveyTaskEnum.values()) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id", e.getType());
            map1.put("name", e.getDesc());
            list.add(map1);
        }
    }
}
