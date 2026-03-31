package engine;

import domain.combat.Enemy;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private final List<Enemy> enemies;

    public BattleEngine(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                alive.add(enemy);
            }
        }
        return alive;
    }
}