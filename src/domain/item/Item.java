package domain.item;

import domain.combat.Combatant;
import engine.BattleEngine;

public interface Item {
    void use(Combatant user, Combatant target, BattleEngine engine);
    String getName();
}