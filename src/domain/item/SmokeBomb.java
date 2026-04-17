package domain.item;

import domain.combat.Combatant;
import domain.status.SmokeBombEffect;
import engine.BattleEngine;

public class SmokeBomb implements Item {

    @Override
public void use(Combatant user, Combatant target, BattleEngine engine) {
    user.addStatusEffect(new SmokeBombEffect());
    System.out.println(user.getName() + " used Smoke Bomb! Invulnerable for 2 turns.");
}

    @Override
    public String getName() {
        return "Smoke Bomb";
    }
}
