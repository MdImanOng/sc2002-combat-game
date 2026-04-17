package engine;

import domain.combat.Enemy;
import domain.combat.Player;
import ui.BattleUI;

public interface EnemyActionStrategy {
    void execute(Enemy enemy, Player player, BattleUI ui);
}