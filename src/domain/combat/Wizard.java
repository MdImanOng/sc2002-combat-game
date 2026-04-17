package domain.combat;

import domain.status.StunEffect;
import engine.BattleEngine;
import java.util.List;

public class Wizard extends Player {
    public Wizard(String name) {
        super(name, 200, 50, 10, 20);
    }

    @Override
    public void executeSpecialSkill(Combatant target, BattleEngine engine) {
        if (engine == null) {
            System.out.println("No battle engine available.");
            return;
        }
        List<Enemy> enemies = engine.getAliveEnemies();
        int kills = 0;
        for (Enemy e : enemies) {
            int damage = Math.max(0, getAttack() - e.getDefense());
            e.takeDamage(damage);
            System.out.println(getName() + " hits " + e.getName()
                    + " with Arcane Blast for " + damage + " damage.");
            if (!e.isAlive()) kills++;
        }
        if (kills > 0) {
            addBonusAttack(kills * 10);
            System.out.println(getName() + " gains +" + (kills * 10) + " ATK from Arcane Blast kills!");
        }
    }

    @Override
    public void useSpecialSkillWithoutCooldown() {
        // PowerStone triggers this — engine context handled by PowerStone
        System.out.println(getName() + " triggers Arcane Blast via Power Stone.");
    }
}