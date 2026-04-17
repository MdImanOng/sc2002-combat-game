package domain.level;

import domain.combat.Enemy;
import domain.combat.Goblin;
import domain.combat.Wolf;
import java.util.ArrayList;
import java.util.List;

public class LevelFactory {
    public static Level createLevel(String difficulty) {
        if (difficulty == null || difficulty.isBlank())
            throw new IllegalArgumentException("Difficulty cannot be null or empty.");
        switch (difficulty.toLowerCase()) {
            case "easy":   return new Level("Easy", createEnemies(3, 0), new ArrayList<>());
            case "medium": return new Level("Medium", createEnemies(1, 1), createEnemies(0, 2));
            case "hard":   return new Level("Hard", createEnemies(2, 0), createEnemies(1, 2));
            default: throw new IllegalArgumentException("Invalid difficulty: " + difficulty
                         + ". Choose easy, medium, or hard.");
        }
    }

    private static List<Enemy> createEnemies(int goblinCount, int wolfCount) {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 1; i <= goblinCount; i++) enemies.add(new Goblin("Goblin_" + i));
        for (int i = 1; i <= wolfCount; i++) enemies.add(new Wolf("Wolf_" + i));
        return enemies;
    }
}