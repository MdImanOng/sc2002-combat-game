package ui;

import domain.combat.Combatant;
import domain.combat.Enemy;
import domain.combat.Player;
import domain.item.Item;
import java.util.List;
import java.util.Scanner;

public class GameUI {
    private final Scanner scanner = new Scanner(System.in);

    public void printLoadingScreen() {
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
        System.out.println("  [2] Medium — Initial Spawn: 1 Goblin + 1 Wolf | Backup Spawn: 2 Wolves");
        System.out.println("  [3] Hard   — Initial Spawn: 2 Goblins          | Backup Spawn: 1 Goblin + 2 Wolves");
        System.out.println("============================================\n");
    }

    public void printRoundHeader(int roundCount) {
        System.out.println("\n--- Round " + roundCount + " ---");
    }

    public void printBackupSpawn(List<Enemy> backup) {
        System.out.println(">>> BACKUP SPAWN! " + backup.size() + " enemies arrive!");
        for (Enemy e : backup) System.out.println("  + " + e);
    }

    public void printTurnHeader(Combatant combatant) {
        System.out.println("\n" + combatant.getName() + "'s turn!");
    }

    public void printStunned(Combatant combatant) {
        System.out.println(combatant.getName() + " is STUNNED — turn skipped.");
    }

    public void printEnemyList(List<Enemy> enemies) {
        System.out.println("Enemies:");
        for (int i = 0; i < enemies.size(); i++)
            System.out.println("  [" + (i + 1) + "] " + enemies.get(i));
    }

    public void printPlayerStatus(Player p) {
        System.out.println("\n" + p.getName() + "'s turn! HP: " + p.getHp() + "/" + p.getMaxHp()
                + " | Cooldown: " + p.getSkillCooldown());
    }

    public void printActions(boolean hasItems) {
        System.out.println("Actions: [1] Basic Attack  [2] Defend  [3] Special Skill"
                + (hasItems ? "  [4] Use Item" : ""));
    }

    public void printItemList(List<Item> inventory) {
        System.out.println("Choose an item:");
        for (int i = 0; i < inventory.size(); i++)
            System.out.println("  [" + (i + 1) + "] " + inventory.get(i).getName());
    }

    public void printItemUsed(Item item) {
        System.out.println(item.getName() + " used and removed from inventory.");
    }

    public void printRoundSummary(int roundCount, Player player, List<Enemy> activeEnemies) {
        System.out.println("\n[End of Round " + roundCount + "]");
        System.out.println("  " + player.getName() + " HP: " + player.getHp() + "/" + player.getMaxHp()
                + " | Cooldown: " + player.getSkillCooldown()
                + " | Items: " + player.getInventory().stream().map(Item::getName).toList());
        for (Enemy e : activeEnemies)
            System.out.println("  " + e.getName() + " HP: "
                    + (e.isAlive() ? e.getHp() + "/" + e.getMaxHp() : "ELIMINATED")
                    + (e.isStunned() ? " [STUNNED]" : ""));
    }

    public void printVictory(Player player, int roundCount) {
        System.out.println("\n=== VICTORY! ===");
        System.out.println("Congratulations, you have defeated all your enemies!");
        System.out.println("Remaining HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("Total Rounds: " + roundCount);
    }

    public void printDefeat(int roundCount, int enemiesRemaining) {
        System.out.println("\n=== DEFEATED. Don't give up, try again! ===");
        System.out.println("Enemies remaining: " + enemiesRemaining);
        System.out.println("Total Rounds Survived: " + roundCount);
    }

    public void printBattleStart(String difficulty) {
        System.out.println("\n=== BATTLE START: " + difficulty + " ===");
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printSelectTarget(List<Enemy> alive) {
        System.out.println("Select target:");
        for (int i = 0; i < alive.size(); i++)
            System.out.println("  [" + (i + 1) + "] " + alive.get(i).getName());
    }

    public void printReplayMenu() {
        System.out.println("\n============================================");
        System.out.println("  [1] Replay with same settings");
        System.out.println("  [2] Start a new game");
        System.out.println("  [3] Exit");
        System.out.println("============================================");
    }

    public void printGoodbye() {
        System.out.println("Thanks for playing!");
    }

    public int readInt(int min, int max) {
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

    public String readName() {
        System.out.print("Enter your name: ");
        return scanner.nextLine().trim();
    }
}