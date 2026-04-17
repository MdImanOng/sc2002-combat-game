package engine;

import domain.action.BasicAttackAction;
import domain.combat.Enemy;
import domain.combat.Player;
import ui.BattleUI;

public class BasicAttackStrategy implements EnemyActionStrategy {
    @Override
    public void execute(Enemy enemy, Player player, BattleUI ui) {
        new BasicAttackAction().execute(enemy, player, null);
    }
}