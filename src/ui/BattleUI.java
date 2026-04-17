package ui;

import domain.combat.Combatant;
import domain.combat.Enemy;
import domain.combat.Player;
import domain.action.ActionType;
import domain.item.Item;
import java.util.List;
import java.util.Scanner;

public class BattleUI {
    private final Scanner scanner = new Scanner(System.in);

    public void displayBattleStart(String difficulty) {
        System.out.println("\n=== BATTLE START: " + difficulty + " ===");
    }

    public void displayRoundHeader(int round) {
        System.out.println("\n--- Round " + round + " ---");
    }

    public void displayBackupSpawn(List<Enemy> backup) {
        System.out.println(">>> BACKUP SPAWN! " + backup.size() + " enemies arrive!");
        for (Enemy e : backup) System.out.println("  + " + e);
    }

    public void displayTurnHeader(Combatant c) {
        System.out.println("\n" + c.getName() + "'s turn!");
    }

    public void displayStunned(Combatant c) {
        System.out.println(c.getName() + " is STUNNED — turn skipped.");
    }

    public void displayPlayerStatus(Player p) {
        System.out.println(p.getName() + " HP: " + p.getHp() + "/" + p.getMaxHp()
                + " | Cooldown: " + p.getSkillCooldown());
    }

    public void displayEnemyList(List<Enemy> enemies) {
        System.out.println("Enemies:");
        for (int i = 0; i < enemies.size(); i++)
            System.out.println("  [" + (i + 1) + "] " + enemies.get(i));
    }

    public void displayItemList(List<Item> items) {
        System.out.println("Choose an item:");
        for (int i = 0; i < items.size(); i++)
            System.out.println("  [" + (i + 1) + "] " + items.get(i).getName());
    }

    public void displayItemUsed(Item item) {
        System.out.println(item.getName() + " used and removed from inventory.");
    }

    public void displayRoundSummary(int round, Player player, List<Enemy> activeEnemies) {
        System.out.println("\n[End of Round " + round + "]");
        System.out.println("  " + player.getName() + " HP: " + player.getHp() + "/" + player.getMaxHp()
                + " | Cooldown: " + player.getSkillCooldown()
                + " | Items: " + player.getInventory().stream().map(Item::getName).toList());
        for (Enemy e : activeEnemies)
            System.out.println("  " + e.getName() + " HP: "
                    + (e.isAlive() ? e.getHp() + "/" + e.getMaxHp() : "ELIMINATED")
                    + (e.isStunned() ? " [STUNNED]" : ""));
    }

    public void displayVictory(Player player, int rounds) {
        System.out.println("\n=== VICTORY! ===");
        System.out.println("Congratulations, you have defeated all your enemies!");
        System.out.println("Remaining HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("Total Rounds: " + rounds);
    }

    public void displayDefeat(int rounds, int enemiesRemaining) {
        System.out.println("\n=== DEFEATED. Don't give up, try again! ===");
        System.out.println("Enemies remaining: " + enemiesRemaining);
        System.out.println("Total Rounds Survived: " + rounds);
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public ActionType promptActionChoice(boolean hasItems) {
        System.out.println("Actions: [1] Basic Attack  [2] Defend  [3] Special Skill"
                + (hasItems ? "  [4] Use Item" : ""));
        int choice = readInt(1, hasItems ? 4 : 3);
        return switch (choice) {
            case 1 -> ActionType.BASIC_ATTACK;
            case 2 -> ActionType.DEFEND;
            case 3 -> ActionType.SPECIAL_SKILL;
            case 4 -> ActionType.USE_ITEM;
            default -> ActionType.BASIC_ATTACK;
        };
    }

    public Combatant promptTargetChoice(List<Enemy> alive) {
        if (alive.isEmpty()) throw new IllegalStateException("No alive enemies.");
        if (alive.size() == 1) return alive.get(0);
        System.out.println("Select target:");
        for (int i = 0; i < alive.size(); i++)
            System.out.println("  [" + (i + 1) + "] " + alive.get(i).getName());
        return alive.get(readInt(1, alive.size()) - 1);
    }

    public int promptItemIndex(int inventorySize) {
        return readInt(1, inventorySize) - 1;
    }

    public boolean promptReplay() {
        System.out.println("\n============================================");
        System.out.println("  [1] Replay with same settings");
        System.out.println("  [2] Start a new game");
        System.out.println("  [3] Exit");
        System.out.println("============================================");
        return readInt(1, 3) != 3;
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