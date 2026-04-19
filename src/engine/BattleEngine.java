package engine;

import domain.combat.*;
import domain.level.Level;
import ui.BattleUI;
import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private final Player player;
    private final List<Enemy> activeEnemies;
    private final TurnOrderStrategy turnOrderStrategy;
    private final BattleUI ui;
    private int currentRound = 0;
    private final Level level;

    public BattleEngine(Player player, Level level,
                        TurnOrderStrategy turnOrderStrategy, BattleUI ui) {
        if (player == null) throw new IllegalArgumentException("Player cannot be null.");
        if (level == null) throw new IllegalArgumentException("Level cannot be null.");
        if (turnOrderStrategy == null) throw new IllegalArgumentException("Strategy cannot be null.");
        if (ui == null) throw new IllegalArgumentException("UI cannot be null.");
        this.player = player;
        this.level = level;
        this.activeEnemies = new ArrayList<>(level.getInitialWave());
        this.turnOrderStrategy = turnOrderStrategy;
        this.ui = ui;
    }

    public List<Enemy> getAliveEnemies() {
        return activeEnemies.stream().filter(Enemy::isAlive).toList();
    }

    public void runBattle() {
        try {
            ui.displayBattleStart(level.getDifficulty());
            while (true) {
                currentRound++;
                ui.displayRoundHeader(currentRound);
                processRound();
                if (checkEndCondition()) return;
                ui.displayRoundSummary(currentRound, player, activeEnemies);
            }
        } catch (Exception e) {
            ui.displayMessage("Critical battle error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void processRound() {
        List<Combatant> allCombatants = new ArrayList<>();
        allCombatants.add(player);
        allCombatants.addAll(getAliveEnemies());
        List<Combatant> turnOrder = getTurnOrder(allCombatants);
        for (Combatant current : turnOrder) {
            if (!current.isAlive()) continue;
            processTurn(current);
            if (checkEndCondition()) return;
            handleBackupSpawn();
        }
    }

    public void processTurn(Combatant current) {
        // Check stun BEFORE ticking effects.
        // Stun blocks the full turn, THEN ticks down at end of that blocked turn.
        // This matches appendix: stunned entity skips turn, stun expires after blocking.
        if (current.isStunned()) {
            ui.displayStunned(current);
            current.tickEffects(); // tick stun duration AFTER blocking the turn
            return;
        }

        current.tickEffects(); // tick non-stun effects at start of normal turn
        if (checkEndCondition()) return;

        ui.displayTurnHeader(current);
        if (current instanceof Player p) ui.displayPlayerStatus(p);
        if (current instanceof Enemy) ui.displayEnemyList(getAliveEnemies());

        BattleContext ctx = new BattleContext(
                new ArrayList<>(activeEnemies),
                player, activeEnemies, currentRound, ui);
        try {
            current.performAction(ctx);
        } catch (IllegalArgumentException e) {
            ui.displayMessage("Invalid action: " + e.getMessage());
        } catch (Exception e) {
            ui.displayMessage("Error during " + current.getName() + "'s turn: " + e.getMessage());
        }
    }

    public boolean checkEndCondition() {
        if (getAliveEnemies().isEmpty() && !level.shouldSpawnBackup()) {
            ui.displayVictory(player, currentRound);
            return true;
        }
        if (!player.isAlive()) {
            ui.displayDefeat(currentRound, getAliveEnemies().size());
            return true;
        }
        return false;
    }

    public void handleBackupSpawn() {
        if (level.shouldSpawnBackup()) {
            List<Enemy> backup = level.spawnBackupWave();
            activeEnemies.addAll(backup);
            ui.displayBackupSpawn(backup);
        }
    }

    public List<Combatant> getTurnOrder(List<Combatant> combatants) {
        return turnOrderStrategy.determineTurnOrder(combatants);
    }
}