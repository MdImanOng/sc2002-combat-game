package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import engine.BattleEngine;

public class SpecialSkillAction implements Action {
    @Override
    public String getName() { return "Special Skill"; }

    @Override
    public void execute(Player actor, Combatant target, BattleEngine engine) {
        if (actor.getSkillCooldown() > 0) {
            System.out.println("Skill on cooldown for " + actor.getSkillCooldown() + " more turn(s).");
            return;
        }
        actor.executeSpecialSkill(target, engine);
        actor.setSkillCooldown(3);
    }
}