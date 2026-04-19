package domain.combat;

import domain.status.ArcaneBoostEffect;
import engine.BattleContext;
import java.util.List;

public class Wizard extends Player {
    private int arcaneBoost = 0;

    public Wizard(String name) {
        super(name, 200, 50, 10, 20);
    }

    @Override
    public void executeSpecialSkill(Combatant target, BattleContext ctx) {
        if (ctx == null) {
            System.out.println("No battle context available.");
            return;
        }
        List<Enemy> enemies = ctx.getAliveEnemies();
        for (Enemy e : enemies) {
            int damage = Math.max(0, getAttack() - e.getDefense());
            e.takeDamage(damage);
            System.out.println(getName() + " hits " + e.getName()
                    + " with Arcane Blast for " + damage + " damage.");
            if (!e.isAlive()) {
                arcaneBoost += 10;
                applyEffect(new ArcaneBoostEffect(10)); // ArcaneBoostEffect.apply() calls increaseAttack(10)
                System.out.println(getName() + " gains +10 ATK from kill! ATK now: " + getAttack());
            }
        }
    }

    public int getArcaneBoost() { return arcaneBoost; }
}