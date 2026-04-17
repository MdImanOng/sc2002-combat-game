package domain.action;

import domain.combat.Combatant;
import engine.BattleContext;

public interface Action {
    void execute(Combatant actor, Combatant target, BattleContext ctx);
    ActionType getType();
}