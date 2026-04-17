package engine;

import domain.action.BasicAttackAction;
import domain.combat.Enemy;
import domain.combat.Player;

public class BasicAttackStrategy implements EnemyActionStrategy {
    @Override
    public void execute(Enemy enemy, Player player, BattleEngine engine) {
        new BasicAttackAction().execute(enemy, player, engine);
    }
}