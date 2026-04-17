package domain.combat;

import engine.BattleContext;
import engine.EnemyActionStrategy;
import java.util.List;

public abstract class Enemy extends Combatant {
    private EnemyActionStrategy actionStrategy;

    public Enemy(String name, int maxHp, int attack, int defense, int speed,
                 EnemyActionStrategy actionStrategy) {
        super(name, maxHp, attack, defense, speed);
        this.actionStrategy = actionStrategy;
    }

    public void setActionStrategy(EnemyActionStrategy strategy) {
        this.actionStrategy = strategy;
    }

    public Combatant selectTarget(List<Combatant> targets) {
        return targets.stream().filter(Combatant::isAlive).findFirst().orElse(null);
    }

    @Override
    public void performAction(BattleContext ctx) {
        actionStrategy.execute(this, ctx.getPlayer(), ctx.getUI());
    }
}