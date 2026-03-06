package com.algorithm.game.dropLoot;
/**
 * 掉落物品
 * 1. 装备与掉落项定义
 */
public class DropItem {
    private String name;
    private String category; // "currency", "common", "rare", "unique" 等
    private int weight;

    public DropItem(String name, String category, int weight) {
        this.name = name;
        this.category = category;
        this.weight = weight;
    }

    // Getters
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getWeight() { return weight; }

    @Override
    public String toString() {
        return name;
    }
}
