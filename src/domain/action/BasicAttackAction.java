package domain.action;

import domain.combat.Combatant;
import engine.BattleEngine;

public class BasicAttackAction implements Action {
    @Override
    public String getName() { return "Basic Attack"; }

    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
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