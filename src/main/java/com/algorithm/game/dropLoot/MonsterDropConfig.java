package com.algorithm.game.dropLoot;

import java.util.ArrayList;
import java.util.List;

/*2. 怪物掉落配置类

 */
public class MonsterDropConfig {
    private String name;
    private String map;
    private int level;
    private List<String> dropSlots; // 如 ["rare", "epic", "currency"]
    private List<DropItem> uniqueItems; // Unique 装备列表（权重极低）

    public MonsterDropConfig(String name, String map, int level,
                             List<String> dropSlots,
                             List<DropItem> uniqueItems) {
        this.name = name;
        this.map = map;
        this.level = level;
        this.dropSlots = new ArrayList<>(dropSlots);
        this.uniqueItems = uniqueItems != null ? new ArrayList<>(uniqueItems) : new ArrayList<>();
    }

    // Getters
    public String getName() { return name; }
    public List<String> getDropSlots() { return dropSlots; }
    public List<DropItem> getUniqueItems() { return uniqueItems; }
}
