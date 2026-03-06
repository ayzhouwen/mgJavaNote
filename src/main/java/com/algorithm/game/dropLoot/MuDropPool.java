package com.algorithm.game.dropLoot;

import java.util.*;
/**
 * 3. 掉落池管理器（静态配置）
 */
public class MuDropPool {
    private static final Map<String, List<DropItem>> POOLS = new HashMap<>();

    static {
        // 货币/材料池
        POOLS.put("currency", Arrays.asList(
                new DropItem("金币", "currency", 1000),
                new DropItem("祝福宝石", "currency", 5),
                new DropItem("灵魂宝石", "currency", 3)
        ));

        // 普通装备
        POOLS.put("common", Arrays.asList(
                new DropItem("+0 皮甲", "common", 200),
                new DropItem("+0 青铜剑", "common", 180),
                new DropItem("小生命药水", "common", 500)
        ));

        // 稀有装备
        POOLS.put("rare", Arrays.asList(
                new DropItem("+3 闪电法杖", "rare", 20),
                new DropItem("+4 守护之戒", "rare", 15),
                new DropItem("+0 天使首饰", "rare", 10)
        ));

        // 史诗装备
        POOLS.put("epic", Arrays.asList(
                new DropItem("+5 追踪者之弓", "epic", 8),
                new DropItem("+6 魔法盾", "epic", 5),
                new DropItem("+0 混沌装备", "epic", 3)
        ));
    }

    public static List<DropItem> getPool(String category) {
        return POOLS.getOrDefault(category, Collections.emptyList());
    }

    public static List<MonsterDropConfig> createMonsterConfigs() {
        List<MonsterDropConfig> configs = new ArrayList<>();

        // 黑龙配置（高阶BOSS）
        List<DropItem> blackDragonUniques = Arrays.asList(
                new DropItem("破坏之剑", "unique", 1),
                new DropItem("王者之翼", "unique", 1),
                new DropItem("流星追魂弓", "unique", 1)
        );

        configs.add(new MonsterDropConfig(
                "黑龙",
                "失落之塔7层",
                130,
                Arrays.asList("rare", "epic", "currency"),
                blackDragonUniques
        ));

        // 骷髅弓箭手（低级怪）
        configs.add(new MonsterDropConfig(
                "骷髅弓箭手",
                "冰风谷",
                30,
                Arrays.asList("common", "currency"),
                null
        ));

        // 死神（顶级BOSS）
        List<DropItem> deathGodUniques = Arrays.asList(
                new DropItem("恶魔之魂", "unique", 2),
                new DropItem("天使武器箱", "unique", 1)
        );

        configs.add(new MonsterDropConfig(
                "死神",
                "死亡沙漠",
                150,
                Arrays.asList("epic", "currency"),
                deathGodUniques
        ));

        return configs;
    }
}
