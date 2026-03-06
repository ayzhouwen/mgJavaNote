package com.algorithm.game.dropLoot;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/**
 *  加权随机选择工具
 */
public class WeightedRandomSelector {
    public static <T extends DropItem> T select(List<T> items) {
        if (items == null || items.isEmpty()) return null;

        int totalWeight = items.stream().mapToInt(DropItem::getWeight).sum();
        if (totalWeight <= 0) return items.get(0);

        int random = ThreadLocalRandom.current().nextInt(totalWeight);
        int cumulative = 0;

        for (T item : items) {
            cumulative += item.getWeight();
            if (random < cumulative) {
                return item;
            }
        }

        return items.get(items.size() - 1);
    }
}
