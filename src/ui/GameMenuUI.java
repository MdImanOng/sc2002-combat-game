package ui;

import domain.combat.Player;
import domain.combat.Warrior;
import domain.combat.Wizard;
import domain.item.Item;
import domain.item.Potion;
import domain.item.PowerStone;
import domain.item.SmokeBomb;
import domain.level.DifficultyLevel;
import java.util.Scanner;

public class GameMenuUI {
    private final Scanner scanner = new Scanner(System.in);

    public void showLoadingScreen() {
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
        System.out.println("  [1] Easy   — Initial Spawn: 3 Goblins");
        System.out.println("  [2] Medium — Initial Spawn: 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("  [3] Hard   — Initial Spawn: 2 Goblins          | Backup: 1 Goblin + 2 Wolves");
        System.out.println("============================================\n");
    }

    public Player promptPlayerChoice() {
        System.out.println("Select your player:\n  [1] Warrior\n  [2] Wizard");
        int choice = readInt(1, 2);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = (choice == 1) ? "Warrior" : "Wizard";
        Player player = (choice == 1) ? new Warrior(name) : new Wizard(name);
        System.out.println("You selected: " + player);
        return player;
    }

    public void promptItemChoices(Player player) {
        System.out.println("\nSelect 2 items (duplicates allowed):");
        System.out.println("  [1] Potion      — Heal 100 HP");
        System.out.println("  [2] Power Stone — Trigger special skill once, no cooldown change");
        System.out.println("  [3] Smoke Bomb  — Invulnerable to enemy attacks for 2 turns");
        for (int i = 1; i <= 2; i++) {
            System.out.println("Item " + i + ":");
            player.addItem(createItem(readInt(1, 3)));
        }
        System.out.println("Items selected: "
                + player.getInventory().stream().map(Item::getName).toList());
    }

    public DifficultyLevel promptDifficulty() {
        System.out.println("\nSelect difficulty:\n  [1] Easy\n  [2] Medium\n  [3] Hard");
        int choice = readInt(1, 3);
        DifficultyLevel level = switch (choice) {
            case 1 -> DifficultyLevel.EASY;
            case 2 -> DifficultyLevel.MEDIUM;
            case 3 -> DifficultyLevel.HARD;
            default -> DifficultyLevel.EASY;
        };
        System.out.println("Difficulty selected: " + level);
        return level;
    }

    public int promptReplayChoice() {
        System.out.println("\n============================================");
        System.out.println("  [1] Replay with same settings");
        System.out.println("  [2] Start a new game");
        System.out.println("  [3] Exit");
        System.out.println("============================================");
        return readInt(1, 3);
    }

    public boolean promptReplay() {
        return promptReplayChoice() != 3;
    }

    public void showResult(String msg) {
        System.out.println(msg);
    }

    private Item createItem(int choice) {
        return switch (choice) {
            case 1 -> new Potion();
            case 2 -> new PowerStone();
            case 3 -> new SmokeBomb();
            default -> throw new IllegalArgumentException("Invalid item choice.");
        };
    }

    private int readInt(int min, int max) {
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