package domain.item;

import domain.combat.Combatant;

public interface Item {
    void use(Combatant user, Combatant target);
    String getName();
}

