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
        int kills = 0;
        for (Enemy e : enemies) {
            int damage = Math.max(0, getAttack() - e.getDefense());
            e.takeDamage(damage);
            System.out.println(getName() + " hits " + e.getName()
                    + " with Arcane Blast for " + damage + " damage.");
            if (!e.isAlive()) kills++;
        }
        if (kills > 0) {
            int boost = kills * 10;
            arcaneBoost += boost;
            applyEffect(new ArcaneBoostEffect(boost));
        }
    }

    public int getArcaneBoost() { return arcaneBoost; }
}