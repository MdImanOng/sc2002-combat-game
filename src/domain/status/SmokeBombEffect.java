package domain.status;

import domain.combat.Combatant;

public class SmokeBombEffect extends StatusEffect {

    public SmokeBombEffect() {
        super(EffectType.SMOKE_INVULNERABLE, 2);
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