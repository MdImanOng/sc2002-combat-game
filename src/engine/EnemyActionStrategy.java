package engine;

import domain.combat.Enemy;
import domain.combat.Player;

public interface EnemyActionStrategy {
    void execute(Enemy enemy, Player player, BattleEngine engine);
}