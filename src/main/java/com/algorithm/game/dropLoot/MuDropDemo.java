package com.algorithm.game.dropLoot;

import java.util.List;

/**
 * 使用示例（Main 测试）
 * @author Administrator
 *
 */
public class MuDropDemo {
    public static void main(String[] args) {
        // 创建爆率为 10 倍的掉落控制器（类似高爆服）
        MuDropController controller = new MuDropController(10.0);

        System.out.println("=== 模拟击杀黑龙 10000 次 ===");
        int uniqueCount = 0;
        for (int i = 1; i <= 10000; i++) {
            List<String> drops = controller.rollDrop("黑龙");
            for (String item : drops) {
                if (item.contains("破坏之剑") || item.contains("王者之翼") || item.contains("流星")) {
                    System.out.println("第 " + i + " 次击杀，爆出 Unique: " + item);
                    uniqueCount++;
                }
            }
        }
        System.out.println("10000 次共爆出 Unique 装备: " + uniqueCount + " 件");

        System.out.println("\n=== 骷髅弓箭手单次掉落 ===");
        System.out.println(controller.rollDrop("骷髅弓箭手"));
    }
}
