package engine;

import domain.action.*;
import domain.combat.*;
import domain.item.Item;
import domain.level.Level;
import ui.GameUI;
import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private final Player player;
    private final Level level;
    private final List<Enemy> activeEnemies;
    private final TurnOrderStrategy turnOrderStrategy;
    private final EnemyActionStrategy enemyActionStrategy;
    private final GameUI ui;
    private int roundCount = 0;

    public BattleEngine(Player player, Level level, TurnOrderStrategy turnOrderStrategy,
                        EnemyActionStrategy enemyActionStrategy, GameUI ui) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null.");
        if (level == null) throw new IllegalArgumentException("Level cannot be null.");
        if (turnOrderStrategy == null) throw new IllegalArgumentException("TurnOrderStrategy cannot be null.");
        if (enemyActionStrategy == null) throw new IllegalArgumentException("EnemyActionStrategy cannot be null.");
        if (ui == null) throw new IllegalArgumentException("GameUI cannot be null.");
        this.player = player;
        this.level = level;
        this.activeEnemies = new ArrayList<>(level.getInitialWave());
        this.turnOrderStrategy = turnOrderStrategy;
        this.enemyActionStrategy = enemyActionStrategy;
        this.ui = ui;
    }

    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy e : activeEnemies) if (e.isAlive()) alive.add(e);
        return alive;
    }

    public void run() {
        try {
            ui.printBattleStart(level.getDifficulty());
            while (true) {
                roundCount++;
                ui.printRoundHeader(roundCount);

                List<Combatant> allCombatants = new ArrayList<>();
                allCombatants.add(player);
                allCombatants.addAll(getAliveEnemies());

                if (checkBattleEnd()) return;

                List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(allCombatants);

                for (Combatant current : turnOrder) {
                    if (!current.isAlive()) continue;

                    // Process effects at this combatant's turn start
                    current.processEffects();

                    // Check battle end in case an effect caused elimination
                    if (checkBattleEnd()) return;

                    if (current.isStunned()) {
                        ui.printStunned(current);
                        continue;
                    }

                    try {
                        if (current instanceof Player p) playerTurn(p);
                        else if (current instanceof Enemy e) enemyTurn(e);
                    } catch (IllegalArgumentException e) {
                        ui.printError("Invalid action: " + e.getMessage() + " Please try again.");
                    } catch (Exception e) {
                        ui.printError("Unexpected error during " + current.getName() + "'s turn: " + e.getMessage());
                    }

                    if (checkBattleEnd()) return;

                    // Check backup spawn immediately after any enemy is eliminated
                    if (level.shouldSpawnBackup()) {
                        List<Enemy> backup = level.spawnBackupWave();
                        activeEnemies.addAll(backup);
                        ui.printBackupSpawn(backup);
                    }
                }

                ui.printRoundSummary(roundCount, player, activeEnemies);
            }
        } catch (Exception e) {
            ui.printError("Critical battle error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void playerTurn(Player p) {
        ui.printPlayerStatus(p);
        List<Enemy> alive = getAliveEnemies();
        ui.printEnemyList(alive);
        ui.printActions(!p.getInventory().isEmpty());

        int action = ui.readInt(1, p.getInventory().isEmpty() ? 3 : 4);

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
            case 4 -> {
                List<Item> inventory = p.getInventory();
                ui.printItemList(inventory);
                int itemChoice = ui.readInt(1, inventory.size());
                Item chosen = inventory.get(itemChoice - 1);
                Enemy itemTarget = selectEnemy(alive);
                new UseItemAction(chosen).execute(p, itemTarget, this);
                inventory.remove(itemChoice - 1);
                ui.printItemUsed(chosen);
            }
        }

        // Cooldown only reduces if player actually took a turn
        p.reduceSkillCooldown();
    }

    private void enemyTurn(Enemy e) {
        try {
            ui.printTurnHeader(e);
            if (!player.isAlive()) return;
            enemyActionStrategy.execute(e, player, this);
        } catch (Exception ex) {
            ui.printError("Error during " + e.getName() + "'s turn: " + ex.getMessage());
        }
    }

    private Enemy selectEnemy(List<Enemy> alive) {
        if (alive.isEmpty()) throw new IllegalStateException("No alive enemies to select.");
        if (alive.size() == 1) return alive.get(0);
        ui.printSelectTarget(alive);
        int idx = ui.readInt(1, alive.size());
        return alive.get(idx - 1);
    }

    private boolean checkBattleEnd() {
        if (getAliveEnemies().isEmpty() && !level.shouldSpawnBackup()) {
            ui.printVictory(player, roundCount);
            return true;
        }
        if (!player.isAlive()) {
            ui.printDefeat(roundCount, getAliveEnemies().size());
            return true;
        }
        return false;
    }
}