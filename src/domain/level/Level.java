package domain.level;

import domain.combat.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final String difficulty;
    private final List<Enemy> initialWave;
    private final List<Enemy> backupWave;
    private boolean backupSpawned;

    public Level(String difficulty, List<Enemy> initialWave, List<Enemy> backupWave) {
        this.difficulty = difficulty;
        this.initialWave = initialWave;
        this.backupWave = backupWave;
        this.backupSpawned = false;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<Enemy> getInitialWave() {
        return initialWave;
    }

    public List<Enemy> getBackupWave() {
        return backupWave;
    }

    public boolean shouldSpawnBackup() {
        if (backupSpawned || backupWave.isEmpty()) {
            return false;
        }

        for (Enemy enemy : initialWave) {
            if (enemy.isAlive()) {
                return false;
            }
        }

        return true;
    }

    public List<Enemy> spawnBackupWave() {
        backupSpawned = true;
        return new ArrayList<>(backupWave);
    }
}