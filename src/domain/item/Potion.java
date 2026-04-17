package domain.item;

import domain.combat.Combatant;
import engine.BattleEngine;

public class Potion implements Item {

    @Override
public void use(Combatant user, Combatant target, BattleEngine engine) {
    user.heal(100);
    System.out.println(user.getName() + " used Potion! Healed 100 HP. HP: "
            + user.getHp() + "/" + user.getMaxHp());
}

    @Override
    public String getName() {
        return "Potion";
    }
}

