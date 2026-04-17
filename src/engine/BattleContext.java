package engine;

import domain.combat.Combatant;
import domain.combat.Enemy;
import domain.combat.Player;
import ui.BattleUI;
import java.util.List;

public class BattleContext {
    private final List<Combatant> allCombatants;
    private final Player player;
    private final List<Enemy> activeEnemies;
    private int currentRound;
    private final BattleUI ui;

    public BattleContext(List<Combatant> allCombatants, Player player,
                         List<Enemy> activeEnemies, int currentRound, BattleUI ui) {
        this.allCombatants = allCombatants;
        this.player = player;
        this.activeEnemies = activeEnemies;
        this.currentRound = currentRound;
        this.ui = ui;
    }

    public List<Enemy> getAliveEnemies() {
        return activeEnemies.stream().filter(e -> e.isAlive()).toList();
    }

    public Player getPlayer() { return player; }
    public int getRound() { return currentRound; }
    public BattleUI getUI() { return ui; }
    public List<Combatant> getAllCombatants() { return allCombatants; }
}