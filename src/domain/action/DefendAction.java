package domain.action;

import domain.combat.Combatant;
import domain.status.DefendEffect;
import engine.BattleContext;

public class DefendAction implements Action {
    @Override
    public ActionType getType() { return ActionType.DEFEND; }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext ctx) {
        actor.applyEffect(new DefendEffect());
        System.out.println(actor.getName() + " takes a defensive stance! +10 DEF for 2 turns.");
    }
}