package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import domain.status.DefendEffect;
import engine.BattleEngine;

public class DefendAction implements Action {
    @Override
    public String getName() { return "Defend"; }

    @Override
    public void execute(Player actor, Combatant target, BattleEngine engine) {
        actor.addStatusEffect(new DefendEffect());
        System.out.println(actor.getName() + " takes a defensive stance! +10 DEF for 2 turns.");
    }
}