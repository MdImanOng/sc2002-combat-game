package domain.status;

import domain.combat.Combatant;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect() {
        super(EffectType.SMOKE_INVULNERABLE, 2);
    }

    @Override
    public void apply(Combatant target) {
        // isInvulnerable() derived from active effects
    }

    @Override
    public void remove(Combatant target) {
        // nothing to undo
    }

    @Override
    public void onTurnStart(Combatant target) {
        tick();
    }
}