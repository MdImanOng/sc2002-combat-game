import domain.combat.Player;
import domain.combat.Warrior;
import domain.combat.Wizard;
import domain.item.Item;
import domain.item.Potion;
import domain.item.PowerStone;
import domain.item.SmokeBomb;
import domain.level.Level;
import domain.level.LevelFactory;
import engine.BattleEngine;
import engine.SpeedBasedTurnOrder;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            printLoadingScreen();
            Player player = selectPlayer();
            selectItems(player);
            Level level = selectLevel();
            BattleEngine engine = new BattleEngine(player, level, new SpeedBasedTurnOrder());
            engine.run();
            askReplay(player, level);
        } catch (IllegalArgumentException e) {
            System.out.println("Setup error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printLoadingScreen() {
        System.out.println("============================================");
        System.out.println("       TURN-BASED COMBAT ARENA");
        System.out.println("============================================");
        System.out.println("\nAvailable Players:");
        System.out.println("  [1] Warrior  | HP: 260 | ATK: 40 | DEF: 20 | SPD: 30");
        System.out.println("               | Skill: Shield Bash — deals damage + stuns target for 2 turns");
        System.out.println("  [2] Wizard   | HP: 200 | ATK: 50 | DEF: 10 | SPD: 20");
        System.out.println("               | Skill: Arcane Blast — hits all enemies, +10 ATK per kill");
        System.out.println("\nAvailable Enemies:");
        System.out.println("  Goblin | HP: 55  | ATK: 35 | DEF: 15 | SPD: 25");
        System.out.println("  Wolf   | HP: 40  | ATK: 45 | DEF: 5  | SPD: 35");
        System.out.println("\nDifficulty Levels:");
        System.out.println("  [1] Easy   — 3 Goblins");
        System.out.println("  [2] Medium — 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("  [3] Hard   — 2 Goblins          | Backup: 1 Goblin + 2 Wolves");
        System.out.println("============================================\n");
    }

    private static Player selectPlayer() {
        System.out.println("Select your player:");
        System.out.println("  [1] Warrior");
        System.out.println("  [2] Wizard");
        int choice = readInt(1, 2);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = (choice == 1) ? "Warrior" : "Wizard";
        Player player = (choice == 1) ? new Warrior(name) : new Wizard(name);
        System.out.println("You selected: " + player);
        return player;
    }

    private static void selectItems(Player player) {
        System.out.println("\nSelect 2 items (duplicates allowed):");
        System.out.println("  [1] Potion      — Heal 100 HP");
        System.out.println("  [2] Power Stone — Trigger special skill once, no cooldown change");
        System.out.println("  [3] Smoke Bomb  — Invulnerable to enemy attacks for 2 turns");
        for (int i = 1; i <= 2; i++) {
            System.out.println("Item " + i + ":");
            int choice = readInt(1, 3);
            player.addItem(createItem(choice));
        }
        System.out.print("Items selected: ");
        player.getInventory().forEach(item -> System.out.print(item.getName() + " "));
        System.out.println();
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
        System.out.println("\nSelect difficulty:");
        System.out.println("  [1] Easy");
        System.out.println("  [2] Medium");
        System.out.println("  [3] Hard");
        int choice = readInt(1, 3);
        String difficulty = switch (choice) {
            case 1 -> "easy";
            case 2 -> "medium";
            case 3 -> "hard";
            default -> throw new IllegalArgumentException("Invalid difficulty choice: " + choice);
        };
        System.out.println("Difficulty selected: " + difficulty.substring(0, 1).toUpperCase()
                + difficulty.substring(1));
        return LevelFactory.createLevel(difficulty);
    }

    private static void askReplay(Player originalPlayer, Level originalLevel) {
        System.out.println("\n============================================");
        System.out.println("  [1] Replay with same settings");
        System.out.println("  [2] Start a new game");
        System.out.println("  [3] Exit");
        System.out.println("============================================");
        int choice = readInt(1, 3);
        try {
            switch (choice) {
                case 1 -> {
                    Player same = (originalPlayer instanceof Warrior)
                            ? new Warrior(originalPlayer.getName())
                            : new Wizard(originalPlayer.getName());
                    originalPlayer.getInventory().forEach(item -> same.addItem(createItemByName(item.getName())));
                    Level sameLevel = LevelFactory.createLevel(originalLevel.getDifficulty().toLowerCase());
                    new BattleEngine(same, sameLevel, new SpeedBasedTurnOrder()).run();
                    askReplay(same, sameLevel);
                }
                case 2 -> main(new String[]{});
                case 3 -> System.out.println("Thanks for playing!");
            }
        } catch (Exception e) {
            System.out.println("Error during replay: " + e.getMessage());
        }
    }

    private static Item createItemByName(String name) {
        return switch (name) {
            case "Potion" -> new Potion();
            case "Power Stone" -> new PowerStone();
            case "Smoke Bomb" -> new SmokeBomb();
            default -> throw new IllegalArgumentException("Unknown item: " + name);
        };
    }

    private static int readInt(int min, int max) {
        int val = -1;
        while (val < min || val > max) {
            System.out.print("Choice [" + min + "-" + max + "]: ");
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) throw new IllegalArgumentException("Input cannot be empty.");
                val = Integer.parseInt(input);
                if (val < min || val > max)
                    throw new IllegalArgumentException("Enter a number between " + min + " and " + max + ".");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
                val = -1;
            }
        }
        return val;
    }
}