package domain.item;

import domain.combat.Combatant;
import domain.status.SmokeBombEffect;

public class SmokeBomb implements Item {

    @Override
    public void use(Combatant user, Combatant target) {
        user.addStatusEffect(new SmokeBombEffect());
        System.out.println(user.getName() + " is now invulnerable!");
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }
}
