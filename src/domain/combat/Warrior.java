package domain.combat;

import domain.status.StunEffect;
import engine.BattleContext;

public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
    }

    @Override
    public void executeSpecialSkill(Combatant target, BattleContext ctx) {
        if (target == null || !target.isAlive()) {
            System.out.println("Invalid target for Shield Bash.");
            return;
        }
        int damage = Math.max(0, getAttack() - target.getDefense());
        target.takeDamage(damage);
        target.applyEffect(new StunEffect());
        System.out.println(getName() + " uses Shield Bash on " + target.getName()
                + " for " + damage + " damage! " + target.getName() + " is STUNNED.");
    }
}