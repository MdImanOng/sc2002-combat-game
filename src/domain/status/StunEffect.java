package domain.status;

import domain.combat.Combatant;

public class StunEffect extends StatusEffect {
    public StunEffect() {
        super(EffectType.STUN, 2);
    }

    @Override
    public void apply(Combatant target) {
        // isStunned() derived from active effects
    }

    @Override
    public void remove(Combatant target) {
        // nothing to undo — isStunned() checks effects list
    }

    @Override
    public void onTurnStart(Combatant target) {
        tick();
    }
}