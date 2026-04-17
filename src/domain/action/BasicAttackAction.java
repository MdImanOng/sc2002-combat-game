package domain.action;

import domain.combat.Combatant;
import engine.BattleContext;

public class BasicAttackAction implements Action {
    @Override
    public ActionType getType() { return ActionType.BASIC_ATTACK; }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext ctx) {
        if (target == null || !target.isAlive()) {
            System.out.println("Invalid target.");
            return;
        }
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        target.takeDamage(damage);
        System.out.println(actor.getName() + " attacks " + target.getName()
                + " for " + damage + " damage.");
    }
}