package engine;

import domain.combat.Player;
import domain.combat.Warrior;
import domain.combat.Wizard;
import domain.item.Item;
import domain.item.Potion;
import domain.item.PowerStone;
import domain.item.SmokeBomb;
import domain.level.DifficultyLevel;
import domain.level.Level;
import domain.level.LevelFactory;
import ui.BattleUI;
import ui.GameMenuUI;

public class GameController {
    private final GameMenuUI menuUI;
    private final BattleUI battleUI;
    private final LevelFactory levelFactory;

    public GameController(GameMenuUI menuUI, BattleUI battleUI, LevelFactory levelFactory) {
        this.menuUI = menuUI;
        this.battleUI = battleUI;
        this.levelFactory = levelFactory;
    }

    public void start() {
        try {
            boolean running = true;
            while (running) {
                GameConfig config = runSetup();
                runGame(config);
                running = handleReplay(config);
            }
            menuUI.showResult("Thanks for playing!");
        } catch (Exception e) {
            menuUI.showResult("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public GameConfig runSetup() {
        menuUI.showLoadingScreen();
        Player player = menuUI.promptPlayerChoice();
        menuUI.promptItemChoices(player);
        DifficultyLevel difficulty = menuUI.promptDifficulty();
        Level level = LevelFactory.createLevel(difficulty);
        return new GameConfig(player, level, difficulty);
    }

    public void runGame(GameConfig config) {
        BattleEngine engine = new BattleEngine(
                config.getPlayer(),
                config.getLevel(),
                new SpeedBasedTurnOrder(),
                battleUI);
        engine.runBattle();
    }

    public boolean handleReplay(GameConfig config) {
        int choice = menuUI.promptReplayChoice();
        return switch (choice) {
            case 1 -> {
                GameConfig rebuilt = rebuildConfig(config);
                runGame(rebuilt);
                yield handleReplay(rebuilt);
            }
            case 2 -> true;
            case 3 -> false;
            default -> false;
        };
    }

    private GameConfig rebuildConfig(GameConfig original) {
        Player same = rebuildPlayer(original.getPlayer());
        Level sameLevel = LevelFactory.createLevel(original.getDifficulty());
        return new GameConfig(same, sameLevel, original.getDifficulty());
    }

    private Player rebuildPlayer(Player original) {
        Player p = (original instanceof Warrior)
                ? new Warrior(original.getName())
                : new Wizard(original.getName());
        original.getInventory().forEach(item -> p.addItem(rebuildItem(item.getName())));
        return p;
    }

    private Item rebuildItem(String name) {
        return switch (name) {
            case "Potion" -> new Potion();
            case "Power Stone" -> new PowerStone();
            case "Smoke Bomb" -> new SmokeBomb();
            default -> throw new IllegalArgumentException("Unknown item: " + name);
        };
    }
}