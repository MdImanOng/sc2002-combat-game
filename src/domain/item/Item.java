package domain.item;

import domain.combat.Combatant;
import engine.BattleContext;

public interface Item {
    void use(Combatant user, Combatant target, BattleContext ctx);
    String getName();
    boolean isConsumed();
}