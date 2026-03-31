package domain.action;

import domain.combat.Combatant;
import domain.combat.Enemy;
import domain.combat.Player;
import domain.combat.Warrior;
import domain.combat.Wizard;
import engine.BattleEngine;

import java.util.List;

public class SpecialSkillAction implements Action {

    @Override
    public String getName() {
        return "Special Skill";
    }

    @Override
    public void execute(Player actor, Combatant target, BattleEngine engine) {
        if (actor.getSkillCooldown() > 0) {
            System.out.println("Skill is on cooldown for " + actor.getSkillCooldown() + " more turn(s).");
            return;
        }

        if (actor instanceof Warrior) {
            useWarriorSkill((Warrior) actor, target);
        } else if (actor instanceof Wizard) {
            useWizardSkill((Wizard) actor, engine);
        } else {
            System.out.println("Unknown player class.");
            return;
        }

        actor.setSkillCooldown(3);
    }

    private void useWarriorSkill(Warrior actor, Combatant target) {
        if (target == null || !target.isAlive()) {
            System.out.println("Invalid target.");
            return;
        }

        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        target.takeDamage(damage);

        System.out.println(actor.getName() + " uses Shield Bash on " + target.getName()
                + " for " + damage + " damage.");
    }

    private void useWizardSkill(Wizard actor, BattleEngine engine) {
        if (engine == null) {
            System.out.println("Battle engine missing for Wizard skill.");
            return;
        }

        List<Enemy> enemies = engine.getAliveEnemies();
        int kills = 0;

        for (Enemy enemy : enemies) {
            int damage = Math.max(0, actor.getAttack() - enemy.getDefense());
            enemy.takeDamage(damage);

            System.out.println(actor.getName() + " hits " + enemy.getName()
                    + " with Arcane Blast for " + damage + " damage.");

            if (!enemy.isAlive()) {
                kills++;
            }
        }

        if (kills > 0) {
            actor.addBonusAttack(kills * 10);
            System.out.println(actor.getName() + " gains +" + (kills * 10)
                    + " attack for the rest of the level.");
        }
    }
}