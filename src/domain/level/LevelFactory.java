package domain.level;

import domain.combat.Enemy;
import domain.combat.Goblin;
import domain.combat.Wolf;
import java.util.ArrayList;
import java.util.List;

public class LevelFactory {
    public static Level createLevel(DifficultyLevel difficulty) {
        if (difficulty == null) throw new IllegalArgumentException("Difficulty cannot be null.");
        switch (difficulty) {
            case EASY:   return new Level("Easy", createEnemies(3, 0), new ArrayList<>());
            case MEDIUM: return new Level("Medium", createEnemies(1, 1), createEnemies(0, 2));
            case HARD:   return new Level("Hard", createEnemies(2, 0), createEnemies(1, 2));
            default: throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }
    }

    public List<Enemy> createEnemiesForLevel(DifficultyLevel difficulty) {
        return createLevel(difficulty).getInitialWave();
    }

    public List<Enemy> createBackupWave(DifficultyLevel difficulty) {
        return createLevel(difficulty).getBackupWave();
    }

    private static List<Enemy> createEnemies(int goblinCount, int wolfCount) {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 1; i <= goblinCount; i++) enemies.add(new Goblin("Goblin_" + i));
        for (int i = 1; i <= wolfCount; i++) enemies.add(new Wolf("Wolf_" + i));
        return enemies;
    }
}