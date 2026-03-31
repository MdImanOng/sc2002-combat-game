package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import engine.BattleEngine;

public class DefendAction implements Action {

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void execute(Player actor, Combatant target, BattleEngine engine) {
        actor.activateDefend();
        System.out.println(actor.getName() + " uses Defend and gains +10 defense for 2 turns.");
    }
}