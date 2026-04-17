package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import engine.BattleEngine;

public class SpecialSkillAction implements Action {
    @Override
    public String getName() { return "Special Skill"; }

    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        if (!(actor instanceof Player p)) {
            System.out.println(actor.getName() + " cannot use a special skill.");
            return;
        }
        if (p.getSkillCooldown() > 0) {
            System.out.println("Skill on cooldown for " + p.getSkillCooldown() + " more turn(s).");
            return;
        }
        p.executeSpecialSkill(target, engine);
        p.setSkillCooldown(3);
    }
}