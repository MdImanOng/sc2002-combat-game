package domain.status;

import domain.combat.Combatant;

public class StunEffect extends StatusEffect {

    public StunEffect() {
        super(EffectType.STUN, 2);
    }

    @Override
    public void apply(Combatant target) {}

    @Override
    public void remove(Combatant target) {}

    @Override
    public void onTurnStart(Combatant target) {
        tick(); // tickEffects() in Combatant handles removal when expired
    }
}