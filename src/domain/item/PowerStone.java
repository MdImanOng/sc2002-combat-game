package domain.item;

import domain.combat.Combatant;
import domain.combat.SkillUser;
import engine.BattleContext;

public class PowerStone implements Item {
    private boolean consumed = false;

    @Override
    public void use(Combatant user, Combatant target, BattleContext ctx) {
        if (!(user instanceof SkillUser skillUser)) {
            System.out.println(user.getName() + " cannot use Power Stone.");
            return;
        }
        int savedCooldown = skillUser.getSkillCooldown();
        skillUser.executeSpecialSkill(target, ctx);
        skillUser.setSkillCooldown(savedCooldown);
        consumed = true;
        System.out.println("Power Stone used — cooldown stays at " + savedCooldown + ".");
    }

    @Override
    public String getName() { return "Power Stone"; }

    @Override
    public boolean isConsumed() { return consumed; }
}