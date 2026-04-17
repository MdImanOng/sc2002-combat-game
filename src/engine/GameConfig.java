package engine;

import domain.combat.Player;
import domain.level.DifficultyLevel;
import domain.level.Level;

public class GameConfig {
    private final Player player;
    private final Level level;
    private final DifficultyLevel difficulty;

    public GameConfig(Player player, Level level, DifficultyLevel difficulty) {
        this.player = player;
        this.level = level;
        this.difficulty = difficulty;
    }

    public Player getPlayer() { return player; }
    public Level getLevel() { return level; }
    public DifficultyLevel getDifficulty() { return difficulty; }
}