import domain.combat.Player;
import domain.combat.Warrior;
import domain.combat.Wizard;
import domain.item.Item;
import domain.item.Potion;
import domain.item.PowerStone;
import domain.item.SmokeBomb;
import domain.level.Level;
import domain.level.LevelFactory;
import engine.BasicAttackStrategy;
import engine.BattleEngine;
import engine.SpeedBasedTurnOrder;
import ui.GameUI;

public class Main {
    private static final GameUI ui = new GameUI();

    public static void main(String[] args) {
        try {
            boolean running = true;
            while (running) {
                ui.printLoadingScreen();
                Player player = selectPlayer();
                selectItems(player);
                Level level = selectLevel();
                new BattleEngine(player, level,
                        new SpeedBasedTurnOrder(),
                        new BasicAttackStrategy(),
                        ui).run();
                running = handleReplay(player, level);
            }
            ui.printGoodbye();
        } catch (IllegalArgumentException e) {
            ui.printError("Setup error: " + e.getMessage());
        } catch (Exception e) {
            ui.printError("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Player selectPlayer() {
        ui.printError("Select your player:\n  [1] Warrior\n  [2] Wizard");
        int choice = ui.readInt(1, 2);
        String name = ui.readName();
        if (name.isEmpty()) name = (choice == 1) ? "Warrior" : "Wizard";
        Player player = (choice == 1) ? new Warrior(name) : new Wizard(name);
        ui.printError("You selected: " + player);
        return player;
    }

    private static void selectItems(Player player) {
        ui.printError("\nSelect 2 items (duplicates allowed):"
                + "\n  [1] Potion      — Heal 100 HP"
                + "\n  [2] Power Stone — Trigger special skill once, no cooldown change"
                + "\n  [3] Smoke Bomb  — Invulnerable to enemy attacks for 2 turns");
        for (int i = 1; i <= 2; i++) {
            ui.printError("Item " + i + ":");
            player.addItem(createItem(ui.readInt(1, 3)));
        }
        ui.printError("Items selected: "
                + player.getInventory().stream().map(Item::getName).toList());
    }

    private static Item createItem(int choice) {
        return switch (choice) {
            case 1 -> new Potion();
            case 2 -> new PowerStone();
            case 3 -> new SmokeBomb();
            default -> throw new IllegalArgumentException("Invalid item choice: " + choice);
        };
    }

    private static Level selectLevel() {
        ui.printError("\nSelect difficulty:\n  [1] Easy\n  [2] Medium\n  [3] Hard");
        int choice = ui.readInt(1, 3);
        String difficulty = switch (choice) {
            case 1 -> "easy";
            case 2 -> "medium";
            case 3 -> "hard";
            default -> throw new IllegalArgumentException("Invalid difficulty choice: " + choice);
        };
        ui.printError("Difficulty selected: "
                + difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1));
        return LevelFactory.createLevel(difficulty);
    }

    private static boolean handleReplay(Player originalPlayer, Level originalLevel) {
        ui.printReplayMenu();
        int choice = ui.readInt(1, 3);
        return switch (choice) {
            case 1 -> {
                Player same = (originalPlayer instanceof Warrior)
                        ? new Warrior(originalPlayer.getName())
                        : new Wizard(originalPlayer.getName());
                originalPlayer.getInventory().forEach(item -> same.addItem(createItemByName(item.getName())));
                Level sameLevel = LevelFactory.createLevel(originalLevel.getDifficulty().toLowerCase());
                new BattleEngine(same, sameLevel,
                        new SpeedBasedTurnOrder(),
                        new BasicAttackStrategy(),
                        ui).run();
                yield handleReplay(same, sameLevel);
            }
            case 2 -> true;
            case 3 -> false;
            default -> false;
        };
    }

    private static Item createItemByName(String name) {
        return switch (name) {
            case "Potion" -> new Potion();
            case "Power Stone" -> new PowerStone();
            case "Smoke Bomb" -> new SmokeBomb();
            default -> throw new IllegalArgumentException("Unknown item: " + name);
        };
    }
}