package domain.status;

import domain.combat.Combatant;

public interface StatusEffect {
    void apply(Combatant target);
    void onTurnStart(Combatant target);
    boolean isExpired();
    String getName();
}