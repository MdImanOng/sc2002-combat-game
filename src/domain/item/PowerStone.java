package domain.item;

import domain.combat.Combatant;
import domain.combat.Player;
import engine.BattleEngine;

public class PowerStone implements Item {
    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
        if (user instanceof Player p) {
            int savedCooldown = p.getSkillCooldown();
            p.executeSpecialSkill(target, engine);
            p.setSkillCooldown(savedCooldown); // cooldown unchanged per spec
            System.out.println("Power Stone used — cooldown stays at " + savedCooldown + ".");
        }
    }

    @Override
    public String getName() { return "Power Stone"; }
}