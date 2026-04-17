package domain.combat;

import domain.status.StunEffect;
import engine.BattleEngine;

public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
    }

    @Override
    public void executeSpecialSkill(Combatant target, BattleEngine engine) {
        if (target == null || !target.isAlive()) {
            System.out.println("Invalid target for Shield Bash.");
            return;
        }
        int damage = Math.max(0, getAttack() - target.getDefense());
        target.takeDamage(damage);
        target.addStatusEffect(new StunEffect());
        System.out.println(getName() + " uses Shield Bash on " + target.getName()
                + " for " + damage + " damage! " + target.getName() + " is STUNNED.");
    }

    @Override
    public void useSpecialSkillWithoutCooldown() {
        // Called by PowerStone — but needs target/engine, so PowerStone handles Warrior differently
        System.out.println(getName() + " triggers Shield Bash via Power Stone (select target in engine).");
    }
}