package domain.item;

import domain.combat.Combatant;
import engine.BattleContext;

public class Potion implements Item {
    private boolean consumed = false;

    @Override
    public void use(Combatant user, Combatant target, BattleContext ctx) {
        user.heal(100);
        consumed = true;
        System.out.println(user.getName() + " used Potion! Healed 100 HP. HP: "
                + user.getHp() + "/" + user.getMaxHp());
    }

    @Override
    public String getName() { return "Potion"; }

    @Override
    public boolean isConsumed() { return consumed; }
}