package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import domain.combat.SkillUser;
import engine.BattleContext;

public class SpecialSkillAction implements Action {
    @Override
    public ActionType getType() { return ActionType.SPECIAL_SKILL; }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext ctx) {
        if (!(actor instanceof SkillUser skillUser)) {
            System.out.println(actor.getName() + " cannot use a special skill.");
            return;
        }
        if (skillUser.getSkillCooldown() > 0) {
            System.out.println("Skill on cooldown for " + skillUser.getSkillCooldown() + " more turn(s).");
            return;
        }
        skillUser.executeSpecialSkill(target, ctx);
        skillUser.setSkillCooldown(3);
    }
}