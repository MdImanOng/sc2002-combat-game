package engine;

import domain.action.*;
import domain.combat.*;
import domain.item.Item;
import domain.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleEngine {
    private final Player player;
    private final Level level;
    private final List<Enemy> activeEnemies;
    private final TurnOrderStrategy turnOrderStrategy;
    private final Scanner scanner = new Scanner(System.in);
    private int roundCount = 0;

    public BattleEngine(Player player, Level level, TurnOrderStrategy strategy) {
        this.player = player;
        this.level = level;
        this.activeEnemies = new ArrayList<>(level.getInitialWave());
        this.turnOrderStrategy = strategy;
    }

    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy e : activeEnemies) if (e.isAlive()) alive.add(e);
        return alive;
    }

    public void run() {
        System.out.println("\n=== BATTLE START: " + level.getDifficulty() + " ===");
        while (true) {
            roundCount++;
            System.out.println("\n--- Round " + roundCount + " ---");

            // Check backup spawn
            if (level.shouldSpawnBackup()) {
                List<Enemy> backup = level.spawnBackupWave();
                activeEnemies.addAll(backup);
                System.out.println(">>> BACKUP SPAWN! " + backup.size() + " enemies arrive!");
                for (Enemy e : backup) System.out.println("  + " + e);
            }

            // Build combatant list
            List<Combatant> allCombatants = new ArrayList<>();
            allCombatants.add(player);
            allCombatants.addAll(getAliveEnemies());

            // Process status effects
            for (Combatant c : allCombatants) c.processEffects();

            // Check win/loss after effects
            if (checkBattleEnd()) return;

            // Determine turn order
            List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(allCombatants);

            for (Combatant current : turnOrder) {
                if (!current.isAlive()) continue;

                if (current.isStunned()) {
                    System.out.println(current.getName() + " is STUNNED — turn skipped.");
                    continue;
                }

                if (current instanceof Player p) {
                    playerTurn(p);
                } else if (current instanceof Enemy e) {
                    enemyTurn(e);
                }

                if (checkBattleEnd()) return;
            }

            // Reduce player skill cooldown at end of round
            player.reduceSkillCooldown();

            displayRoundSummary();
        }
    }

    private void playerTurn(Player p) {
        System.out.println("\n" + p.getName() + "'s turn! HP: " + p.getHp() + "/" + p.getMaxHp()
                + " | Cooldown: " + p.getSkillCooldown());
        System.out.println("Enemies:");
        List<Enemy> alive = getAliveEnemies();
        for (int i = 0; i < alive.size(); i++)
            System.out.println("  [" + (i + 1) + "] " + alive.get(i));

        System.out.println("Actions: [1] Basic Attack  [2] Defend  [3] Special Skill"
                + (p.getInventory().isEmpty() ? "" : "  [4] Use Item"));

        int action = readInt(1, p.getInventory().isEmpty() ? 3 : 4);

        switch (action) {
            case 1 -> {
                Enemy target = selectEnemy(alive);
                new BasicAttackAction().execute(p, target, this);
            }
            case 2 -> new DefendAction().execute(p, null, this);
            case 3 -> {
                Enemy target = selectEnemy(alive);
                new SpecialSkillAction().execute(p, target, this);
            }
            case 4 -> new UseItemAction().execute(p, selectEnemy(alive), this);
        }
    }

    private void enemyTurn(Enemy e) {
        System.out.println("\n" + e.getName() + "'s turn!");
        int damage = Math.max(0, e.getAttack() - player.getDefense());
        player.takeDamage(damage);
    }

    private Enemy selectEnemy(List<Enemy> alive) {
        if (alive.size() == 1) return alive.get(0);
        System.out.print("Select target [1-" + alive.size() + "]: ");
        int idx = readInt(1, alive.size());
        return alive.get(idx - 1);
    }

    private int readInt(int min, int max) {
        int val = -1;
        while (val < min || val > max) {
            System.out.print("Choice [" + min + "-" + max + "]: ");
            try { val = Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { val = -1; }
        }
        return val;
    }

    private boolean checkBattleEnd() {
        if (getAliveEnemies().isEmpty() && !level.shouldSpawnBackup()) {
            System.out.println("\n=== VICTORY! All enemies defeated! ===");
            System.out.println("Remaining HP: " + player.getHp() + "/" + player.getMaxHp());
            System.out.println("Total Rounds: " + roundCount);
            return true;
        }
        if (!player.isAlive()) {
            System.out.println("\n=== DEFEATED. Don't give up, try again! ===");
            System.out.println("Total Rounds Survived: " + roundCount);
            return true;
        }
        return false;
    }

    private void displayRoundSummary() {
        System.out.println("\n[End of Round " + roundCount + "]");
        System.out.println("  " + player.getName() + " HP: " + player.getHp() + "/" + player.getMaxHp()
                + " | Cooldown: " + player.getSkillCooldown()
                + " | Items: " + player.getInventory().stream()
                        .map(Item::getName).toList());
        for (Enemy e : activeEnemies)
            System.out.println("  " + e.getName() + " HP: " + (e.isAlive() ? e.getHp() : "ELIMINATED")
                    + (e.isStunned() ? " [STUNNED]" : ""));
    }
}