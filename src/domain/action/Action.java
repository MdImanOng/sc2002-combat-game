package domain.action;

import domain.combat.Combatant;
import engine.BattleEngine;

public interface Action {
    String getName();
    void execute(Combatant actor, Combatant target, BattleEngine engine);
}