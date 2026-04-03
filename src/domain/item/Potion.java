package domain.item;

import domain.combat.Combatant;

public class Potion implements Item {

    @Override
    public void use(Combatant user, Combatant target) {
        user.heal(100);   // ✅ use existing method
        System.out.println(user.getName() + " used Potion and healed!");
    }

    @Override
    public String getName() {
        return "Potion";
    }
}

