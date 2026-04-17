package domain.item;

import domain.combat.Combatant;
import domain.status.SmokeBombEffect;
import engine.BattleContext;

public class SmokeBomb implements Item {
    private boolean consumed = false;

    @Override
    public void use(Combatant user, Combatant target, BattleContext ctx) {
        user.applyEffect(new SmokeBombEffect());
        consumed = true;
        System.out.println(user.getName() + " used Smoke Bomb! Invulnerable for 2 turns.");
    }

    @Override
    public String getName() { return "Smoke Bomb"; }

    @Override
    public boolean isConsumed() { return consumed; }
}