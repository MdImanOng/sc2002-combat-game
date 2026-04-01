package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import engine.BattleEngine;

public class UseItemAction implements Action {

    @Override
    public String getName() {
        return "Use Item";
    }

    @Override
    public void execute(Player actor, Combatant target, BattleEngine engine) {
        if (actor == null) {
            System.out.println("Invalid player.");
            return;
        }

        System.out.println(actor.getName() + " uses an item.");
    }
}