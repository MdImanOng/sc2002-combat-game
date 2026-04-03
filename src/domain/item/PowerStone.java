package domain.item;

import domain.combat.Combatant;

public class PowerStone implements Item {

    @Override
    public void use(Combatant user, Combatant target) {
        user.useSpecialSkillWithoutCooldown();
        System.out.println(user.getName() + " used Power Stone!");
    }

    @Override
    public String getName() {
        return "Power Stone";
    }
}

