package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import engine.BattleEngine;

public interface Action {
    String getName();
    void execute(Player actor, Combatant target, BattleEngine engine);
}