package com.algorithm.game.dropLoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MuDropController {
    private static final Logger log = LoggerFactory.getLogger(MuDropController.class);
    private final Map<String, MonsterDropConfig> monsterConfigMap;
    private final double dropRateMultiplier; // 服务器爆率倍数，如 5.0 = 500%

    public MuDropController(double dropRateMultiplier) {
        this.dropRateMultiplier = dropRateMultiplier;
        this.monsterConfigMap = new HashMap<>();
        for (MonsterDropConfig config : MuDropPool.createMonsterConfigs()) {
            monsterConfigMap.put(config.getName(), config);
        }
    }

    /**
     * 执行一次怪物击杀后的掉落计算
     *
     * @param monsterName 怪物名称，如 "黑龙"
     * @return 掉落的物品名称列表
     */
    public List<String> rollDrop(String monsterName) {
        MonsterDropConfig config = monsterConfigMap.get(monsterName);
        if (config == null) {
            return Collections.emptyList();
        }

        List<String> drops = new ArrayList<>();
        Random rand = ThreadLocalRandom.current();
        double d=rand.nextDouble();
        log.info("rand:"+ d);
        // 1. 货币/材料类掉落（95%概率）
        if (d < 0.95) {
            List<DropItem> currencyPool = MuDropPool.getPool("currency");
            DropItem item = WeightedRandomSelector.select(currencyPool);
            if (item != null) {
                drops.add(item.getName());
            }
        }

        // 2. 装备掉落（基础30%概率，受爆率倍数影响）
        double equipDropChance = Math.min(0.3 * dropRateMultiplier, 1.0);
        if (rand.nextDouble() < equipDropChance) {
            // 从该怪允许的掉落槽中随机选一个（排除 currency）
            List<String> nonCurrencySlots = new ArrayList<>(config.getDropSlots());
            nonCurrencySlots.removeIf("currency"::equals);

            if (!nonCurrencySlots.isEmpty()) {
                String chosenSlot = nonCurrencySlots.get(rand.nextInt(nonCurrencySlots.size()));
                List<DropItem> pool = MuDropPool.getPool(chosenSlot);
                DropItem item = WeightedRandomSelector.select(pool);
                if (item != null) {
                    drops.add(item.getName());
                }
            }
        }

        // 3. Unique 装备掉落（独立极低概率，1/50000 基础）
        List<DropItem> uniqueItems = config.getUniqueItems();
        if (!uniqueItems.isEmpty()) {
            double uniqueBaseProb = 1.0 / 50000; // 0.002%
            double uniqueActualProb = uniqueBaseProb * dropRateMultiplier;
            if (rand.nextDouble() < uniqueActualProb) {
                DropItem unique = WeightedRandomSelector.select(uniqueItems);
                if (unique != null) {
                    drops.add(unique.getName());
                }
            }
        }

        return drops;
    }

}
